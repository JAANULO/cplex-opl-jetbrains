package com.github.cplexopl.structure

import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.github.cplexopl.psi.*
import com.intellij.icons.AllIcons
import javax.swing.Icon

class OplStructureViewElement(psiElement: PsiElement) : PsiTreeElementBase<PsiElement>(psiElement) {

    override fun getChildrenBase(): MutableCollection<StructureViewTreeElement> {
        val elements = mutableListOf<StructureViewTreeElement>()
        val currentElement = element ?: return elements

        // Jeśli jesteśmy na poziomie głównego pliku, szukamy jego dzieci
        if (currentElement.containingFile == currentElement) {

            // 1. Zmienne (Dane i Decyzyjne)
            PsiTreeUtil.findChildrenOfType(currentElement, OplVarDeclaration::class.java).forEach { elements.add(OplStructureViewElement(it)) }
            PsiTreeUtil.findChildrenOfType(currentElement, OplDvarDeclaration::class.java).forEach { elements.add(OplStructureViewElement(it)) }

            // 2. Funkcje celu
            PsiTreeUtil.findChildrenOfType(currentElement, OplObjectiveDeclaration::class.java).forEach { elements.add(OplStructureViewElement(it)) }

            // 3. Ograniczenia
            PsiTreeUtil.findChildrenOfType(currentElement, OplConstraintItem::class.java).forEach { elements.add(OplStructureViewElement(it)) }
        }
        return elements
    }

    override fun getPresentableText(): String? {
        val el = element ?: return null
        return when (el) {
            is OplVarDeclaration -> el.node.findChildByType(OplTypes.ID)?.text ?: "Variable"
            is OplDvarDeclaration -> el.node.findChildByType(OplTypes.ID)?.text ?: "Decision Variable"
            is OplObjectiveDeclaration -> "Objective Function"
            is OplConstraintItem -> el.node.findChildByType(OplTypes.ID)?.text ?: "Constraint"
            else -> el.containingFile.name
        }
    }

    override fun getIcon(open: Boolean): Icon? {
        val el = element ?: return null
        return when (el) {
            is OplVarDeclaration -> AllIcons.Nodes.Variable
            is OplDvarDeclaration -> AllIcons.Nodes.Parameter
            is OplObjectiveDeclaration -> AllIcons.Nodes.Function
            is OplConstraintItem -> AllIcons.Nodes.Tag
            else -> null
        }
    }
}