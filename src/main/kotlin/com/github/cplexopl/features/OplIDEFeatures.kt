package com.github.cplexopl.features

import com.github.cplexopl.lexer.OplLexer
import com.github.cplexopl.parser.OplParserDefinition
import com.github.cplexopl.psi.OplNamedElement
import com.github.cplexopl.psi.OplTypes
import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.lang.refactoring.RefactoringSupportProvider
import com.intellij.lexer.FlexAdapter
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.tree.TokenSet

// 1. Support for refactoring "Rename" (Change Name)
class OplRefactoringSupportProvider : RefactoringSupportProvider() {
    override fun isMemberInplaceRenameAvailable(element: PsiElement, context: PsiElement?): Boolean {
        return element is OplNamedElement
    }
}

// 2. Support for searching usages (Find Usages)
class OplFindUsagesProvider : FindUsagesProvider {

    override fun getWordsScanner(): WordsScanner? {
        return DefaultWordsScanner(
            FlexAdapter(OplLexer(null)),
            TokenSet.create(OplTypes.ID),
            OplParserDefinition.COMMENTS,
            OplParserDefinition.STRING_LITERALS
        )
    }

    override fun canFindUsagesFor(psiElement: PsiElement): Boolean {
        return psiElement is PsiNamedElement
    }

    override fun getHelpId(psiElement: PsiElement): String? = null

    override fun getType(element: PsiElement): String {
        return "Variable"
    }

    override fun getDescriptiveName(element: PsiElement): String {
        return if (element is PsiNamedElement) element.name ?: "" else ""
    }

    override fun getNodeText(element: PsiElement, useFullName: Boolean): String {
        return getDescriptiveName(element)
    }
}
