package com.github.cplexopl

import com.intellij.psi.PsiDocumentManager
import com.intellij.testFramework.PlatformTestUtil
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
        
        // Standard timing test (AST parsing only, without full Annotator)
        val time = measureTimeMillis {
            myFixture.configureByText("parser_perf.mod", code)
            PsiDocumentManager.getInstance(project).commitAllDocuments()
        }
        
        println("Parsing time for $numElements elements after Pratt Parser optimization: $time ms")
        
        // Automatic timeout test (e.g., max 1000 ms)
        val timeLimit = 1000L
        assertTrue(
            "Expression parsing took too long ($time ms). Limit is $timeLimit ms.",
            time < timeLimit
        )
    }
}
