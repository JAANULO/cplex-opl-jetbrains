package com.github.cplexopl

import com.intellij.lang.Language

// Language = singleton representing a programming language in IntelliJ Platform
// Singleton = one global instance (design pattern)
object OplLanguage : Language("OPL") {
    private fun readResolve(): Any = OplLanguage
}

object OplDatLanguage : Language("OPLDat") {
    private fun readResolve(): Any = OplDatLanguage
}
