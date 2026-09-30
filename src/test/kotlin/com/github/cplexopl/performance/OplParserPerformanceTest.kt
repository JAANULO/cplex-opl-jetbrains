package com.github.cplexopl.performance

import com.intellij.psi.PsiDocumentManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import kotlin.system.measureTimeMillis

class OplParserPerformanceTest : BasePlatformTestCase() {

    fun testPrattParserExpressionPerformance() {
        // Generate a massive equation with thousands of arithmetic operations,
        // which on the old grammar would kill the CPU with deep recursion.
        val numElements = 5000 
        val sb = java.lang.StringBuilder()
        sb.append("subject to {\n")
        sb.append("  wielkie_rownanie: ")
        for (i in 1..numElements) {
            sb.append("x_$i * 2.5 + ")
        }
        sb.append("0 <= 1000;\n")
        sb.append("}\n")

        val code = sb.toString()
        
        // Warmup with a smaller expression
        myFixture.configureByText("parser_warmup.mod", "subject to { x * 2.5 + 0 <= 1000; }")
        PsiDocumentManager.getInstance(project).commitAllDocuments()

        val time = measureTimeMillis {
            myFixture.configureByText("parser_perf.mod", code)
            PsiDocumentManager.getInstance(project).commitAllDocuments()
        }
        
        val timeLimit = 2000L
        assertTrue(
            "Expression parsing took too long ($time ms). Limit is $timeLimit ms.",
            time < timeLimit
        )
    }
}
