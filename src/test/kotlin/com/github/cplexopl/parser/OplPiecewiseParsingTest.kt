package com.github.cplexopl.parser

import com.intellij.psi.PsiErrorElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplPiecewiseParsingTest : BasePlatformTestCase() {

    fun testPiecewiseFunctionParsing() {
        val code = "pwlFunction costPenalty = piecewise { 10 -> 0.2; 15 -> 0.5; 25 } (0, 0);"
        myFixture.configureByText("test.mod", code)
        val parseErrors = PsiTreeUtil.findChildrenOfType(myFixture.file, PsiErrorElement::class.java)
        assertEmpty("Nie powinno być błędów parsowania pwlFunction", parseErrors)
    }
}
