package com.github.cplexopl.performance

import com.github.cplexopl.console.OplInfeasibilityFilter
import com.github.cplexopl.console.OplLinkFilter
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Assert.assertTrue
import java.io.File
import kotlin.system.measureTimeMillis

class OplConsoleFilterPerformanceTest : BasePlatformTestCase() {

    override fun setUp() {
        super.setUp()
        val basePath = project.basePath ?: return
        val baseDir = File(basePath)
        if (!baseDir.exists()) {
            baseDir.mkdirs()
        }
        File(baseDir, "perf-test.mod").createNewFile()
    }

    fun testConsoleFilterPerformance100kLines() {
        val linkFilter = OplLinkFilter(project)
        val infeasibilityFilter = OplInfeasibilityFilter(project)

        val numLines = 100_000
        val sampleLines = listOf(
            "CPLEX 22.1.2.0: Optimal solution found.",
            "Iteration 1200: Objective = 452.1234",
            "ctInfeasible at 4:17-25 perf-test.mod",
            "Error: perf-test.mod:42",
            "Presolve time = 0.02 sec. (12.45 ticks)",
            "Gomory fractional cuts applied: 4"
        )

        // Warmup (1k lines)
        for (i in 0 until 1000) {
            val line = sampleLines[i % sampleLines.size]
            val len = line.length
            linkFilter.applyFilter(line, len)
            infeasibilityFilter.applyFilter(line, len)
        }

        val linesToProcess = ArrayList<String>(numLines)
        for (i in 0 until numLines) {
            linesToProcess.add(sampleLines[i % sampleLines.size])
        }

        val elapsed = measureTimeMillis {
            for (line in linesToProcess) {
                val len = line.length
                linkFilter.applyFilter(line, len)
                infeasibilityFilter.applyFilter(line, len)
            }
        }

        val timeLimit = 6000L
        assertTrue(
            "Filtering 100k lines took too long ($elapsed ms). Limit is $timeLimit ms.",
            elapsed < timeLimit
        )
    }

    fun testCatastrophicBacktrackingOnGiantLine() {
        val linkFilter = OplLinkFilter(project)
        val infeasibilityFilter = OplInfeasibilityFilter(project)

        // Create a giant line (5 million characters) without a valid file syntax
        val giantLine = "X".repeat(5_000_000)
        val len = giantLine.length

        val elapsed = measureTimeMillis {
            linkFilter.applyFilter(giantLine, len)
            infeasibilityFilter.applyFilter(giantLine, len)
        }

        val timeLimit = 300L
        assertTrue(
            "Filtering giant line took too long ($elapsed ms). Limit is $timeLimit ms.",
            elapsed < timeLimit
        )
    }
}
