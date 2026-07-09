package com.github.cplexopl.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.github.cplexopl.psi.OplTypes;
import com.intellij.psi.TokenType;

%%

// Dyrektywy JFlex - narzędzia generującego Lexer z tej specyfikacji
%class OplLexer
%public
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{ return;
%eof}

// Makra - nazwane wyrażenia regularne wielokrotnego użytku
CRLF            = \r\n|\r|\n
WHITE_SPACE     = [ \t\f]
ID              = [a-zA-Z_][a-zA-Z0-9_]*
DIGIT           = [0-9]
INTEGER         = {DIGIT}+
// Float wymaga teraz co najmniej jednej cyfry po kropce, aby nie kolidować z "1.."
FLOAT           = {DIGIT}+\.{DIGIT}+([eE][+-]?{DIGIT}+)?
STRING          = \"([^\"\\\n]|\\.)*\"
LINE_COMMENT    = "//"[^\r\n]*
BLOCK_COMMENT   = "/*"([^*]|\*[^/])*"*/"

%%

// Reguły dopasowania - kolejność ma znaczenie!
// Lexer sprawdza reguły od góry, bierze pierwszą pasującą.

// Białe znaki (whitespace) - pomijamy
{WHITE_SPACE}+          { return TokenType.WHITE_SPACE; }
{CRLF}                  { return TokenType.WHITE_SPACE; }

// Komentarze
{LINE_COMMENT}          { return OplTypes.LINE_COMMENT; }
{BLOCK_COMMENT}         { return OplTypes.BLOCK_COMMENT; }

// Słowa kluczowe - MUSZĄ być przed regułą ID
"CP"                    { return OplTypes.CP; }
"interval"              { return OplTypes.INTERVAL; }
"sequence"              { return OplTypes.SEQUENCE; }
"using"                 { return OplTypes.USING; }
"with"                  { return OplTypes.WITH; }
"minimize"              { return OplTypes.MINIMIZE; }
"maximize"              { return OplTypes.MAXIMIZE; }
"subject"               { return OplTypes.SUBJECT; }
"forall"                { return OplTypes.FORALL; }
"exists"                { return OplTypes.EXISTS; }
"in"                    { return OplTypes.IN; }
"to"                    { return OplTypes.TO; }
"if"                    { return OplTypes.IF; }
"else"                  { return OplTypes.ELSE; }
"then"                  { return OplTypes.THEN; }
"int"                   { return OplTypes.INT; }
"float"                 { return OplTypes.FLOAT; }
"boolean"               { return OplTypes.BOOLEAN; }
"string"                { return OplTypes.STRING; }
"range"                 { return OplTypes.RANGE; }
"tuple"                 { return OplTypes.TUPLE; }
"dvar"                  { return OplTypes.DVAR; }
"dexpr"                 { return OplTypes.DEXPR; }
"execute"               { return OplTypes.EXECUTE; }
"include"               { return OplTypes.INCLUDE; }
"assert"                { return OplTypes.ASSERT; }
"sum"                   { return OplTypes.SUM; }
"all"                   { return OplTypes.ALL; }
"pulse"                 { return OplTypes.PULSE; }
"step"                  { return OplTypes.STEP; }
"allDifferent"          { return OplTypes.ALLDIFFERENT; }
"pack"                  { return OplTypes.PACK; }

// Literały
{STRING}                { return OplTypes.STRING_LITERAL; }
{FLOAT}                 { return OplTypes.FLOAT_LITERAL; }
{INTEGER}               { return OplTypes.INTEGER_LITERAL; }

// Identyfikator (zmienna, nazwa)
{ID}                    { return OplTypes.ID; }

// Operatory wieloznakowe (Kolejność: najpierw najdłuższe!)
"..."                   { return OplTypes.ELLIPSIS; }
".."                    { return OplTypes.DOTDOT; }
"=="                    { return OplTypes.EQEQ; }
"<="                    { return OplTypes.LE; }
">="                    { return OplTypes.GE; }
"!="                    { return OplTypes.NEQ; }
"&&"                    { return OplTypes.ANDAND; }
"||"                    { return OplTypes.OROR; }
"!"                     { return OplTypes.NOT; }
"%"                     { return OplTypes.MOD; }

// Operatory jednozkakowe
";"                     { return OplTypes.SEMICOLON; }
":"                     { return OplTypes.COLON; }
","                     { return OplTypes.COMMA; }
"."                     { return OplTypes.DOT; }
"="                     { return OplTypes.EQ; }
"<"                     { return OplTypes.LT; }
">"                     { return OplTypes.GT; }
"+"                     { return OplTypes.PLUS; }
"-"                     { return OplTypes.MINUS; }
"*"                     { return OplTypes.STAR; }
"/"                     { return OplTypes.SLASH; }
"{"                     { return OplTypes.LBRACE; }
"}"                     { return OplTypes.RBRACE; }
"["                     { return OplTypes.LBRACKET; }
"]"                     { return OplTypes.RBRACKET; }
"("                     { return OplTypes.LPAREN; }
")"                     { return OplTypes.RPAREN; }

// Nieznany znak - zwracamy jako błąd
[^]                     { return TokenType.BAD_CHARACTER; }
