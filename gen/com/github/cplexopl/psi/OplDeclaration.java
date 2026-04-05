// This is a generated file. Not intended for manual editing.
package com.github.cplexopl.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface OplDeclaration extends PsiElement {

  @Nullable
  OplConstraintSection getConstraintSection();

  @Nullable
  OplDvarDeclaration getDvarDeclaration();

  @Nullable
  OplExecuteBlock getExecuteBlock();

  @Nullable
  OplIncludeDeclaration getIncludeDeclaration();

  @Nullable
  OplObjectiveDeclaration getObjectiveDeclaration();

  @Nullable
  OplTupleDeclaration getTupleDeclaration();

  @Nullable
  OplVarDeclaration getVarDeclaration();

}
