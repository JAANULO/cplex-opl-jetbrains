package com.github.cplexopl

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplCompletionTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String = "src/test/testData/completion"

    fun testKeywordCompletion() {
        myFixture.configureByFile("keywordCompletion.mod")
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings
        assertNotNull("No completion results (null)", strings)
        assertTrue("Missing 'minimize' in autocomplete", strings!!.contains("minimize"))
        assertTrue("Missing 'dvar' in autocomplete", strings.contains("dvar"))
    }

    fun testContextualCompletion() {
        myFixture.configureByFile("contextualCompletion.mod")
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings
        assertNotNull("No completion results", strings)
        assertTrue("Missing 'myVar1' in autocomplete", strings!!.contains("myVar1"))
        assertTrue("Missing 'myVar2' in autocomplete", strings.contains("myVar2"))
    }
}
