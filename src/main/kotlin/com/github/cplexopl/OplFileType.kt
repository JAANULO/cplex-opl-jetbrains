package com.github.cplexopl

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon
import com.intellij.icons.AllIcons

// FileType = klasa rejestrująca rozszerzenie pliku w IDE
// LanguageFileType łączy typ pliku z konkretnym językiem (dla podświetlania składni)
object OplFileType : LanguageFileType(OplLanguage) {
    override fun getName(): String = "OPL Model File"
    override fun getDescription(): String = "IBM ILOG CPLEX OPL model file"
    override fun getDefaultExtension(): String = "mod"
    // Używamy wbudowanej ikony IDE - możesz później podmienić na własną
    override fun getIcon(): Icon = OplIcons.FILE
}

object OplDatFileType : LanguageFileType(OplDatLanguage) {
    override fun getName(): String = "OPL Data File"
    override fun getDescription(): String = "IBM ILOG CPLEX OPL data file"
    override fun getDefaultExtension(): String = "dat"
    override fun getIcon(): Icon = OplIcons.FILE
}
