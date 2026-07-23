package com.github.cplexopl

import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplCommenterTest : BasePlatformTestCase() {

    fun testCommentAction() {
        myFixture.configureByText(OplFileType, "<selection>dvar int x;\ndvar int y;</selection>\n")
        myFixture.performEditorAction(com.intellij.openapi.actionSystem.IdeActions.ACTION_COMMENT_LINE)
        myFixture.checkResult("//<selection>dvar int x;\n//dvar int y;</selection>\n")
    }
}
