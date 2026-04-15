package com.github.cplexopl.reference

import com.github.cplexopl.psi.*
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil

class OplReference(
    element: PsiElement,
    textRange: TextRange,
    private val variableName: String
) : PsiReferenceBase<PsiElement>(element, textRange) {

    override fun resolve(): PsiElement? {
        // ZAWSZE używamy aktywnego widoku z edytora, ignorując pliki na dysku
        val file = element.containingFile ?: return null
        val targetName = variableName.trim()

        // Funkcja celownicza: wyciąga samą literę z bloku deklaracji
        fun extractTargetToken(node: PsiElement): PsiElement? {
            val ids = node.node.getChildren(null).filter { it.elementType == OplTypes.ID }
            return ids.firstOrNull { it.text == targetName }?.psi
        }

        // Skanujemy po kolei wszystkie typy
        val dvars = PsiTreeUtil.findChildrenOfType(file, OplDvarDeclaration::class.java)
        for (dvar in dvars) { extractTargetToken(dvar)?.let { return it } }

        val vars = PsiTreeUtil.findChildrenOfType(file, OplVarDeclaration::class.java)
        for (v in vars) { extractTargetToken(v)?.let { return it } }

        val tuples = PsiTreeUtil.findChildrenOfType(file, OplTupleDeclaration::class.java)
        for (t in tuples) { extractTargetToken(t)?.let { return it } }

        val constraints = PsiTreeUtil.findChildrenOfType(file, OplConstraintItem::class.java)
        for (c in constraints) { extractTargetToken(c)?.let { return it } }

        return null
    }

    override fun getVariants(): Array<Any> = emptyArray()
}