package com.github.cplexopl.features

import com.github.cplexopl.OplFileType
import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplCommenterTest : BasePlatformTestCase() {

    fun testCommentAction() {
        myFixture.configureByText(OplFileType, "<selection>dvar int x;\ndvar int y;</selection>\n")
        myFixture.performEditorAction(IdeActions.ACTION_COMMENT_LINE)
        myFixture.checkResult("//<selection>dvar int x;\n//dvar int y;</selection>\n")
    }
}
