package com.github.cplexopl

import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplCommenterTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String = "src/test/testData/features"

    fun testCommentAction() {
        myFixture.configureByFile("commenterAction.mod")
        myFixture.performEditorAction(com.intellij.openapi.actionSystem.IdeActions.ACTION_COMMENT_LINE)
        println("COMMENTER_OUTPUT_BEGIN")
        println(myFixture.file.text)
        println("COMMENTER_OUTPUT_END")
        myFixture.checkResultByFile("commenterAction_after.mod")
    }
}
