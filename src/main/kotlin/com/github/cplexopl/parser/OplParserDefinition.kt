package com.github.cplexopl.parser

import com.github.cplexopl.OplLanguage
import com.github.cplexopl.lexer.OplLexer
import com.github.cplexopl.psi.OplFile
import com.github.cplexopl.psi.OplTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class OplParserDefinition : ParserDefinition {
    companion object {
        val FILE = IFileElementType(OplLanguage)
        val COMMENTS = TokenSet.create(OplTypes.LINE_COMMENT, OplTypes.BLOCK_COMMENT)
        val STRING_LITERALS = TokenSet.create(OplTypes.STRING_LITERAL)
    }

    override fun createLexer(project: Project?): Lexer = FlexAdapter(OplLexer(null))
    override fun createParser(project: Project?): PsiParser = OplParser()
    override fun getFileNodeType(): IFileElementType = FILE
    override fun getCommentTokens(): TokenSet = COMMENTS
    override fun getStringLiteralElements(): TokenSet = STRING_LITERALS
    override fun createElement(node: ASTNode?): PsiElement = OplTypes.Factory.createElement(node)
    override fun createFile(viewProvider: FileViewProvider): PsiFile = OplFile(viewProvider)
}