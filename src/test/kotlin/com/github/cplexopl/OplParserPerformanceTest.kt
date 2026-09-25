package com.github.cplexopl

import com.intellij.psi.PsiDocumentManager
import com.intellij.testFramework.PlatformTestUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import kotlin.system.measureTimeMillis

class OplParserPerformanceTest : BasePlatformTestCase() {

    fun testPrattParserExpressionPerformance() {
        // Generujemy potężne równanie z tysiącami operacji arytmetycznych,
        // co na starej gramatyce zabiłoby procesor głęboką rekurencją.
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
        
        // Zwykły pomiar czasu (tylko parsowanie drzewa AST, bez pełnego Annotatora)
        val time = measureTimeMillis {
            myFixture.configureByText("parser_perf.mod", code)
            PsiDocumentManager.getInstance(project).commitAllDocuments()
        }
        
        println("Czas parsowania $numElements elementów po optymalizacji Pratt Parser: $time ms")
        
        // Automatyczny test z progiem czasu (np. max 1000 ms)
        val timeLimit = 1000L
        assertTrue(
            "Parsowanie wyrażenia zajęło zbyt dużo czasu ($time ms). Limit to $timeLimit ms.",
            time < timeLimit
        )
    }
}
