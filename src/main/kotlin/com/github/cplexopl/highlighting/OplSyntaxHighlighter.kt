package com.github.cplexopl.highlighting

import com.github.cplexopl.lexer.OplLexer
import com.github.cplexopl.psi.OplTypes
import com.intellij.lexer.FlexAdapter // Added import
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

object OplHighlighterColors {
    val KEYWORD = createTextAttributesKey("OPL_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)
    val COMMENT = createTextAttributesKey("OPL_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
    val BLOCK_COMMENT = createTextAttributesKey("OPL_BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT)
    val STRING = createTextAttributesKey("OPL_STRING", DefaultLanguageHighlighterColors.STRING)
    val NUMBER = createTextAttributesKey("OPL_NUMBER", DefaultLanguageHighlighterColors.NUMBER)
    val IDENTIFIER = createTextAttributesKey("OPL_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER)
    val OPERATOR = createTextAttributesKey("OPL_OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN)
    val BRACES = createTextAttributesKey("OPL_BRACES", DefaultLanguageHighlighterColors.BRACES)
    val BAD_CHAR = createTextAttributesKey("OPL_BAD_CHAR", DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE)
}

class OplSyntaxHighlighter : SyntaxHighlighterBase() {

    // Fixed method: use FlexAdapter and pass null to OplLexer
    override fun getHighlightingLexer(): Lexer = FlexAdapter(OplLexer(null))

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            OplTypes.USING, OplTypes.WITH, OplTypes.MINIMIZE, OplTypes.MAXIMIZE,
            OplTypes.SUBJECT, OplTypes.FORALL, OplTypes.EXISTS, OplTypes.IN,
            OplTypes.TO, OplTypes.IF, OplTypes.ELSE, OplTypes.THEN,
            OplTypes.INT, OplTypes.INT_PLUS, OplTypes.FLOAT, OplTypes.FLOAT_PLUS,
            OplTypes.BOOLEAN, OplTypes.STRING, OplTypes.RANGE, OplTypes.TUPLE,
            OplTypes.STRUCT, OplTypes.INITIAL, OplTypes.CONSTRAINT, OplTypes.CONSTRAINTS,
            OplTypes.SETOF, OplTypes.KEY, OplTypes.DVAR, OplTypes.DEXPR,
            OplTypes.EXECUTE, OplTypes.MAIN, OplTypes.INCLUDE, OplTypes.ASSERT,
            OplTypes.SUM, OplTypes.ALL, OplTypes.CP, OplTypes.CPLEX,
            OplTypes.INTERVAL, OplTypes.SEQUENCE, OplTypes.PULSE, OplTypes.STEP,
            OplTypes.ALLDIFFERENT, OplTypes.PACK, OplTypes.SIZE, OplTypes.PIECEWISE,
            OplTypes.PWLFUNCTION, OplTypes.SHEETCONNECTION, OplTypes.SHEETREAD,
            OplTypes.SHEETWRITE, OplTypes.DBCONNECTION, OplTypes.DBREAD,
            OplTypes.DBEXECUTE, OplTypes.DBUPDATE, OplTypes.CUMULFUNCTION,
            OplTypes.STATEFUNCTION, OplTypes.STEPFUNCTION, OplTypes.INFINITY,
            OplTypes.MAXINT, OplTypes.TEMPLATE, OplTypes.PREPARE, OplTypes.INVOKE,
            OplTypes.ORDERED, OplTypes.SORTED, OplTypes.REVERSED, OplTypes.SYMDIFF,
            OplTypes.OPTIONAL, OplTypes.FROM, OplTypes.INTENSITY, OplTypes.TYPES,
            OplTypes.STEPWISE, OplTypes.DIV, OplTypes.INTER, OplTypes.UNION,
            OplTypes.DIFF, OplTypes.PROD, OplTypes.AND, OplTypes.OR,
            OplTypes.TRUE, OplTypes.FALSE ->
                arrayOf(OplHighlighterColors.KEYWORD)

            OplTypes.LINE_COMMENT -> arrayOf(OplHighlighterColors.COMMENT)
            OplTypes.BLOCK_COMMENT -> arrayOf(OplHighlighterColors.BLOCK_COMMENT)

            OplTypes.STRING_LITERAL -> arrayOf(OplHighlighterColors.STRING)
            OplTypes.INTEGER_LITERAL, OplTypes.FLOAT_LITERAL -> arrayOf(OplHighlighterColors.NUMBER)

            OplTypes.ID -> arrayOf(OplHighlighterColors.IDENTIFIER)

            OplTypes.PLUS, OplTypes.MINUS, OplTypes.STAR, OplTypes.SLASH,
            OplTypes.EQ, OplTypes.EQEQ, OplTypes.NEQ, OplTypes.LT, OplTypes.LE,
            OplTypes.GT, OplTypes.GE, OplTypes.ANDAND, OplTypes.OROR,
            OplTypes.NOT, OplTypes.MOD, OplTypes.IMPLIES, OplTypes.ARROW,
            OplTypes.PIPE, OplTypes.DOTDOT, OplTypes.ELLIPSIS, OplTypes.QUESTION,
            OplTypes.COLON, OplTypes.SEMICOLON, OplTypes.COMMA, OplTypes.DOT ->
                arrayOf(OplHighlighterColors.OPERATOR)

            OplTypes.LBRACE, OplTypes.RBRACE,
            OplTypes.LBRACKET, OplTypes.RBRACKET,
            OplTypes.LPAREN, OplTypes.RPAREN -> arrayOf(OplHighlighterColors.BRACES)

            TokenType.BAD_CHARACTER -> arrayOf(OplHighlighterColors.BAD_CHAR)

            else -> emptyArray()
        }
    }
}