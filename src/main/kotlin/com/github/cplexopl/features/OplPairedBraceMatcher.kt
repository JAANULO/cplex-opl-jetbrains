package com.github.cplexopl.features

import com.github.cplexopl.psi.OplTypes
import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType

class OplPairedBraceMatcher : PairedBraceMatcher {
    private val pairs = arrayOf(
        BracePair(OplTypes.LBRACE, OplTypes.RBRACE, true),
        BracePair(OplTypes.LPAREN, OplTypes.RPAREN, false),
        BracePair(OplTypes.LBRACKET, OplTypes.RBRACKET, false)
    )

    override fun getPairs(): Array<BracePair> = pairs
    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?) = true
    override fun getCodeConstructStart(file: PsiFile, openingBraceOffset: Int): Int = openingBraceOffset
}