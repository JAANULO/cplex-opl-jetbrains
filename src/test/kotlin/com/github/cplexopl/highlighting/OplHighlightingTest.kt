package com.github.cplexopl.highlighting

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplHighlightingTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String = "src/test/testData/highlighting"

    fun testHighlightingFeatures() {
        myFixture.testHighlighting(true, true, true, "highlighting_tests.mod")
    }

    fun testKeywords() {
        myFixture.testHighlighting(true, true, true, "keywords.mod")
    }

    fun testBuiltins() {
        myFixture.testHighlighting(true, true, true, "cplex_builtins_test.mod")
    }

    fun testCpMissingUsingCp() {
        myFixture.testHighlighting(true, true, true, "cp_missing_using_cp_test.mod")
    }

    fun testAddUsingCpQuickFix() {
        myFixture.configureByText("test_fix.mod", """
            dvar interval a;
            dvar interval b[1..5];
            subject to {
                <caret>span(a, all(i in 1..5) b[i]);
            }
        """.trimIndent())
        myFixture.doHighlighting()
        val intention = myFixture.findSingleIntention("Insert 'using CP;' declaration at top of file")
        assertNotNull(intention)
        myFixture.launchAction(intention)
        myFixture.checkResult("""
            using CP;
            dvar interval a;
            dvar interval b[1..5];
            subject to {
                span(a, all(i in 1..5) b[i]);
            }
        """.trimIndent())
    }
}
