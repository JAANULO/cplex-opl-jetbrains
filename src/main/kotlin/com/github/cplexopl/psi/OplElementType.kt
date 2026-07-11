package com.github.cplexopl.psi

import com.github.cplexopl.OplLanguage
import com.intellij.psi.tree.IElementType

/**
 * Class representing structural element types (AST nodes) for the OPL language.
 */
class OplElementType(debugName: String) : IElementType(debugName, OplLanguage)
