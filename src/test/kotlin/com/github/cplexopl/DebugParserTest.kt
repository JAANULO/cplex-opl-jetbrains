package com.github.cplexopl

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.psi.util.PsiTreeUtil
import java.io.File

class DebugParserTest : BasePlatformTestCase() {
    fun testParse20() {
        val code = "pwlFunction costPenalty = piecewise { 10 -> 0.2; 15 -> 0.5; 25 } (0, 0);"
        myFixture.configureByText("test.mod", code)
        val parseErrors = com.intellij.psi.util.PsiTreeUtil.findChildrenOfType(myFixture.file, com.intellij.psi.PsiErrorElement::class.java)
        
        for (pe in parseErrors) {
            println("PARSE ERROR at " + pe.textRange + ": " + pe.errorDescription + " (parent: " + pe.parent.text + ")")
        }
        println(com.intellij.psi.impl.DebugUtil.psiToString(myFixture.file, false))
        
        assertEmpty(parseErrors)
    }
}
