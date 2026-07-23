package com.github.cplexopl

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplHighlightingTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String = "src/test/testData/highlighting"

    fun testHighlightingFeatures() {
        myFixture.testHighlighting(true, true, true, "highlighting_tests.mod")
    }
}
