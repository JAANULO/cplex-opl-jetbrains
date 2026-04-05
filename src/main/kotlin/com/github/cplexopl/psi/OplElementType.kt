package com.github.cplexopl.psi

import com.github.cplexopl.OplLanguage
import com.intellij.psi.tree.IElementType

/**
 * Klasa reprezentująca typy elementów strukturalnych (węzły drzewa AST) dla języka OPL.
 */
class OplElementType(debugName: String) : IElementType(debugName, OplLanguage)
