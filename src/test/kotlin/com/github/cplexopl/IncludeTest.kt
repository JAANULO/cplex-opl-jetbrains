package com.github.cplexopl

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class IncludeTest : BasePlatformTestCase() {
    fun testIncludeResolution() {
        val modCode = """
            include "params.mod";
            dvar int x;
        """.trimIndent()
        
        val paramsCode = """
            int y = 5;
        """.trimIndent()
        
        // create both in the same directory
        myFixture.addFileToProject("testdir/params.mod", paramsCode)
        val file = myFixture.addFileToProject("testdir/model.mod", modCode)
        myFixture.configureFromExistingVirtualFile(file.virtualFile)
        
        val highlights = myFixture.doHighlighting()
        val errors = highlights.filter { it.severity == com.intellij.lang.annotation.HighlightSeverity.ERROR }
        
        println("Errors: " + errors.map { it.description })
        assertEmpty(errors)
    }
}
