package com.github.cplexopl.templates

import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.psi.PsiFile
import com.intellij.codeInsight.template.TemplateActionContext

// Nowy, jednoparametrowy konstruktor (zgodny z najnowszym API JetBrains)
class OplTemplateContextType : TemplateContextType("OPL") {
    override fun isInContext(templateActionContext: TemplateActionContext): Boolean {
        // Szablony zadziałają tylko w plikach modelu i danych OPL
        val file = templateActionContext.file
        return file.name.endsWith(".mod") || file.name.endsWith(".dat")
    }
}