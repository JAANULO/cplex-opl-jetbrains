// This is a generated file. Not intended for manual editing.
package com.github.cplexopl.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.cplexopl.psi.OplTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.github.cplexopl.psi.*;

public class OplFactorImpl extends ASTWrapperPsiElement implements OplFactor {

  public OplFactorImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull OplVisitor visitor) {
    visitor.visitFactor(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof OplVisitor) accept((OplVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public OplExpression getExpression() {
    return findChildByClass(OplExpression.class);
  }

  @Override
  @Nullable
  public OplFactor getFactor() {
    return findChildByClass(OplFactor.class);
  }

  @Override
  @NotNull
  public List<OplOplIterator> getOplIteratorList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, OplOplIterator.class);
  }

  @Override
  @Nullable
  public PsiElement getFloatLiteral() {
    return findChildByType(FLOAT_LITERAL);
  }

  @Override
  @Nullable
  public PsiElement getId() {
    return findChildByType(ID);
  }

  @Override
  @Nullable
  public PsiElement getIntegerLiteral() {
    return findChildByType(INTEGER_LITERAL);
  }

  @Override
  @Nullable
  public PsiElement getStringLiteral() {
    return findChildByType(STRING_LITERAL);
  }

}
