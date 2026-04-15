// This is a generated file. Not intended for manual editing.
package com.github.cplexopl.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface OplFactor extends PsiElement {

  @NotNull
  List<OplExpression> getExpressionList();

  @Nullable
  PsiElement getFloatLiteral();

  @Nullable
  PsiElement getId();

  @Nullable
  PsiElement getIntegerLiteral();

  @Nullable
  PsiElement getStringLiteral();

}
