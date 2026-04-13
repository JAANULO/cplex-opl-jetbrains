package com.github.cplexopl

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

// Typ pliku dla modeli (.mod)
object OplFileType : LanguageFileType(OplLanguage) {
    override fun getName(): String = "OPL Model File"
    override fun getDescription(): String = "IBM ILOG CPLEX OPL model file"
    override fun getDefaultExtension(): String = "mod"
    override fun getIcon(): Icon = OplIcons.MOD_FILE
}

// Typ pliku dla danych (.dat)
object OplDatFileType : LanguageFileType(OplDatLanguage) {
    override fun getName(): String = "OPL Data File"
    override fun getDescription(): String = "IBM ILOG CPLEX OPL data file"
    override fun getDefaultExtension(): String = "dat"
    override fun getIcon(): Icon = OplIcons.DAT_FILE
}