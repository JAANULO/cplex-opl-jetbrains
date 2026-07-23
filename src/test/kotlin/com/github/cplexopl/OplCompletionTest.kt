package com.github.cplexopl

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplCompletionTest : BasePlatformTestCase() {

    fun testKeywordCompletion() {
        myFixture.configureByText(OplFileType, "// Oczekujemy autouzupełniania tutaj\n<caret>")
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings
        assertNotNull("No completion results (null)", strings)
        assertTrue("Missing 'minimize' in autocomplete", strings!!.contains("minimize"))
        assertTrue("Missing 'dvar' in autocomplete", strings.contains("dvar"))
    }

    fun testContextualCompletion() {
        myFixture.configureByText(
            OplFileType,
            """
                dvar int myVar1 in 1..10;
                dvar boolean myVar2;
                
                minimize my<caret>
            """.trimIndent()
        )
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings
        assertNotNull("No completion results", strings)
        assertTrue("Missing 'myVar1' in autocomplete", strings!!.contains("myVar1"))
        assertTrue("Missing 'myVar2' in autocomplete", strings.contains("myVar2"))
    }
}
