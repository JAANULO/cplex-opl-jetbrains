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
            .around(com.github.cplexopl.psi.OplTypes.LE).spaceIf(true)
            .around(com.github.cplexopl.psi.OplTypes.GE).spaceIf(true)
            // Spacja po przecinku
            .after(com.github.cplexopl.psi.OplTypes.COMMA).spaceIf(true)
            // Nowa linia przed klamrami sekcji
            .before(com.github.cplexopl.psi.OplTypes.LBRACE).none()
    }
}