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

            // --- Inspekcje: Nieliniowość (MIP) ---
            if (element is OplFactor) {
                val idNode = element.node.findChildByType(OplTypes.ID)
                if (idNode != null) {
                    val name = idNode.text
                    if (name == "min" || name == "max" || name == "abs") {
                        holder.newAnnotation(HighlightSeverity.WARNING, "Użycie funkcji nieliniowej '$name' może wydłużyć czas obliczeń (MIP).")
                            .range(idNode.textRange)
                            .create()
                    }
                }
            }

            // --- Inspekcje: Walidacja funkcji celu w szeregowaniu (CP) ---
            if (element is OplObjectiveDeclaration) {
                val file = element.containingFile
                if (file != null) {
                    val hasInterval = PsiTreeUtil.findChildrenOfType(file, OplDvarDeclaration::class.java).any {
                        it.node.findChildByType(OplTypes.INTERVAL) != null || it.node.findChildByType(OplTypes.SEQUENCE) != null
                    }
                    if (hasInterval) {
                        val objText = element.text
                        if (!objText.contains("endOf") && !objText.contains("lengthOf") && !objText.contains("startOf") && !objText.contains("startAtEnd")) {
                            val targetRange = element.node.findChildByType(OplTypes.MINIMIZE)?.textRange ?: element.node.findChildByType(OplTypes.MAXIMIZE)?.textRange ?: element.textRange
                            holder.newAnnotation(HighlightSeverity.WARNING, "Brak funkcji czasu (np. endOf) w funkcji celu przy szeregowaniu.")
                                .range(targetRange)
                                .create()
                        }
                    }
                }
            }

            // Analizujemy tylko tokeny zmiennych
            if (element.node.elementType == OplTypes.ID) {
                // 0. Omijamy bloki execute (skrypt JS, nie deklaracje OPL)
                var p = element.parent
                while (p != null && p !is com.intellij.psi.PsiFile) {
                    if (p.node.elementType.toString() == "EXECUTE_BLOCK") return
                    p = p.parent
                }

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

                    // --- 2. Scope-aware analysis (Zasięg w pętlach i wielokrotnych iteratorach) ---
                    var isLocalVariable = false
                    var currentNode: PsiElement? = element.parent

                    while (currentNode != null && currentNode !is com.intellij.psi.PsiFile) {

                        // KROK 1: Sprawdzamy, czy obecny węzeł posiada pod-węzły typu ITERATOR (np. w FORALL lub SUM)
                        val children = currentNode.node.getChildren(null)
                        for (child in children) {
                            if (child.elementType.toString().contains("ITERATOR")) {
                                // Pobieramy WSZYSTKIE identyfikatory z iteratora, aby obsłużyć wpisy takie jak: forall(i, j in 1..10)
                                val idNodes = child.getChildren(null).filter { it.elementType == OplTypes.ID }
                                if (idNodes.any { it.text == name }) {
                                    isLocalVariable = true
                                    break
                                }
                            }
                        }
                        if (isLocalVariable) break // Znaleźliśmy zmienną w iteratorze obok, przerywamy!

                        // KROK 2: Sprawdzamy, czy sami nie jesteśmy wewnątrz iteratora (np. warunek given[i][j] != 0)
                        if (currentNode.node.elementType.toString().contains("ITERATOR")) {
                            val idNodes = currentNode.node.getChildren(null).filter { it.elementType == OplTypes.ID }
                            if (idNodes.any { it.text == name }) {
                                isLocalVariable = true
                                break
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
        } catch (_: Exception) {
            // Wygłuszamy krytyczne wyjątki PSI, aby IDE kontynuowało pracę
        }
    }
}