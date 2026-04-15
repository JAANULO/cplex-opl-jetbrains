package com.github.cplexopl.reference

import com.github.cplexopl.psi.OplFactor
import com.github.cplexopl.psi.OplTypes
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.util.ProcessingContext

class OplReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        // Nasłuchujemy na OplFactor (czyli na użycia zmiennych w równaniach)
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(OplFactor::class.java),
            object : PsiReferenceProvider() {
                override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
                    val factor = element as OplFactor
                    val idNode = factor.node.findChildByType(OplTypes.ID)

                    if (idNode != null) {
                        val name = idNode.text
                        // Obliczamy dokładną pozycję litery wewnątrz równania
                        val startOffset = idNode.startOffset - factor.textRange.startOffset
                        val range = TextRange(startOffset, startOffset + idNode.textLength)
                        return arrayOf(OplReference(factor, range, name))
                    }
                    return PsiReference.EMPTY_ARRAY
                }
            }
        )
    }
}