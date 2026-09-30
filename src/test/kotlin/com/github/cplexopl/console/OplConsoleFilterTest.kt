package com.github.cplexopl.console

import com.intellij.execution.filters.Filter
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Assert
import java.io.File

class OplConsoleFilterTest : BasePlatformTestCase() {

    override fun setUp() {
        super.setUp()
        val basePath = project.basePath ?: return
        val baseDir = File(basePath)
        if (!baseDir.exists()) {
            baseDir.mkdirs()
        }
        File(baseDir, "infeasible-test.mod").createNewFile()
        File(baseDir, "infeasible-test.dat").createNewFile()
    }

    fun testLinkFilterMatchesStandardError() {
        val filter = OplLinkFilter(project)
        val line = "Error at infeasible-test.mod:12:34: syntax error"
        val result = filter.applyFilter(line, line.length)
        
        Assert.assertNotNull("Filter should match error line with line and column", result)
    }

    fun testLinkFilterIgnoresNonMatchingLines() {
        val filter = OplLinkFilter(project)
        val line = "CPLEX 22.1.2.0: Optimal solution found."
        val result = filter.applyFilter(line, line.length)
        
        Assert.assertNull("Filter should ignore non-matching lines", result)
    }

    fun testInfeasibilityFilterMatchesConflict() {
        val filter = OplInfeasibilityFilter(project)
        val line = "ctDemand at 4:17-25 infeasible-test.mod"
        
        val result = filter.applyFilter(line, line.length)
        Assert.assertNotNull("Filter should match conflict constraint line", result)
    }

    fun testInfeasibilityFilterMatchesDatFile() {
        val filter = OplInfeasibilityFilter(project)
        val line = "capacity_bound at 102:5-12 infeasible-test.dat"
        
        val result = filter.applyFilter(line, line.length)
        Assert.assertNotNull("Filter should match .dat files", result)
    }

    fun testInfeasibilityFilterMatchesTempFilePath() {
        val filter = OplInfeasibilityFilter(project)
        val line = "ct1 at 4:8-16 C:\\Users\\atona\\AppData\\Local\\Temp\\_temp_242972bd-5b07-4fbf-b663-1084e80f9198_infeasible-test.mod"
        
        val result = filter.applyFilter(line, line.length)
        Assert.assertNotNull("Filter should match conflict line even with temp file path", result)
    }

    fun testInfeasibilityFilterIgnoresIrrelevantOutput() {
        val filter = OplInfeasibilityFilter(project)
        val line = "Version identifier: 22.1.2.0 | 2024-11-25 | 0edbb82fd"
        
        val result = filter.applyFilter(line, line.length)
        Assert.assertNull("Filter should ignore version info", result)
    }
}
