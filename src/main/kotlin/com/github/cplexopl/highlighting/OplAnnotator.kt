package com.github.cplexopl.highlighting

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.lang.annotation.HighlightSeverity
import com.github.cplexopl.psi.*

class OplAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        // TARCZA OCHRONNA: Jeśli wystąpi jakikolwiek błąd, wyłapujemy go,
        // dzięki czemu IDE nigdy więcej nie zawiesi się na "Analyzing"
        try {
            // --- 1. Walidacja typów (boolean in range) ---
            if (element is OplDvarDeclaration) {
                val isBoolean = element.node.findChildByType(OplTypes.BOOLEAN) != null
                val hasRange = element.node.findChildByType(OplTypes.IN) != null

                if (isBoolean && hasRange) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Type 'boolean' cannot have a range ('in' clause)")
                        .range(element.textRange)
                        .create()
                }
            }

            // Analizujemy tylko tokeny zmiennych
            if (element.node.elementType == OplTypes.ID) {
                val name = element.text
                val parent = element.parent ?: return

                // Zignoruj słowa wbudowane CPLEX oraz globalne funkcje CP (Constraint Programming)
                val builtins = setOf(
                    "abs", "ceil", "floor", "max", "min", "sum", "forall",
                    "pulse", "step", "allDifferent", "pack", "all"
                )

                if (builtins.contains(name)) return

                // Czy ten ID to miejsce deklaracji zmiennej?
                val isDeclaration = parent is OplDvarDeclaration ||
                        parent is OplVarDeclaration ||
                        parent is OplTupleDeclaration ||
                        parent is OplConstraintItem ||
                        (parent is OplFactor && parent.node.findChildByType(OplTypes.SUM) != null)

                val file = element.containingFile ?: return
                val declaredVariables = mutableMapOf<String, MutableList<PsiElement>>()

                // Funkcja skanująca plik w poszukiwaniu deklaracji
                fun registerDeclaration(declarationNode: PsiElement) {
                    val idNode = declarationNode.node.findChildByType(OplTypes.ID)
                    if (idNode != null) {
                        declaredVariables.computeIfAbsent(idNode.text) { mutableListOf() }.add(declarationNode)
                    }
                }

                PsiTreeUtil.findChildrenOfType(file, OplVarDeclaration::class.java).forEach { registerDeclaration(it) }
                PsiTreeUtil.findChildrenOfType(file, OplDvarDeclaration::class.java).forEach { registerDeclaration(it) }
                PsiTreeUtil.findChildrenOfType(file, OplTupleDeclaration::class.java).forEach { registerDeclaration(it) }

                // Rejestrujemy jako globalne TYLKO etykiety ograniczeń (te z dwukropkiem, np. CapacityConstraint:)
                // Pętle forall mają własne iteratory, których NIE WOLNO dodawać do puli globalnej.
                PsiTreeUtil.findChildrenOfType(file, OplConstraintItem::class.java).forEach {
                    if (it.node.findChildByType(OplTypes.COLON) != null) {
                        registerDeclaration(it)
                    }
                }

                if (isDeclaration) {
                    // Sprawdzanie duplikatów
                    val declarationsList = declaredVariables[name]
                    if (declarationsList != null && declarationsList.size > 1 && declarationsList.indexOf(parent) > 0) {
                        holder.newAnnotation(HighlightSeverity.ERROR, "Variable '$name' is already defined")
                            .range(element.textRange)
                            .create()
                    }

                    // Sprawdzanie brakującego średnika (tylko dla faktycznych deklaracji zmiennych/typów)
                    val needsSemicolon = parent is OplDvarDeclaration ||
                            parent is OplVarDeclaration ||
                            parent is OplTupleDeclaration

                    if (needsSemicolon) {
                        var lastChild = parent.node.lastChildNode
                        while (lastChild != null && lastChild.elementType == com.intellij.psi.TokenType.WHITE_SPACE) {
                            lastChild = lastChild.treePrev
                        }

                        if (lastChild != null && lastChild.elementType != OplTypes.SEMICOLON) {
                            holder.newAnnotation(HighlightSeverity.ERROR, "Missing semicolon ';' at the end of declaration")
                                .range(element.textRange)
                                .create()
                        }
                    }

                } else {
                    // --- 2. Scope-aware analysis (Zasięg w pętlach) ---
                    var isLocalVariable = false
                    var currentNode: PsiElement? = element.parent

                    while (currentNode != null && currentNode !is com.intellij.psi.PsiFile) {
                        val node = currentNode.node
                        if (node != null) {
                            val hasInClause = node.findChildByType(OplTypes.IN) != null
                            if (hasInClause) {
                                val directIds = node.getChildren(null).filter { it.elementType == OplTypes.ID }
                                if (directIds.any { it.text == name }) {
                                    isLocalVariable = true
                                    break
                                }
                            }
                        }
                        currentNode = currentNode.parent
                    }

                    if (!isLocalVariable && !declaredVariables.containsKey(name)) {
                        holder.newAnnotation(HighlightSeverity.ERROR, "Undefined variable: '$name'")
                            .range(element.textRange)
                            .create()
                    }
                }
            }
        } catch (e: Exception) {
            // Wygłuszamy krytyczne wyjątki PSI, aby IDE kontynuowało pracę
        }
    }
}