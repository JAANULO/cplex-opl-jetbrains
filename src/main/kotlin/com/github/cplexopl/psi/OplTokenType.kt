package com.github.cplexopl.psi

import com.github.cplexopl.OplLanguage
import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls

class OplTokenType(debugName: @NonNls String) : IElementType(debugName, OplLanguage) {
    override fun toString(): String = "OplTokenType." + super.toString()
}