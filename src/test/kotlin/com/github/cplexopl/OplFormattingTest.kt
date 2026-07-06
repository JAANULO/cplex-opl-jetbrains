package com.github.cplexopl

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplFormattingTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String = "src/test/testData/formatter"

    fun testBasicFormatting() {
        myFixture.configureByFile("formattingBefore.mod")
        WriteCommandAction.runWriteCommandAction(project) {
            CodeStyleManager.getInstance(project).reformat(myFixture.file)
        }
        println("FORMATTING_OUTPUT_BEGIN")
        println(myFixture.file.text)
        println("FORMATTING_OUTPUT_END")
        myFixture.checkResultByFile("formattingAfter.mod")
    }
}
