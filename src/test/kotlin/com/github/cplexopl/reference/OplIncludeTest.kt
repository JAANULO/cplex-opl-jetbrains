package com.github.cplexopl.reference

import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplIncludeTest : BasePlatformTestCase() {

    fun testIncludeResolution() {
        val modCode = """
            include "params.mod";
            dvar int x;
        """.trimIndent()
        
        val paramsCode = """
            int y = 5;
        """.trimIndent()
        
        // Create both files in the same directory
        myFixture.addFileToProject("testdir/params.mod", paramsCode)
        val file = myFixture.addFileToProject("testdir/model.mod", modCode)
        myFixture.configureFromExistingVirtualFile(file.virtualFile)
        
        val highlights = myFixture.doHighlighting()
        val errors = highlights.filter { it.severity == HighlightSeverity.ERROR }
        assertEmpty("Nie powinno być błędów rozwiązania include", errors)
    }
}
