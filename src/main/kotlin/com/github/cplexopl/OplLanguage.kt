package com.github.cplexopl

import com.intellij.lang.Language

// Language = singleton reprezentujący język programowania w IntelliJ Platform
// Singleton = jedna globalna instancja (wzorzec projektowy - design pattern)
object OplLanguage : Language("OPL") {
    private fun readResolve(): Any = OplLanguage
}

object OplDatLanguage : Language("OPLDat") {
    private fun readResolve(): Any = OplDatLanguage
}
