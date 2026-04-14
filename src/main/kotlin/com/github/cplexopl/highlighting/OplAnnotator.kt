package com.github.cplexopl.highlighting

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.lang.annotation.HighlightSeverity
import com.github.cplexopl.psi.*

class OplAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        // --- ZADANIE 3.2: Walidacja typów ---
        // Sprawdzamy błąd bezpośrednio na głównym węźle deklaracji
        if (element is OplDvarDeclaration) {
            val isBoolean = element.node.findChildByType(OplTypes.BOOLEAN) != null
            val hasRange = element.node.findChildByType(OplTypes.IN) != null

            if (isBoolean && hasRange) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Type 'boolean' cannot have a range ('in' clause)")
                    .range(element.textRange)
                    .create()
            }
        }

        // Analizujemy tylko węzły typu ID (nazwy zmiennych)
        if (element.node.elementType == OplTypes.ID) {
            val name = element.text
            val parent = element.parent

            // 1. Zignoruj słowa wbudowane CPLEX
            val builtins = setOf("abs", "ceil", "floor", "round", "sqrt", "log", "exp", "max", "min", "card")
            if (builtins.contains(name)) return

            // 2. Rozróżniamy miejsce w kodzie: czy jesteśmy w DEKLARACJI czy w UŻYCIU zmiennej?
            val isVarDecl = parent is OplVarDeclaration && parent.node.findChildByType(OplTypes.ID)?.psi == element
            val isDvarDecl = parent is OplDvarDeclaration && parent.node.findChildByType(OplTypes.ID)?.psi == element
            val isTupleDecl = parent is OplTupleDeclaration && parent.node.findChildByType(OplTypes.ID)?.psi == element
            val isConstraintLabel = parent is OplConstraintItem && parent.node.findChildByType(OplTypes.ID)?.psi == element
            val isSumIterator = parent is OplFactor && parent.node.findChildByType(OplTypes.SUM) != null && parent.node.findChildByType(OplTypes.ID)?.psi == element

            val isDeclaration = isVarDecl || isDvarDecl || isTupleDecl || isConstraintLabel || isSumIterator

            // 3. Budujemy słownik wszystkich zmiennych w pliku wraz z ilością ich wystąpień (jako deklaracji)
            val file = element.containingFile
            val declaredVariables = mutableMapOf<String, MutableList<PsiElement>>()

            fun registerDeclaration(psiElement: PsiElement) {
                val idNode = psiElement.node.findChildByType(OplTypes.ID)?.psi
                if (idNode != null) {
                    val idText = idNode.text
                    declaredVariables.getOrPut(idText) { mutableListOf() }.add(idNode)
                }
            }

            // Skanowanie całego drzewa (uruchamiane bardzo szybko przez IDE)
            PsiTreeUtil.findChildrenOfType(file, OplVarDeclaration::class.java).forEach { registerDeclaration(it) }
            PsiTreeUtil.findChildrenOfType(file, OplDvarDeclaration::class.java).forEach { registerDeclaration(it) }
            PsiTreeUtil.findChildrenOfType(file, OplTupleDeclaration::class.java).forEach { registerDeclaration(it) }
            PsiTreeUtil.findChildrenOfType(file, OplConstraintItem::class.java).forEach { registerDeclaration(it) }
            PsiTreeUtil.findChildrenOfType(file, OplFactor::class.java).forEach {
                if (it.node.findChildByType(OplTypes.SUM) != null) registerDeclaration(it)
            }

            // 4. Logika sprawdzania błędów
            if (isDeclaration) {
                // Sprawdzanie duplikatów
                val declarationsList = declaredVariables[name]
                if (declarationsList != null && declarationsList.size > 1 && declarationsList.indexOf(element) > 0) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Variable '$name' is already defined")
                        .range(element.textRange)
                        .create()
                }

                // Sprawdzanie brakującego średnika na końcu deklaracji
                val lastChild = parent.node.lastChildNode
                if (lastChild?.elementType != OplTypes.SEMICOLON) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Missing semicolon ';'")
                        .range(com.intellij.openapi.util.TextRange.create(parent.textRange.endOffset, parent.textRange.endOffset))
                        .create()
                }

            } else {
                // Jeśli jesteśmy w użyciu (zwykłe wywołanie zmiennej), sprawdzamy czy w ogóle istnieje
                if (!declaredVariables.containsKey(name)) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Undefined variable: '$name'")
                        .range(element.textRange)
                        .create()
                }
            }
        }
    }
}