// This is a generated file. Not intended for manual editing.
package com.github.cplexopl.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.cplexopl.psi.OplTypes.*;
import com.github.cplexopl.psi.OplNamedElementImpl;
import com.github.cplexopl.psi.*;

public class OplTupleDeclarationImpl extends OplNamedElementImpl implements OplTupleDeclaration {

  public OplTupleDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull OplVisitor visitor) {
    visitor.visitTupleDeclaration(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof OplVisitor) accept((OplVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<OplTupleField> getTupleFieldList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, OplTupleField.class);
  }

  @Override
  @Nullable
  public PsiElement getId() {
    return findChildByType(ID);
  }

}
