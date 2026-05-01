// This is a generated file. Not intended for manual editing.
package com.github.cplexopl.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface OplConstraintItem extends PsiElement {

  @NotNull
  List<OplConstraintItem> getConstraintItemList();

  @NotNull
  List<OplExpression> getExpressionList();

  @NotNull
  List<OplOplIterator> getOplIteratorList();

  @Nullable
  PsiElement getId();

}
