package com.github.cplexopl.reference

import com.github.cplexopl.psi.OplFactor
import com.github.cplexopl.psi.OplTypes
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.util.ProcessingContext

class OplReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        // We listen for OplFactor (i.e., usage of variables in equations)
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(OplFactor::class.java),
            object : PsiReferenceProvider() {
                override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
                    val factor = element as OplFactor
                    val references = mutableListOf<PsiReference>()

                    var childNode = factor.node.firstChildNode
                    while (childNode != null) {
                        if (childNode.elementType == OplTypes.ID) {
                            val name = childNode.text
                            // Calculate exact position of the letter inside the equation
                            val startOffset = childNode.startOffset - factor.textRange.startOffset
                            val range = TextRange(startOffset, startOffset + childNode.textLength)
                            references.add(OplReference(factor, range, name))
                        }
                        childNode = childNode.treeNext
                    }

                    return references.toTypedArray()
                }
            }
        )
    }
}