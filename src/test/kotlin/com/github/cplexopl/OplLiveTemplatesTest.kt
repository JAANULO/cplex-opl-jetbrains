package com.github.cplexopl

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplLiveTemplatesTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String = "src/test/testData/templates"

    fun testModelTemplate() {
        myFixture.configureByFile("liveTemplate.mod")
        myFixture.type("\t") // Trigger expansion
        // Przejdź tabulatorami przez wszystkie 5 zmiennych szablonu (NAME, DESCRIPTION, N, CTNAME, CONSTRAINT), aby zakończyć sesję szablonu
        repeat(5) {
            myFixture.type("\t")
        }
        com.intellij.openapi.command.WriteCommandAction.runWriteCommandAction(project) {
            com.intellij.psi.codeStyle.CodeStyleManager.getInstance(project).reformat(myFixture.file)
        }
        myFixture.checkResultByFile("liveTemplate_after.mod")
    }
}
