package com.github.cplexopl

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplFormattingTest : BasePlatformTestCase() {

    fun testBasicFormatting() {
        myFixture.configureByText(
            OplFileType,
            """
            subject to{
            x>=0;
            forall(i in R)
            {
            y[i] <= 10;
            }
            }
            
            execute{
            writeln("Hello");
            }
            """.trimIndent()
        )
        WriteCommandAction.runWriteCommandAction(project) {
            CodeStyleManager.getInstance(project).reformat(myFixture.file)
        }
        myFixture.checkResult(
            """
            subject to {
                x >= 0;
                forall(i in R) {
                    y[i] <= 10;
                }
            }
            
            execute {
                writeln("Hello");
            }
            """.trimIndent()
        )
    }
}
