package com.github.cplexopl.formatter

import com.intellij.formatting.*
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.github.cplexopl.OplLanguage

class OplFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        return FormattingModelProvider.createFormattingModelForPsiFile(
            formattingContext.containingFile,
            OplBlock(formattingContext.node, null, null, createSpaceBuilder(formattingContext.codeStyleSettings)),
            formattingContext.codeStyleSettings
        )
    }

    private fun createSpaceBuilder(settings: CodeStyleSettings): SpacingBuilder {
        return SpacingBuilder(settings, OplLanguage)
            // Spacje wokół operatorów przypisania i porównania
            .around(com.github.cplexopl.psi.OplTypes.EQ).spaceIf(true)
            .around(com.github.cplexopl.psi.OplTypes.EQEQ).spaceIf(true) // Dodane podwójne ==
            .around(com.github.cplexopl.psi.OplTypes.NEQ).spaceIf(true)  // Dodane nierówne !=
            .around(com.github.cplexopl.psi.OplTypes.LE).spaceIf(true)
            .around(com.github.cplexopl.psi.OplTypes.GE).spaceIf(true)
            // Spacja po przecinku
            .after(com.github.cplexopl.psi.OplTypes.COMMA).spaceIf(true)
            // Wymuszenie dokładnie jednej spacji i zakaz nowej linii przed LBRACE i EXECUTE_BODY
            .before(com.github.cplexopl.psi.OplTypes.LBRACE).spacing(1, 1, 0, false, 0)
            .before(com.github.cplexopl.psi.OplTypes.EXECUTE_BODY).spacing(1, 1, 0, false, 0)
    }
}