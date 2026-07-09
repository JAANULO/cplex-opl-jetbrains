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

  public ASTNode parse(IElementType root_, PsiBuilder builder_) {
    parseLight(root_, builder_);
    return builder_.getTreeBuilt();
  }

  public void parseLight(IElementType root_, PsiBuilder builder_) {
    boolean result_;
    builder_ = adapt_builder_(root_, builder_, this, null);
    Marker marker_ = enter_section_(builder_, 0, _COLLAPSE_, null);
    result_ = parse_root_(root_, builder_);
    exit_section_(builder_, 0, marker_, root_, result_, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType root_, PsiBuilder builder_) {
    return parse_root_(root_, builder_, 0);
  }

  static boolean parse_root_(IElementType root_, PsiBuilder builder_, int level_) {
    return oplFile(builder_, level_ + 1);
  }

  /* ********************************************************** */
  // term ((PLUS | MINUS) term)*
  static boolean additiveExpression(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "additiveExpression")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = term(builder_, level_ + 1);
    result_ = result_ && additiveExpression_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ((PLUS | MINUS) term)*
  private static boolean additiveExpression_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "additiveExpression_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!additiveExpression_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "additiveExpression_1", pos_)) break;
    }
    return true;
  }

  // (PLUS | MINUS) term
  private static boolean additiveExpression_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "additiveExpression_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = additiveExpression_1_0_0(builder_, level_ + 1);
    result_ = result_ && term(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // PLUS | MINUS
  private static boolean additiveExpression_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "additiveExpression_1_0_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, PLUS);
    if (!result_) result_ = consumeToken(builder_, MINUS);
    return result_;
  }

  /* ********************************************************** */
  // expression ((LE | LT | GT | GE | EQ | EQEQ | NEQ) expression)?
  static boolean constraintExpression(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "constraintExpression")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = expression(builder_, level_ + 1);
    result_ = result_ && constraintExpression_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ((LE | LT | GT | GE | EQ | EQEQ | NEQ) expression)?
  private static boolean constraintExpression_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "constraintExpression_1")) return false;
    constraintExpression_1_0(builder_, level_ + 1);
    return true;
  }

  // (LE | LT | GT | GE | EQ | EQEQ | NEQ) expression
  private static boolean constraintExpression_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "constraintExpression_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = constraintExpression_1_0_0(builder_, level_ + 1);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // LE | LT | GT | GE | EQ | EQEQ | NEQ
  private static boolean constraintExpression_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "constraintExpression_1_0_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, LE);
    if (!result_) result_ = consumeToken(builder_, LT);
    if (!result_) result_ = consumeToken(builder_, GT);
    if (!result_) result_ = consumeToken(builder_, GE);
    if (!result_) result_ = consumeToken(builder_, EQ);
    if (!result_) result_ = consumeToken(builder_, EQEQ);
    if (!result_) result_ = consumeToken(builder_, NEQ);
    return result_;
  }

  /* ********************************************************** */
  // label? constraintExpression SEMICOLON
  //                  | FORALL LPAREN oplIterator (COMMA oplIterator)* RPAREN LBRACE constraintItem* RBRACE
  //                  | FORALL LPAREN oplIterator (COMMA oplIterator)* RPAREN constraintItem
  public static boolean constraintItem(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "constraintItem")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, CONSTRAINT_ITEM, "<constraint item>");
    result_ = constraintItem_0(builder_, level_ + 1);
    if (!result_) result_ = constraintItem_1(builder_, level_ + 1);
    if (!result_) result_ = constraintItem_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // label? constraintExpression SEMICOLON
  private static boolean constraintItem_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "constraintItem_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = constraintItem_0_0(builder_, level_ + 1);
    result_ = result_ && constraintExpression(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, SEMICOLON);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // label?
  private static boolean constraintItem_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "constraintItem_0_0")) return false;
    label(builder_, level_ + 1);
    return true;
  }

  // FORALL LPAREN oplIterator (COMMA oplIterator)* RPAREN LBRACE constraintItem* RBRACE
  private static boolean constraintItem_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "constraintItem_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, FORALL, LPAREN);
    result_ = result_ && oplIterator(builder_, level_ + 1);
    result_ = result_ && constraintItem_1_3(builder_, level_ + 1);
    result_ = result_ && consumeTokens(builder_, 0, RPAREN, LBRACE);
    result_ = result_ && constraintItem_1_6(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RBRACE);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (COMMA oplIterator)*
  private static boolean constraintItem_1_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "constraintItem_1_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!constraintItem_1_3_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "constraintItem_1_3", pos_)) break;
    }
    return true;
  }

  // COMMA oplIterator
  private static boolean constraintItem_1_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "constraintItem_1_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && oplIterator(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // constraintItem*
  private static boolean constraintItem_1_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "constraintItem_1_6")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!constraintItem(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "constraintItem_1_6", pos_)) break;
    }
    return true;
  }

  // FORALL LPAREN oplIterator (COMMA oplIterator)* RPAREN constraintItem
  private static boolean constraintItem_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "constraintItem_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, FORALL, LPAREN);
    result_ = result_ && oplIterator(builder_, level_ + 1);
    result_ = result_ && constraintItem_2_3(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    result_ = result_ && constraintItem(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (COMMA oplIterator)*
  private static boolean constraintItem_2_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "constraintItem_2_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!constraintItem_2_3_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "constraintItem_2_3", pos_)) break;
    }
    return true;
  }

  // COMMA oplIterator
  private static boolean constraintItem_2_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "constraintItem_2_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && oplIterator(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // SUBJECT TO LBRACE constraintItem* RBRACE
  public static boolean constraintSection(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "constraintSection")) return false;
    if (!nextTokenIs(builder_, SUBJECT)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, CONSTRAINT_SECTION, null);
    result_ = consumeTokens(builder_, 1, SUBJECT, TO, LBRACE);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, constraintSection_3(builder_, level_ + 1));
    result_ = pinned_ && consumeToken(builder_, RBRACE) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // constraintItem*
  private static boolean constraintSection_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "constraintSection_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!constraintItem(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "constraintSection_3", pos_)) break;
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
  //               | usingDeclaration
  public static boolean declaration(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "declaration")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DECLARATION, "<declaration>");
    result_ = varDeclaration(builder_, level_ + 1);
    if (!result_) result_ = dvarDeclaration(builder_, level_ + 1);
    if (!result_) result_ = objectiveDeclaration(builder_, level_ + 1);
    if (!result_) result_ = constraintSection(builder_, level_ + 1);
    if (!result_) result_ = executeBlock(builder_, level_ + 1);
    if (!result_) result_ = tupleDeclaration(builder_, level_ + 1);
    if (!result_) result_ = includeDeclaration(builder_, level_ + 1);
    if (!result_) result_ = usingDeclaration(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // DVAR (INT | FLOAT | BOOLEAN | INTERVAL | SEQUENCE) ID (LBRACKET expression RBRACKET)* (IN rangeExpression)? SEMICOLON
  public static boolean dvarDeclaration(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dvarDeclaration")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DVAR_DECLARATION, "<dvar declaration>");
    result_ = consumeToken(builder_, DVAR);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, dvarDeclaration_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, consumeToken(builder_, ID)) && result_;
    result_ = pinned_ && report_error_(builder_, dvarDeclaration_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, dvarDeclaration_4(builder_, level_ + 1)) && result_;
    result_ = pinned_ && consumeToken(builder_, SEMICOLON) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, OplParser::statement_recover);
    return result_ || pinned_;
  }

  // INT | FLOAT | BOOLEAN | INTERVAL | SEQUENCE
  private static boolean dvarDeclaration_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dvarDeclaration_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, INT);
    if (!result_) result_ = consumeToken(builder_, FLOAT);
    if (!result_) result_ = consumeToken(builder_, BOOLEAN);
    if (!result_) result_ = consumeToken(builder_, INTERVAL);
    if (!result_) result_ = consumeToken(builder_, SEQUENCE);
    return result_;
  }

  // (LBRACKET expression RBRACKET)*
  private static boolean dvarDeclaration_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dvarDeclaration_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!dvarDeclaration_3_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "dvarDeclaration_3", pos_)) break;
    }
    return true;
  }

  // LBRACKET expression RBRACKET
  private static boolean dvarDeclaration_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dvarDeclaration_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LBRACKET);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RBRACKET);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (IN rangeExpression)?
  private static boolean dvarDeclaration_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dvarDeclaration_4")) return false;
    dvarDeclaration_4_0(builder_, level_ + 1);
    return true;
  }

  // IN rangeExpression
  private static boolean dvarDeclaration_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "dvarDeclaration_4_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, IN);
    result_ = result_ && rangeExpression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // EXECUTE ID? executeBody
  public static boolean executeBlock(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "executeBlock")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, EXECUTE_BLOCK, "<execute block>");
    result_ = consumeToken(builder_, EXECUTE);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, executeBlock_1(builder_, level_ + 1));
    result_ = pinned_ && executeBody(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, OplParser::statement_recover);
    return result_ || pinned_;
  }

  // ID?
  private static boolean executeBlock_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "executeBlock_1")) return false;
    consumeToken(builder_, ID);
    return true;
  }

  /* ********************************************************** */
  // LBRACE executeItem* RBRACE
  public static boolean executeBody(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "executeBody")) return false;
    if (!nextTokenIs(builder_, LBRACE)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LBRACE);
    result_ = result_ && executeBody_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RBRACE);
    exit_section_(builder_, marker_, EXECUTE_BODY, result_);
    return result_;
  }

  // executeItem*
  private static boolean executeBody_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "executeBody_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!executeItem(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "executeBody_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // executeBody | executeToken
  static boolean executeItem(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "executeItem")) return false;
    boolean result_;
    result_ = executeBody(builder_, level_ + 1);
    if (!result_) result_ = executeToken(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // ID | INTEGER_LITERAL | FLOAT_LITERAL | STRING_LITERAL | LPAREN | RPAREN | LBRACKET | RBRACKET | PLUS | MINUS | STAR | SLASH | EQ | EQEQ | NEQ | LT | LE | GT | GE | SEMICOLON | COMMA | DOT | DOTDOT | ELLIPSIS | COLON | INT | FLOAT | BOOLEAN | STRING | RANGE | DVAR | DEXPR | TUPLE | MINIMIZE | MAXIMIZE | SUBJECT_TO | INCLUDE | EXECUTE | FORALL | EXISTS | IN | TO | IF | ELSE | THEN | USING | CP | INTERVAL | SEQUENCE | WITH | ASSERT | SUM | ALL | PULSE | STEP | ALLDIFFERENT | PACK | ANDAND | OROR | NOT | MOD
  static boolean executeToken(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "executeToken")) return false;
    boolean result_;
    result_ = consumeToken(builder_, ID);
    if (!result_) result_ = consumeToken(builder_, INTEGER_LITERAL);
    if (!result_) result_ = consumeToken(builder_, FLOAT_LITERAL);
    if (!result_) result_ = consumeToken(builder_, STRING_LITERAL);
    if (!result_) result_ = consumeToken(builder_, LPAREN);
    if (!result_) result_ = consumeToken(builder_, RPAREN);
    if (!result_) result_ = consumeToken(builder_, LBRACKET);
    if (!result_) result_ = consumeToken(builder_, RBRACKET);
    if (!result_) result_ = consumeToken(builder_, PLUS);
    if (!result_) result_ = consumeToken(builder_, MINUS);
    if (!result_) result_ = consumeToken(builder_, STAR);
    if (!result_) result_ = consumeToken(builder_, SLASH);
    if (!result_) result_ = consumeToken(builder_, EQ);
    if (!result_) result_ = consumeToken(builder_, EQEQ);
    if (!result_) result_ = consumeToken(builder_, NEQ);
    if (!result_) result_ = consumeToken(builder_, LT);
    if (!result_) result_ = consumeToken(builder_, LE);
    if (!result_) result_ = consumeToken(builder_, GT);
    if (!result_) result_ = consumeToken(builder_, GE);
    if (!result_) result_ = consumeToken(builder_, SEMICOLON);
    if (!result_) result_ = consumeToken(builder_, COMMA);
    if (!result_) result_ = consumeToken(builder_, DOT);
    if (!result_) result_ = consumeToken(builder_, DOTDOT);
    if (!result_) result_ = consumeToken(builder_, ELLIPSIS);
    if (!result_) result_ = consumeToken(builder_, COLON);
    if (!result_) result_ = consumeToken(builder_, INT);
    if (!result_) result_ = consumeToken(builder_, FLOAT);
    if (!result_) result_ = consumeToken(builder_, BOOLEAN);
    if (!result_) result_ = consumeToken(builder_, STRING);
    if (!result_) result_ = consumeToken(builder_, RANGE);
    if (!result_) result_ = consumeToken(builder_, DVAR);
    if (!result_) result_ = consumeToken(builder_, DEXPR);
    if (!result_) result_ = consumeToken(builder_, TUPLE);
    if (!result_) result_ = consumeToken(builder_, MINIMIZE);
    if (!result_) result_ = consumeToken(builder_, MAXIMIZE);
    if (!result_) result_ = consumeToken(builder_, SUBJECT_TO);
    if (!result_) result_ = consumeToken(builder_, INCLUDE);
    if (!result_) result_ = consumeToken(builder_, EXECUTE);
    if (!result_) result_ = consumeToken(builder_, FORALL);
    if (!result_) result_ = consumeToken(builder_, EXISTS);
    if (!result_) result_ = consumeToken(builder_, IN);
    if (!result_) result_ = consumeToken(builder_, TO);
    if (!result_) result_ = consumeToken(builder_, IF);
    if (!result_) result_ = consumeToken(builder_, ELSE);
    if (!result_) result_ = consumeToken(builder_, THEN);
    if (!result_) result_ = consumeToken(builder_, USING);
    if (!result_) result_ = consumeToken(builder_, CP);
    if (!result_) result_ = consumeToken(builder_, INTERVAL);
    if (!result_) result_ = consumeToken(builder_, SEQUENCE);
    if (!result_) result_ = consumeToken(builder_, WITH);
    if (!result_) result_ = consumeToken(builder_, ASSERT);
    if (!result_) result_ = consumeToken(builder_, SUM);
    if (!result_) result_ = consumeToken(builder_, ALL);
    if (!result_) result_ = consumeToken(builder_, PULSE);
    if (!result_) result_ = consumeToken(builder_, STEP);
    if (!result_) result_ = consumeToken(builder_, ALLDIFFERENT);
    if (!result_) result_ = consumeToken(builder_, PACK);
    if (!result_) result_ = consumeToken(builder_, ANDAND);
    if (!result_) result_ = consumeToken(builder_, OROR);
    if (!result_) result_ = consumeToken(builder_, NOT);
    if (!result_) result_ = consumeToken(builder_, MOD);
    return result_;
  }

  /* ********************************************************** */
  // rangeExpression | additiveExpression
  public static boolean expression(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expression")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, EXPRESSION, "<expression>");
    result_ = rangeExpression(builder_, level_ + 1);
    if (!result_) result_ = additiveExpression(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // (PLUS | MINUS) factor
  //          | (SUM | ALL) LPAREN oplIterator (COMMA oplIterator)* RPAREN expression
  //          | (ID | ALLDIFFERENT | PACK | PULSE | STEP) ( LPAREN (expression (COMMA expression)*)? RPAREN )? (LBRACKET expression RBRACKET)*
  //          | INTEGER_LITERAL
  //          | FLOAT_LITERAL
  //          | STRING_LITERAL
  //          | LBRACKET (expression (COMMA expression)*)? RBRACKET
  //          | LPAREN expression RPAREN
  public static boolean factor(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _COLLAPSE_, FACTOR, "<factor>");
    result_ = factor_0(builder_, level_ + 1);
    if (!result_) result_ = factor_1(builder_, level_ + 1);
    if (!result_) result_ = factor_2(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, INTEGER_LITERAL);
    if (!result_) result_ = consumeToken(builder_, FLOAT_LITERAL);
    if (!result_) result_ = consumeToken(builder_, STRING_LITERAL);
    if (!result_) result_ = factor_6(builder_, level_ + 1);
    if (!result_) result_ = factor_7(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (PLUS | MINUS) factor
  private static boolean factor_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = factor_0_0(builder_, level_ + 1);
    result_ = result_ && factor(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // PLUS | MINUS
  private static boolean factor_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_0_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, PLUS);
    if (!result_) result_ = consumeToken(builder_, MINUS);
    return result_;
  }

  // (SUM | ALL) LPAREN oplIterator (COMMA oplIterator)* RPAREN expression
  private static boolean factor_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = factor_1_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, LPAREN);
    result_ = result_ && oplIterator(builder_, level_ + 1);
    result_ = result_ && factor_1_3(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // SUM | ALL
  private static boolean factor_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_1_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, SUM);
    if (!result_) result_ = consumeToken(builder_, ALL);
    return result_;
  }

  // (COMMA oplIterator)*
  private static boolean factor_1_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_1_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!factor_1_3_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "factor_1_3", pos_)) break;
    }
    return true;
  }

  // COMMA oplIterator
  private static boolean factor_1_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_1_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && oplIterator(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (ID | ALLDIFFERENT | PACK | PULSE | STEP) ( LPAREN (expression (COMMA expression)*)? RPAREN )? (LBRACKET expression RBRACKET)*
  private static boolean factor_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = factor_2_0(builder_, level_ + 1);
    result_ = result_ && factor_2_1(builder_, level_ + 1);
    result_ = result_ && factor_2_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ID | ALLDIFFERENT | PACK | PULSE | STEP
  private static boolean factor_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_2_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, ID);
    if (!result_) result_ = consumeToken(builder_, ALLDIFFERENT);
    if (!result_) result_ = consumeToken(builder_, PACK);
    if (!result_) result_ = consumeToken(builder_, PULSE);
    if (!result_) result_ = consumeToken(builder_, STEP);
    return result_;
  }

  // ( LPAREN (expression (COMMA expression)*)? RPAREN )?
  private static boolean factor_2_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_2_1")) return false;
    factor_2_1_0(builder_, level_ + 1);
    return true;
  }

  // LPAREN (expression (COMMA expression)*)? RPAREN
  private static boolean factor_2_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_2_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && factor_2_1_0_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (expression (COMMA expression)*)?
  private static boolean factor_2_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_2_1_0_1")) return false;
    factor_2_1_0_1_0(builder_, level_ + 1);
    return true;
  }

  // expression (COMMA expression)*
  private static boolean factor_2_1_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_2_1_0_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = expression(builder_, level_ + 1);
    result_ = result_ && factor_2_1_0_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (COMMA expression)*
  private static boolean factor_2_1_0_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_2_1_0_1_0_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!factor_2_1_0_1_0_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "factor_2_1_0_1_0_1", pos_)) break;
    }
    return true;
  }

  // COMMA expression
  private static boolean factor_2_1_0_1_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_2_1_0_1_0_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (LBRACKET expression RBRACKET)*
  private static boolean factor_2_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_2_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!factor_2_2_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "factor_2_2", pos_)) break;
    }
    return true;
  }

  // LBRACKET expression RBRACKET
  private static boolean factor_2_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_2_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LBRACKET);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RBRACKET);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // LBRACKET (expression (COMMA expression)*)? RBRACKET
  private static boolean factor_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_6")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LBRACKET);
    result_ = result_ && factor_6_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RBRACKET);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (expression (COMMA expression)*)?
  private static boolean factor_6_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_6_1")) return false;
    factor_6_1_0(builder_, level_ + 1);
    return true;
  }

  // expression (COMMA expression)*
  private static boolean factor_6_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_6_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = expression(builder_, level_ + 1);
    result_ = result_ && factor_6_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (COMMA expression)*
  private static boolean factor_6_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_6_1_0_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!factor_6_1_0_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "factor_6_1_0_1", pos_)) break;
    }
    return true;
  }

  // COMMA expression
  private static boolean factor_6_1_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_6_1_0_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // LPAREN expression RPAREN
  private static boolean factor_7(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "factor_7")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // INCLUDE STRING_LITERAL SEMICOLON
  public static boolean includeDeclaration(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "includeDeclaration")) return false;
    if (!nextTokenIs(builder_, INCLUDE)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, INCLUDE_DECLARATION, null);
    result_ = consumeTokens(builder_, 1, INCLUDE, STRING_LITERAL, SEMICOLON);
    pinned_ = result_; // pin = 1
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // declaration | SEMICOLON
  static boolean item_(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "item_")) return false;
    boolean result_;
    result_ = declaration(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, SEMICOLON);
    return result_;
  }

  /* ********************************************************** */
  // ID COLON
  static boolean label(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "label")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, ID, COLON);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // (MINIMIZE | MAXIMIZE) expression SEMICOLON
  public static boolean objectiveDeclaration(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "objectiveDeclaration")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, OBJECTIVE_DECLARATION, "<objective declaration>");
    result_ = objectiveDeclaration_0(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, expression(builder_, level_ + 1));
    result_ = pinned_ && consumeToken(builder_, SEMICOLON) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, OplParser::statement_recover);
    return result_ || pinned_;
  }

  // MINIMIZE | MAXIMIZE
  private static boolean objectiveDeclaration_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "objectiveDeclaration_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, MINIMIZE);
    if (!result_) result_ = consumeToken(builder_, MAXIMIZE);
    return result_;
  }

  /* ********************************************************** */
  // item_*
  static boolean oplFile(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "oplFile")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!item_(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "oplFile", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // ID IN expression (COLON constraintExpression)?
  public static boolean oplIterator(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "oplIterator")) return false;
    if (!nextTokenIs(builder_, ID)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, ID, IN);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && oplIterator_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, OPL_ITERATOR, result_);
    return result_;
  }

  // (COLON constraintExpression)?
  private static boolean oplIterator_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "oplIterator_3")) return false;
    oplIterator_3_0(builder_, level_ + 1);
    return true;
  }

  // COLON constraintExpression
  private static boolean oplIterator_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "oplIterator_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COLON);
    result_ = result_ && constraintExpression(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // additiveExpression DOTDOT additiveExpression
  public static boolean rangeExpression(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "rangeExpression")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, RANGE_EXPRESSION, "<range expression>");
    result_ = additiveExpression(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, DOTDOT);
    result_ = result_ && additiveExpression(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // !(INT | FLOAT | BOOLEAN | INTERVAL | SEQUENCE | STRING | RANGE | DVAR | TUPLE | MINIMIZE | MAXIMIZE | SUBJECT | INCLUDE | EXECUTE | USING | SEMICOLON | RBRACE)
  static boolean statement_recover(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "statement_recover")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !statement_recover_0(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // INT | FLOAT | BOOLEAN | INTERVAL | SEQUENCE | STRING | RANGE | DVAR | TUPLE | MINIMIZE | MAXIMIZE | SUBJECT | INCLUDE | EXECUTE | USING | SEMICOLON | RBRACE
  private static boolean statement_recover_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "statement_recover_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, INT);
    if (!result_) result_ = consumeToken(builder_, FLOAT);
    if (!result_) result_ = consumeToken(builder_, BOOLEAN);
    if (!result_) result_ = consumeToken(builder_, INTERVAL);
    if (!result_) result_ = consumeToken(builder_, SEQUENCE);
    if (!result_) result_ = consumeToken(builder_, STRING);
    if (!result_) result_ = consumeToken(builder_, RANGE);
    if (!result_) result_ = consumeToken(builder_, DVAR);
    if (!result_) result_ = consumeToken(builder_, TUPLE);
    if (!result_) result_ = consumeToken(builder_, MINIMIZE);
    if (!result_) result_ = consumeToken(builder_, MAXIMIZE);
    if (!result_) result_ = consumeToken(builder_, SUBJECT);
    if (!result_) result_ = consumeToken(builder_, INCLUDE);
    if (!result_) result_ = consumeToken(builder_, EXECUTE);
    if (!result_) result_ = consumeToken(builder_, USING);
    if (!result_) result_ = consumeToken(builder_, SEMICOLON);
    if (!result_) result_ = consumeToken(builder_, RBRACE);
    return result_;
  }

  /* ********************************************************** */
  // factor ((STAR | SLASH) factor)*
  public static boolean term(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "term")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TERM, "<term>");
    result_ = factor(builder_, level_ + 1);
    result_ = result_ && term_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // ((STAR | SLASH) factor)*
  private static boolean term_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "term_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!term_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "term_1", pos_)) break;
    }
    return true;
  }

  // (STAR | SLASH) factor
  private static boolean term_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "term_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = term_1_0_0(builder_, level_ + 1);
    result_ = result_ && factor(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // STAR | SLASH
  private static boolean term_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "term_1_0_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, STAR);
    if (!result_) result_ = consumeToken(builder_, SLASH);
    return result_;
  }

  /* ********************************************************** */
  // TUPLE ID LBRACE tupleField* RBRACE SEMICOLON
  public static boolean tupleDeclaration(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "tupleDeclaration")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TUPLE_DECLARATION, "<tuple declaration>");
    result_ = consumeTokens(builder_, 1, TUPLE, ID, LBRACE);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, tupleDeclaration_3(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, consumeTokens(builder_, -1, RBRACE, SEMICOLON)) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, OplParser::statement_recover);
    return result_ || pinned_;
  }

  // tupleField*
  private static boolean tupleDeclaration_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "tupleDeclaration_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!tupleField(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "tupleDeclaration_3", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // (INT | FLOAT | STRING | ID) ID SEMICOLON
  public static boolean tupleField(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "tupleField")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TUPLE_FIELD, "<tuple field>");
    result_ = tupleField_0(builder_, level_ + 1);
    result_ = result_ && consumeTokens(builder_, 1, ID, SEMICOLON);
    pinned_ = result_; // pin = 2
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // INT | FLOAT | STRING | ID
  private static boolean tupleField_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "tupleField_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, INT);
    if (!result_) result_ = consumeToken(builder_, FLOAT);
    if (!result_) result_ = consumeToken(builder_, STRING);
    if (!result_) result_ = consumeToken(builder_, ID);
    return result_;
  }

  /* ********************************************************** */
  // USING CP SEMICOLON
  public static boolean usingDeclaration(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "usingDeclaration")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, USING_DECLARATION, "<using declaration>");
    result_ = consumeTokens(builder_, 1, USING, CP, SEMICOLON);
    pinned_ = result_; // pin = 1
    exit_section_(builder_, level_, marker_, result_, pinned_, OplParser::statement_recover);
    return result_ || pinned_;
  }

  /* ********************************************************** */
  // (INT | FLOAT | BOOLEAN | STRING | RANGE) ID (LBRACKET expression RBRACKET)* (EQ (ELLIPSIS | expression))? SEMICOLON
  public static boolean varDeclaration(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "varDeclaration")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, VAR_DECLARATION, "<var declaration>");
    result_ = varDeclaration_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, ID);
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, varDeclaration_2(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, varDeclaration_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && consumeToken(builder_, SEMICOLON) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, OplParser::statement_recover);
    return result_ || pinned_;
  }

  // INT | FLOAT | BOOLEAN | STRING | RANGE
  private static boolean varDeclaration_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "varDeclaration_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, INT);
    if (!result_) result_ = consumeToken(builder_, FLOAT);
    if (!result_) result_ = consumeToken(builder_, BOOLEAN);
    if (!result_) result_ = consumeToken(builder_, STRING);
    if (!result_) result_ = consumeToken(builder_, RANGE);
    return result_;
  }

  // (LBRACKET expression RBRACKET)*
  private static boolean varDeclaration_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "varDeclaration_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!varDeclaration_2_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "varDeclaration_2", pos_)) break;
    }
    return true;
  }

  // LBRACKET expression RBRACKET
  private static boolean varDeclaration_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "varDeclaration_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LBRACKET);
    result_ = result_ && expression(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RBRACKET);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (EQ (ELLIPSIS | expression))?
  private static boolean varDeclaration_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "varDeclaration_3")) return false;
    varDeclaration_3_0(builder_, level_ + 1);
    return true;
  }

  // EQ (ELLIPSIS | expression)
  private static boolean varDeclaration_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "varDeclaration_3_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, EQ);
    result_ = result_ && varDeclaration_3_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ELLIPSIS | expression
  private static boolean varDeclaration_3_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "varDeclaration_3_0_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, ELLIPSIS);
    if (!result_) result_ = expression(builder_, level_ + 1);
    return result_;
  }

}
