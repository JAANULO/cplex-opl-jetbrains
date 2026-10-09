package com.github.cplexopl.statistics

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplUsageCollectorTest : BasePlatformTestCase() {

    fun testGroupInitialization() {
        val group = OplUsageCollector.group
        assertEquals("cplex.opl", group.id)
        assertEquals(1, group.version)
    }

    fun testFailSafeLoggingDoesNotThrow() {
        // Weryfikacja, że wywołanie metod logujących w środowisku testowym nie rzuca wyjątków
        OplUsageCollector.logModelExecuted(
            hasDat = true,
            hasOps = false,
            source = OplUsageCollector.ExecutionSource.ACTION,
            status = OplUsageCollector.ExitStatus.SUCCESS,
            exitCode = 0
        )

        OplUsageCollector.logModelExecuted(
            hasDat = false,
            hasOps = true,
            source = OplUsageCollector.ExecutionSource.RUN_CONFIG,
            status = OplUsageCollector.ExitStatus.SOLVER_ERROR,
            exitCode = 1
        )

        OplUsageCollector.logFileCreated(OplUsageCollector.FileTypeEnum.MOD)
        OplUsageCollector.logFileCreated(OplUsageCollector.FileTypeEnum.DAT)
        OplUsageCollector.logFileCreated(OplUsageCollector.FileTypeEnum.OPS)

        OplUsageCollector.logPythonRunnerGenerated(true)
        OplUsageCollector.logPythonRunnerGenerated(false)

        OplUsageCollector.logCplexDetection(OplUsageCollector.DetectionSource.AUTO_DETECTED_DEFAULT_DIR)
        OplUsageCollector.logCplexDetection(OplUsageCollector.DetectionSource.ENV_VAR)
        OplUsageCollector.logCplexDetection(OplUsageCollector.DetectionSource.CUSTOM_SETTINGS)
        OplUsageCollector.logCplexDetection(OplUsageCollector.DetectionSource.NOT_FOUND)
    }
}
