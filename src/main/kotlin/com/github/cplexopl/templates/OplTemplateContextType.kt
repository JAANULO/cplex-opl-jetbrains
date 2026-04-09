package com.github.cplexopl.templates

import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.psi.PsiFile

// Nowy, jednoparametrowy konstruktor (zgodny z najnowszym API JetBrains)
class OplTemplateContextType : TemplateContextType("OPL") {
    @Suppress("OVERRIDE_DEPRECATION")
    override fun isInContext(file: PsiFile, offset: Int): Boolean {
        // Szablony zadziałają tylko w plikach modelu i danych OPL
        return file.name.endsWith(".mod") || file.name.endsWith(".dat")
    }
}