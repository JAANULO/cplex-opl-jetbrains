package com.github.cplexopl.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiNameIdentifierOwner
import com.github.cplexopl.OplLanguage

// 1. Interfejs do zmiany nazwy
interface OplNamedElement : PsiNameIdentifierOwner

// 2. Implementacja dla mixinów z Grammar-Kit
abstract class OplNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), OplNamedElement {

    override fun getNameIdentifier(): PsiElement? {
        return findChildByType(OplTypes.ID)
    }

    override fun getName(): String? {
        return nameIdentifier?.text
    }

    override fun setName(name: String): PsiElement {
        val keyNode = nameIdentifier?.node
        if (keyNode != null) {
            val newElement = OplElementFactory.createIdentifier(project, name)
            node.replaceChild(keyNode, newElement.node)
        }
        return this
    }
}

// 3. Fabryka drzewa AST używana przy refaktoryzacji
object OplElementFactory {
    fun createIdentifier(project: Project, name: String): PsiElement {
        val file = createFile(project, "dvar int $name;")
        val declaration = file.firstChild // item_ (first is declaration)
        return declaration.node.findChildByType(OplTypes.ID)?.psi ?: file.firstChild
    }

    fun createFile(project: Project, text: String): OplFile {
        return PsiFileFactory.getInstance(project).createFileFromText("dummy.mod", OplLanguage, text) as OplFile
    }
}
