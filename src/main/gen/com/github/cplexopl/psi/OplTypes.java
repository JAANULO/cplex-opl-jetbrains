// This is a generated file. Not intended for manual editing.
package com.github.cplexopl.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.github.cplexopl.psi.impl.*;

public interface OplTypes {

  IElementType CONSTRAINT_ITEM = new OplElementType("CONSTRAINT_ITEM");
  IElementType CONSTRAINT_SECTION = new OplElementType("CONSTRAINT_SECTION");
  IElementType DECLARATION = new OplElementType("DECLARATION");
  IElementType DVAR_DECLARATION = new OplElementType("DVAR_DECLARATION");
  IElementType EXECUTE_BLOCK = new OplElementType("EXECUTE_BLOCK");
  IElementType EXECUTE_BODY = new OplElementType("EXECUTE_BODY");
  IElementType EXPRESSION = new OplElementType("EXPRESSION");
  IElementType FACTOR = new OplElementType("FACTOR");
  IElementType INCLUDE_DECLARATION = new OplElementType("INCLUDE_DECLARATION");
  IElementType OBJECTIVE_DECLARATION = new OplElementType("OBJECTIVE_DECLARATION");
  IElementType OPL_ITERATOR = new OplElementType("OPL_ITERATOR");
  IElementType RANGE_EXPRESSION = new OplElementType("RANGE_EXPRESSION");
  IElementType TERM = new OplElementType("TERM");
  IElementType TUPLE_DECLARATION = new OplElementType("TUPLE_DECLARATION");
  IElementType TUPLE_FIELD = new OplElementType("TUPLE_FIELD");
  IElementType USING_DECLARATION = new OplElementType("USING_DECLARATION");
  IElementType VAR_DECLARATION = new OplElementType("VAR_DECLARATION");

  IElementType ALL = new OplTokenType("all");
  IElementType ALLDIFFERENT = new OplTokenType("allDifferent");
  IElementType ANDAND = new OplTokenType("&&");
  IElementType ASSERT = new OplTokenType("assert");
  IElementType BLOCK_COMMENT = new OplTokenType("BLOCK_COMMENT");
  IElementType BOOLEAN = new OplTokenType("boolean");
  IElementType COLON = new OplTokenType(":");
  IElementType COMMA = new OplTokenType(",");
  IElementType CP = new OplTokenType("cp");
  IElementType DEXPR = new OplTokenType("dexpr");
  IElementType DOT = new OplTokenType(".");
  IElementType DOTDOT = new OplTokenType("..");
  IElementType DVAR = new OplTokenType("dvar");
  IElementType ELLIPSIS = new OplTokenType("...");
  IElementType ELSE = new OplTokenType("else");
  IElementType EQ = new OplTokenType("=");
  IElementType EQEQ = new OplTokenType("==");
  IElementType EXECUTE = new OplTokenType("execute");
  IElementType EXISTS = new OplTokenType("exists");
  IElementType FLOAT = new OplTokenType("float");
  IElementType FLOAT_LITERAL = new OplTokenType("FLOAT_LITERAL");
  IElementType FORALL = new OplTokenType("forall");
  IElementType GE = new OplTokenType(">=");
  IElementType GT = new OplTokenType(">");
  IElementType ID = new OplTokenType("ID");
  IElementType IF = new OplTokenType("if");
  IElementType IN = new OplTokenType("in");
  IElementType INCLUDE = new OplTokenType("include");
  IElementType INT = new OplTokenType("int");
  IElementType INTEGER_LITERAL = new OplTokenType("INTEGER_LITERAL");
  IElementType INTERVAL = new OplTokenType("interval");
  IElementType LBRACE = new OplTokenType("{");
  IElementType LBRACKET = new OplTokenType("[");
  IElementType LE = new OplTokenType("<=");
  IElementType LINE_COMMENT = new OplTokenType("LINE_COMMENT");
  IElementType LPAREN = new OplTokenType("(");
  IElementType LT = new OplTokenType("<");
  IElementType MAXIMIZE = new OplTokenType("maximize");
  IElementType MINIMIZE = new OplTokenType("minimize");
  IElementType MINUS = new OplTokenType("-");
  IElementType MOD = new OplTokenType("%");
  IElementType NEQ = new OplTokenType("!=");
  IElementType NOT = new OplTokenType("!");
  IElementType OROR = new OplTokenType("||");
  IElementType PACK = new OplTokenType("pack");
  IElementType PLUS = new OplTokenType("+");
  IElementType PULSE = new OplTokenType("pulse");
  IElementType RANGE = new OplTokenType("range");
  IElementType RBRACE = new OplTokenType("}");
  IElementType RBRACKET = new OplTokenType("]");
  IElementType RPAREN = new OplTokenType(")");
  IElementType SEMICOLON = new OplTokenType(";");
  IElementType SEQUENCE = new OplTokenType("sequence");
  IElementType SLASH = new OplTokenType("/");
  IElementType STAR = new OplTokenType("*");
  IElementType STEP = new OplTokenType("step");
  IElementType STRING = new OplTokenType("string");
  IElementType STRING_LITERAL = new OplTokenType("STRING_LITERAL");
  IElementType SUBJECT_TO = new OplTokenType("subject to");
  IElementType SUM = new OplTokenType("sum");
  IElementType THEN = new OplTokenType("then");
  IElementType TO = new OplTokenType("to");
  IElementType TUPLE = new OplTokenType("tuple");
  IElementType USING = new OplTokenType("using");
  IElementType WITH = new OplTokenType("with");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == CONSTRAINT_ITEM) {
        return new OplConstraintItemImpl(node);
      }
      else if (type == CONSTRAINT_SECTION) {
        return new OplConstraintSectionImpl(node);
      }
      else if (type == DECLARATION) {
        return new OplDeclarationImpl(node);
      }
      else if (type == DVAR_DECLARATION) {
        return new OplDvarDeclarationImpl(node);
      }
      else if (type == EXECUTE_BLOCK) {
        return new OplExecuteBlockImpl(node);
      }
      else if (type == EXECUTE_BODY) {
        return new OplExecuteBodyImpl(node);
      }
      else if (type == EXPRESSION) {
        return new OplExpressionImpl(node);
      }
      else if (type == FACTOR) {
        return new OplFactorImpl(node);
      }
      else if (type == INCLUDE_DECLARATION) {
        return new OplIncludeDeclarationImpl(node);
      }
      else if (type == OBJECTIVE_DECLARATION) {
        return new OplObjectiveDeclarationImpl(node);
      }
      else if (type == OPL_ITERATOR) {
        return new OplOplIteratorImpl(node);
      }
      else if (type == RANGE_EXPRESSION) {
        return new OplRangeExpressionImpl(node);
      }
      else if (type == TERM) {
        return new OplTermImpl(node);
      }
      else if (type == TUPLE_DECLARATION) {
        return new OplTupleDeclarationImpl(node);
      }
      else if (type == TUPLE_FIELD) {
        return new OplTupleFieldImpl(node);
      }
      else if (type == USING_DECLARATION) {
        return new OplUsingDeclarationImpl(node);
      }
      else if (type == VAR_DECLARATION) {
        return new OplVarDeclarationImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
