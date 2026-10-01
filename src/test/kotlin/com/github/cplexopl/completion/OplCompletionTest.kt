package com.github.cplexopl.completion

import com.github.cplexopl.OplFileType
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplCompletionTest : BasePlatformTestCase() {

    fun testKeywordCompletion() {
        myFixture.configureByText(OplFileType, "// Expecting autocompletion here\n<caret>")
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings
        assertNotNull("No completion results (null)", strings)
        assertTrue("Missing 'minimize' in autocomplete", strings!!.contains("minimize"))
        assertTrue("Missing 'dvar' in autocomplete", strings.contains("dvar"))
        assertTrue("Missing 'CPLEX' in autocomplete", strings.contains("CPLEX"))
        assertTrue("Missing 'constraints' in autocomplete", strings.contains("constraints"))
        assertTrue("Missing 'constraint' in autocomplete", strings.contains("constraint"))
        assertTrue("Missing 'setof' in autocomplete", strings.contains("setof"))
        assertTrue("Missing 'struct' in autocomplete", strings.contains("struct"))
        assertTrue("Missing 'card' in autocomplete", strings.contains("card"))
        assertTrue("Missing 'endOf' in autocomplete", strings.contains("endOf"))
    }

    fun testContextualCompletion() {
        myFixture.configureByText(
            OplFileType,
            """
                dvar int myVar1 in 1..10;
                dvar boolean myVar2;
                constraint myCt1[1..10];
                
                minimize my<caret>
            """.trimIndent()
        )
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings
        assertNotNull("No completion results", strings)
        assertTrue("Missing 'myVar1' in autocomplete", strings!!.contains("myVar1"))
        assertTrue("Missing 'myVar2' in autocomplete", strings.contains("myVar2"))
        assertTrue("Missing 'myCt1' in autocomplete", strings.contains("myCt1"))
    }
}
