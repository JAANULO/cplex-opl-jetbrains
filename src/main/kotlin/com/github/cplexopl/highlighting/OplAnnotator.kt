package com.github.cplexopl.highlighting

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.lang.annotation.HighlightSeverity
import com.github.cplexopl.psi.*

class OplAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        // Interesują nas tylko węzły reprezentujące identyfikatory (zmienne/funkcje)
        if (element.node.elementType == OplTypes.ID) {
            val name = element.text
            val parent = element.parent

            // 1. Zignoruj słowa wbudowane CPLEX, które nie są deklarowane przez użytkownika
            val builtins = setOf("abs", "ceil", "floor", "round", "sqrt", "log", "exp", "max", "min", "card")
            if (builtins.contains(name)) return

            // 2. Check if we are exactly at the DECLARATION name (left side), not the assignment (right side)
            val isVarDecl = parent is OplVarDeclaration && parent.node.findChildByType(OplTypes.ID)?.psi == element
            val isDvarDecl = parent is OplDvarDeclaration && parent.node.findChildByType(OplTypes.ID)?.psi == element
            val isTupleDecl = parent is OplTupleDeclaration && parent.node.findChildByType(OplTypes.ID)?.psi == element
            val isConstraintLabel = parent is OplConstraintItem && parent.node.findChildByType(OplTypes.ID)?.psi == element
            val isSumIterator = parent is OplFactor && parent.node.findChildByType(OplTypes.SUM) != null && parent.node.findChildByType(OplTypes.ID)?.psi == element

            if (isVarDecl || isDvarDecl || isTupleDecl || isConstraintLabel || isSumIterator) return

            // 3. This is a "usage" of a variable. Scan the file for legal declarations.
            val file = element.containingFile
            val definedVariables = mutableSetOf<String>()

            fun extractId(psiElement: PsiElement) {
                psiElement.node.findChildByType(OplTypes.ID)?.text?.let { definedVariables.add(it) }
            }

            PsiTreeUtil.findChildrenOfType(file, OplVarDeclaration::class.java).forEach { extractId(it) }
            PsiTreeUtil.findChildrenOfType(file, OplDvarDeclaration::class.java).forEach { extractId(it) }
            PsiTreeUtil.findChildrenOfType(file, OplTupleDeclaration::class.java).forEach { extractId(it) }
            PsiTreeUtil.findChildrenOfType(file, OplConstraintItem::class.java).forEach { extractId(it) }
            PsiTreeUtil.findChildrenOfType(file, OplFactor::class.java).forEach {
                if (it.node.findChildByType(OplTypes.SUM) != null) extractId(it)
            }

            // 4. Verdict: If the variable is not defined, mark it as an error
            if (!definedVariables.contains(name)) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Undefined variable: '$name'")
                    .range(element.textRange)
                    .create()
            }
        }
    }
}