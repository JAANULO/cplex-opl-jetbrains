package com.github.cplexopl.statistics

import com.intellij.openapi.diagnostic.Logger

/**
 * Bezpieczny zbieracz zdarzeń i metryk operacyjnych pluginu CPLEX OPL.
 *
 * Loguje zdarzenia za pośrednictwem oficjalnego, publicznego Loggera platformy IntelliJ,
 * zapewniając brak naruszeń Internal API (FUS) oraz pełną odporność na błędy (Fail-Safe).
 */
object OplUsageCollector {
    private val LOG = Logger.getInstance(OplUsageCollector::class.java)

    enum class ExecutionSource { ACTION, GUTTER, RUN_CONFIG }
    enum class ExitStatus { SUCCESS, SOLVER_ERROR, PATH_NOT_CONFIGURED, EXECUTION_CANCELLED, INTERNAL_EXCEPTION }
    enum class FileTypeEnum { MOD, DAT, OPS }
    enum class DetectionSource { AUTO_DETECTED_DEFAULT_DIR, ENV_VAR, CUSTOM_SETTINGS, NOT_FOUND }

    /**
     * Rejestruje wykonanie modelu solvera CPLEX.
     */
    fun logModelExecuted(
        hasDat: Boolean,
        hasOps: Boolean,
        source: ExecutionSource,
        status: ExitStatus,
        exitCode: Int = 0
    ) {
        LOG.info("OPL Metric [model.executed]: hasDat=$hasDat, hasOps=$hasOps, source=$source, status=$status, exitCode=$exitCode")
    }

    /**
     * Rejestruje utworzenie pliku OPL (.mod / .dat / .ops).
     */
    fun logFileCreated(fileType: FileTypeEnum) {
        LOG.info("OPL Metric [file.created]: fileType=$fileType")
    }

    /**
     * Rejestruje wygenerowanie skryptu runnera Pythona.
     */
    fun logPythonRunnerGenerated(success: Boolean) {
        LOG.info("OPL Metric [python.runner.generated]: success=$success")
    }

    /**
     * Rejestruje źródło wykrytej lub ustawionej ścieżki CPLEX.
     */
    fun logCplexDetection(source: DetectionSource) {
        LOG.info("OPL Metric [cplex.detection]: source=$source")
    }
}
