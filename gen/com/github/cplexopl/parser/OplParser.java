// This is a generated file. Not intended for manual editing.
package com.github.cplexopl.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.github.cplexopl.psi.OplTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class OplParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return oplFile(b, l + 1);
  }

  /* ********************************************************** */
  // (ID COLON)? expression (LT | LE | GT | GE | EQ | NEQ) expression SEMICOLON
  //                  | FORALL LPAREN ID IN expression RPAREN LBRACE constraintItem* RBRACE
  public static boolean constraintItem(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constraintItem")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CONSTRAINT_ITEM, "<constraint item>");
    r = constraintItem_0(b, l + 1);
    if (!r) r = constraintItem_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (ID COLON)? expression (LT | LE | GT | GE | EQ | NEQ) expression SEMICOLON
  private static boolean constraintItem_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constraintItem_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = constraintItem_0_0(b, l + 1);
    r = r && expression(b, l + 1);
    r = r && constraintItem_0_2(b, l + 1);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // (ID COLON)?
  private static boolean constraintItem_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constraintItem_0_0")) return false;
    constraintItem_0_0_0(b, l + 1);
    return true;
  }

  // ID COLON
  private static boolean constraintItem_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constraintItem_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ID, COLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // LT | LE | GT | GE | EQ | NEQ
  private static boolean constraintItem_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constraintItem_0_2")) return false;
    boolean r;
    r = consumeToken(b, LT);
    if (!r) r = consumeToken(b, LE);
    if (!r) r = consumeToken(b, GT);
    if (!r) r = consumeToken(b, GE);
    if (!r) r = consumeToken(b, EQ);
    if (!r) r = consumeToken(b, NEQ);
    return r;
  }

  // FORALL LPAREN ID IN expression RPAREN LBRACE constraintItem* RBRACE
  private static boolean constraintItem_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constraintItem_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, FORALL, LPAREN, ID, IN);
    r = r && expression(b, l + 1);
    r = r && consumeTokens(b, 0, RPAREN, LBRACE);
    r = r && constraintItem_1_7(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // constraintItem*
  private static boolean constraintItem_1_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constraintItem_1_7")) return false;
    while (true) {
      int c = current_position_(b);
      if (!constraintItem(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "constraintItem_1_7", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // SUBJECT_TO LBRACE constraintItem* RBRACE
  public static boolean constraintSection(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constraintSection")) return false;
    if (!nextTokenIs(b, SUBJECT_TO)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, SUBJECT_TO, LBRACE);
    r = r && constraintSection_2(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, m, CONSTRAINT_SECTION, r);
    return r;
  }

  // constraintItem*
  private static boolean constraintSection_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constraintSection_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!constraintItem(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "constraintSection_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // varDeclaration
  //               | dvarDeclaration
  //               | objectiveDeclaration
  //               | constraintSection
  //               | executeBlock
  //               | tupleDeclaration
  //               | includeDeclaration
  public static boolean declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DECLARATION, "<declaration>");
    r = varDeclaration(b, l + 1);
    if (!r) r = dvarDeclaration(b, l + 1);
    if (!r) r = objectiveDeclaration(b, l + 1);
    if (!r) r = constraintSection(b, l + 1);
    if (!r) r = executeBlock(b, l + 1);
    if (!r) r = tupleDeclaration(b, l + 1);
    if (!r) r = includeDeclaration(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // DVAR (INT | FLOAT | BOOLEAN) ID (IN rangeExpression)? SEMICOLON
  public static boolean dvarDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dvarDeclaration")) return false;
    if (!nextTokenIs(b, DVAR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DVAR);
    r = r && dvarDeclaration_1(b, l + 1);
    r = r && consumeToken(b, ID);
    r = r && dvarDeclaration_3(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, DVAR_DECLARATION, r);
    return r;
  }

  // INT | FLOAT | BOOLEAN
  private static boolean dvarDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dvarDeclaration_1")) return false;
    boolean r;
    r = consumeToken(b, INT);
    if (!r) r = consumeToken(b, FLOAT);
    if (!r) r = consumeToken(b, BOOLEAN);
    return r;
  }

  // (IN rangeExpression)?
  private static boolean dvarDeclaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dvarDeclaration_3")) return false;
    dvarDeclaration_3_0(b, l + 1);
    return true;
  }

  // IN rangeExpression
  private static boolean dvarDeclaration_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dvarDeclaration_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IN);
    r = r && rangeExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // EXECUTE (ID)? LBRACE .* RBRACE
  public static boolean executeBlock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "executeBlock")) return false;
    if (!nextTokenIs(b, EXECUTE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EXECUTE);
    r = r && executeBlock_1(b, l + 1);
    r = r && consumeToken(b, LBRACE);
    exit_section_(b, m, EXECUTE_BLOCK, r);
    return r;
  }

  // (ID)?
  private static boolean executeBlock_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "executeBlock_1")) return false;
    consumeToken(b, ID);
    return true;
  }

  /* ********************************************************** */
  // term ((PLUS | MINUS) term)*
  public static boolean expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXPRESSION, "<expression>");
    r = term(b, l + 1);
    r = r && expression_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ((PLUS | MINUS) term)*
  private static boolean expression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!expression_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "expression_1", c)) break;
    }
    return true;
  }

  // (PLUS | MINUS) term
  private static boolean expression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression_1_0_0(b, l + 1);
    r = r && term(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // PLUS | MINUS
  private static boolean expression_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, PLUS);
    if (!r) r = consumeToken(b, MINUS);
    return r;
  }

  /* ********************************************************** */
  // INTEGER_LITERAL | FLOAT_LITERAL | STRING_LITERAL | ID | LPAREN expression RPAREN
  //          | SUM LPAREN ID IN expression RPAREN expression
  //          | FORALL LPAREN ID IN expression RPAREN expression
  public static boolean factor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "factor")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FACTOR, "<factor>");
    r = consumeToken(b, INTEGER_LITERAL);
    if (!r) r = consumeToken(b, FLOAT_LITERAL);
    if (!r) r = consumeToken(b, STRING_LITERAL);
    if (!r) r = consumeToken(b, ID);
    if (!r) r = factor_4(b, l + 1);
    if (!r) r = factor_5(b, l + 1);
    if (!r) r = factor_6(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // LPAREN expression RPAREN
  private static boolean factor_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "factor_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // SUM LPAREN ID IN expression RPAREN expression
  private static boolean factor_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "factor_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, SUM, LPAREN, ID, IN);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // FORALL LPAREN ID IN expression RPAREN expression
  private static boolean factor_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "factor_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, FORALL, LPAREN, ID, IN);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // INCLUDE STRING_LITERAL SEMICOLON
  public static boolean includeDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "includeDeclaration")) return false;
    if (!nextTokenIs(b, INCLUDE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, INCLUDE, STRING_LITERAL, SEMICOLON);
    exit_section_(b, m, INCLUDE_DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // declaration | SEMICOLON
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    r = declaration(b, l + 1);
    if (!r) r = consumeToken(b, SEMICOLON);
    return r;
  }

  /* ********************************************************** */
  // (MINIMIZE | MAXIMIZE) expression SEMICOLON
  public static boolean objectiveDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "objectiveDeclaration")) return false;
    if (!nextTokenIs(b, "<objective declaration>", MAXIMIZE, MINIMIZE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OBJECTIVE_DECLARATION, "<objective declaration>");
    r = objectiveDeclaration_0(b, l + 1);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // MINIMIZE | MAXIMIZE
  private static boolean objectiveDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "objectiveDeclaration_0")) return false;
    boolean r;
    r = consumeToken(b, MINIMIZE);
    if (!r) r = consumeToken(b, MAXIMIZE);
    return r;
  }

  /* ********************************************************** */
  // item_*
  static boolean oplFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "oplFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "oplFile", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // expression DOTDOT expression
  public static boolean rangeExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rangeExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, RANGE_EXPRESSION, "<range expression>");
    r = expression(b, l + 1);
    r = r && consumeToken(b, DOTDOT);
    r = r && expression(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // factor ((STAR | SLASH) factor)*
  public static boolean term(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "term")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TERM, "<term>");
    r = factor(b, l + 1);
    r = r && term_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ((STAR | SLASH) factor)*
  private static boolean term_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "term_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!term_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "term_1", c)) break;
    }
    return true;
  }

  // (STAR | SLASH) factor
  private static boolean term_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "term_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = term_1_0_0(b, l + 1);
    r = r && factor(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // STAR | SLASH
  private static boolean term_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "term_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, STAR);
    if (!r) r = consumeToken(b, SLASH);
    return r;
  }

  /* ********************************************************** */
  // TUPLE ID LBRACE tupleField* RBRACE SEMICOLON
  public static boolean tupleDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tupleDeclaration")) return false;
    if (!nextTokenIs(b, TUPLE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, TUPLE, ID, LBRACE);
    r = r && tupleDeclaration_3(b, l + 1);
    r = r && consumeTokens(b, 0, RBRACE, SEMICOLON);
    exit_section_(b, m, TUPLE_DECLARATION, r);
    return r;
  }

  // tupleField*
  private static boolean tupleDeclaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tupleDeclaration_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!tupleField(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "tupleDeclaration_3", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // (INT | FLOAT | STRING | ID) ID SEMICOLON
  public static boolean tupleField(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tupleField")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TUPLE_FIELD, "<tuple field>");
    r = tupleField_0(b, l + 1);
    r = r && consumeTokens(b, 0, ID, SEMICOLON);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // INT | FLOAT | STRING | ID
  private static boolean tupleField_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tupleField_0")) return false;
    boolean r;
    r = consumeToken(b, INT);
    if (!r) r = consumeToken(b, FLOAT);
    if (!r) r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, ID);
    return r;
  }

  /* ********************************************************** */
  // (INT | FLOAT | BOOLEAN | STRING | RANGE) ID (EQ expression)? SEMICOLON
  public static boolean varDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "varDeclaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VAR_DECLARATION, "<var declaration>");
    r = varDeclaration_0(b, l + 1);
    r = r && consumeToken(b, ID);
    r = r && varDeclaration_2(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // INT | FLOAT | BOOLEAN | STRING | RANGE
  private static boolean varDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "varDeclaration_0")) return false;
    boolean r;
    r = consumeToken(b, INT);
    if (!r) r = consumeToken(b, FLOAT);
    if (!r) r = consumeToken(b, BOOLEAN);
    if (!r) r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, RANGE);
    return r;
  }

  // (EQ expression)?
  private static boolean varDeclaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "varDeclaration_2")) return false;
    varDeclaration_2_0(b, l + 1);
    return true;
  }

  // EQ expression
  private static boolean varDeclaration_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "varDeclaration_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EQ);
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

}
