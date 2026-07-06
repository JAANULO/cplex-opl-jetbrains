package com.github.cplexopl

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplHighlightingTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String = "src/test/testData/highlighting"

    fun testDuplicateVariables() {
        myFixture.configureByFile("duplicateVariables.mod")
        myFixture.checkHighlighting(true, false, true)
    }

    fun testUndefinedVariable() {
        myFixture.testHighlighting(true, false, true, "undefinedVariable.mod")
    }

    // fun testMissingSemicolon() {
    //     myFixture.testHighlighting(true, true, true, "missingSemicolon.mod")
    // }

    fun testInspections() {
        myFixture.testHighlighting(true, true, true, "inspections.mod")
    }

    fun testBooleanRangeError() {
        myFixture.configureByFile("booleanRangeError.mod")
        myFixture.checkHighlighting(true, false, true)
    }
}
