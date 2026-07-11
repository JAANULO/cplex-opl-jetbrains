package com.github.cplexopl.templates

import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.psi.PsiFile
import com.intellij.codeInsight.template.TemplateActionContext

// New, single-parameter constructor (compatible with latest JetBrains API)
class OplTemplateContextType : TemplateContextType("OPL") {
    override fun isInContext(templateActionContext: TemplateActionContext): Boolean {
        // Templates will work only in OPL model and data files
        val file = templateActionContext.file
        return file.name.endsWith(".mod") || file.name.endsWith(".dat")
    }
}