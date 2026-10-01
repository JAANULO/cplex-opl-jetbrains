package com.github.cplexopl.completion

import com.github.cplexopl.OplFileType
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplBuiltinsCompletionTest : BasePlatformTestCase() {

    fun testCpBuiltinsCompletion() {
        myFixture.configureByText(OplFileType, "// Autocomplete CP built-ins\n<caret>")
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings
        assertNotNull("No completion results", strings)

        // CP constraints & functions
        assertTrue("Missing 'span' in autocomplete", strings!!.contains("span"))
        assertTrue("Missing 'alternative' in autocomplete", strings.contains("alternative"))
        assertTrue("Missing 'synchronize' in autocomplete", strings.contains("synchronize"))
        assertTrue("Missing 'forbidStart' in autocomplete", strings.contains("forbidStart"))
        assertTrue("Missing 'stepAt' in autocomplete", strings.contains("stepAt"))
        assertTrue("Missing 'count' in autocomplete", strings.contains("count"))
        assertTrue("Missing 'distribute' in autocomplete", strings.contains("distribute"))
        assertTrue("Missing 'inverse' in autocomplete", strings.contains("inverse"))
        assertTrue("Missing 'startBeforeStart' in autocomplete", strings.contains("startBeforeStart"))
        assertTrue("Missing 'endBeforeEnd' in autocomplete", strings.contains("endBeforeEnd"))
        assertTrue("Missing 'startOfNext' in autocomplete", strings.contains("startOfNext"))

        // Math & set functions
        assertTrue("Missing 'powerset' in autocomplete", strings.contains("powerset"))
        assertTrue("Missing 'standardDeviation' in autocomplete", strings.contains("standardDeviation"))
        assertTrue("Missing 'sgn' in autocomplete", strings.contains("sgn"))
        assertTrue("Missing 'dist' in autocomplete", strings.contains("dist"))
    }

    fun testScriptSymbolsCompletion() {
        myFixture.configureByText(OplFileType, "// Autocomplete script symbols\n<caret>")
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings
        assertNotNull("No completion results", strings)

        // Script globals & instances
        assertTrue("Missing 'thisOplModel' in autocomplete", strings!!.contains("thisOplModel"))
        assertTrue("Missing 'cplex' in autocomplete", strings.contains("cplex"))
        assertTrue("Missing 'cp' in autocomplete", strings.contains("cp"))
        assertTrue("Missing 'Opl' in autocomplete", strings.contains("Opl"))
        assertTrue("Missing 'writeln' in autocomplete", strings.contains("writeln"))
        assertTrue("Missing 'write' in autocomplete", strings.contains("write"))

        // Script classes
        assertTrue("Missing 'IloOplOutputFile' in autocomplete", strings.contains("IloOplOutputFile"))
        assertTrue("Missing 'IloOplModel' in autocomplete", strings.contains("IloOplModel"))
        assertTrue("Missing 'IloOplDataElements' in autocomplete", strings.contains("IloOplDataElements"))
        assertTrue("Missing 'IloOplCallJava' in autocomplete", strings.contains("IloOplCallJava"))
    }

    fun testFunctionParenthesesInsertHandler() {
        myFixture.configureByText(OplFileType, "spa<caret>")
        myFixture.completeBasic()
        myFixture.checkResult("span(<caret>)")
    }
}
