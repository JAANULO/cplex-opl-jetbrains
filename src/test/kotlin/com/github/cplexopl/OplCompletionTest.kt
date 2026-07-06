package com.github.cplexopl

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplCompletionTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String = "src/test/testData/completion"

    fun testKeywordCompletion() {
        myFixture.configureByFile("keywordCompletion.mod")
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings
        assertNotNull("Brak wyników podpowiedzi (null)", strings)
        assertTrue("Brak 'minimize' w autouzupełnianiu", strings!!.contains("minimize"))
        assertTrue("Brak 'dvar' w autouzupełnianiu", strings.contains("dvar"))
    }

    fun testContextualCompletion() {
        myFixture.configureByFile("contextualCompletion.mod")
        myFixture.completeBasic()
        val strings = myFixture.lookupElementStrings
        assertNotNull("Brak wyników podpowiedzi", strings)
        assertTrue("Brak 'myVar1' w autouzupełnianiu", strings!!.contains("myVar1"))
        assertTrue("Brak 'myVar2' w autouzupełnianiu", strings.contains("myVar2"))
    }
}
