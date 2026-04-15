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

public class OplDeclarationImpl extends ASTWrapperPsiElement implements OplDeclaration {

  public OplDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull OplVisitor visitor) {
    visitor.visitDeclaration(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof OplVisitor) accept((OplVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public OplConstraintSection getConstraintSection() {
    return findChildByClass(OplConstraintSection.class);
  }

  @Override
  @Nullable
  public OplDvarDeclaration getDvarDeclaration() {
    return findChildByClass(OplDvarDeclaration.class);
  }

  @Override
  @Nullable
  public OplExecuteBlock getExecuteBlock() {
    return findChildByClass(OplExecuteBlock.class);
  }

  @Override
  @Nullable
  public OplIncludeDeclaration getIncludeDeclaration() {
    return findChildByClass(OplIncludeDeclaration.class);
  }

  @Override
  @Nullable
  public OplObjectiveDeclaration getObjectiveDeclaration() {
    return findChildByClass(OplObjectiveDeclaration.class);
  }

  @Override
  @Nullable
  public OplTupleDeclaration getTupleDeclaration() {
    return findChildByClass(OplTupleDeclaration.class);
  }

  @Override
  @Nullable
  public OplUsingDeclaration getUsingDeclaration() {
    return findChildByClass(OplUsingDeclaration.class);
  }

  @Override
  @Nullable
  public OplVarDeclaration getVarDeclaration() {
    return findChildByClass(OplVarDeclaration.class);
  }

}
