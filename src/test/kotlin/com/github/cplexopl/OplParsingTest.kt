package com.github.cplexopl

import com.github.cplexopl.parser.OplParserDefinition
import com.intellij.testFramework.ParsingTestCase

class OplParsingTest : ParsingTestCase("parsing", "mod", OplParserDefinition()) {

    override fun getTestDataPath(): String = "src/test/testData"

    override fun skipSpaces(): Boolean = false

    override fun includeRanges(): Boolean = true

    fun testSimpleModel() {
        doTest(true)
    }

    fun testComplexModel() {
        doTest(true)
    }
}
