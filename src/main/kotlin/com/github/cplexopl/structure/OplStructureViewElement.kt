package com.github.cplexopl.structure

import com.github.cplexopl.psi.*
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.NavigationItem
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.icons.AllIcons
import javax.swing.Icon

class OplStructureViewElement(private val element: PsiElement) : StructureViewTreeElement, NavigationItem {

    override fun getValue(): Any = element

    override fun navigate(requestFocus: Boolean) {
        (element as? NavigationItem)?.navigate(requestFocus)
    }

    override fun canNavigate(): Boolean = (element as? NavigationItem)?.canNavigate() ?: false
    override fun canNavigateToSource(): Boolean = (element as? NavigationItem)?.canNavigateToSource() ?: false
    override fun getName(): String? = (element as? NavigationItem)?.name

    override fun getPresentation(): ItemPresentation {
        return object : ItemPresentation {
            override fun getPresentableText(): String {
                return when (element) {
                    is OplVarDeclaration -> "var " + (element.node.findChildByType(OplTypes.ID)?.text ?: "")
                    is OplDvarDeclaration -> "dvar " + (element.node.findChildByType(OplTypes.ID)?.text ?: "")
                    is OplTupleDeclaration -> "tuple " + (element.node.findChildByType(OplTypes.ID)?.text ?: "")
                    is OplObjectiveDeclaration -> if (element.text.contains("minimize")) "minimize" else "maximize"
                    is OplConstraintItem -> element.node.findChildByType(OplTypes.ID)?.text ?: "constraint"
                    is OplConstraintSection -> "subject to"
                    else -> (element as? com.intellij.psi.PsiFile)?.name ?: ""
                }
            }

            override fun getLocationString(): String? = null

            override fun getIcon(unused: Boolean): Icon? {
                return when (element) {
                    is OplVarDeclaration -> AllIcons.Nodes.Variable
                    is OplDvarDeclaration -> AllIcons.Nodes.Field
                    is OplTupleDeclaration -> AllIcons.Nodes.Class
                    is OplObjectiveDeclaration -> AllIcons.Nodes.Target
                    is OplConstraintItem -> AllIcons.Nodes.Property
                    else -> AllIcons.Nodes.Package
                }
            }
        }
    }

    override fun getChildren(): Array<TreeElement> {
        val children = mutableListOf<TreeElement>()

        if (element is com.intellij.psi.PsiFile) {
            // Scan tree looking for specific types, ignoring wrapper nodes (e.g. OplDeclaration)
            PsiTreeUtil.findChildrenOfType(element, OplVarDeclaration::class.java).forEach { children.add(OplStructureViewElement(it)) }
            PsiTreeUtil.findChildrenOfType(element, OplDvarDeclaration::class.java).forEach { children.add(OplStructureViewElement(it)) }
            PsiTreeUtil.findChildrenOfType(element, OplTupleDeclaration::class.java).forEach { children.add(OplStructureViewElement(it)) }
            PsiTreeUtil.findChildrenOfType(element, OplObjectiveDeclaration::class.java).forEach { children.add(OplStructureViewElement(it)) }
            PsiTreeUtil.findChildrenOfType(element, OplConstraintSection::class.java).forEach { children.add(OplStructureViewElement(it)) }
        } else if (element is OplConstraintSection) {
            // Inside 'subject to' section search for individual constraints
            PsiTreeUtil.findChildrenOfType(element, OplConstraintItem::class.java).forEach {
                children.add(OplStructureViewElement(it))
            }
        }

        return children.toTypedArray()
    }
}