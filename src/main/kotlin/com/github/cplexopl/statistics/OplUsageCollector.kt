package com.github.cplexopl.statistics

import com.intellij.internal.statistic.eventLog.EventLogGroup
import com.intellij.internal.statistic.eventLog.events.EventFields
import com.intellij.internal.statistic.service.fus.collectors.CounterUsagesCollector
import com.intellij.openapi.diagnostic.Logger

/**
 * Zbieracz zdarzeń anonimowej telemetrii w oparciu o JetBrains Feature Usage Statistics (FUS).
 *
 * Gwarantuje brak PII (Personally Identifiable Information) oraz odporność na błędy (Fail-Safe).
 */
object OplUsageCollector : CounterUsagesCollector() {
    private val LOG = Logger.getInstance(OplUsageCollector::class.java)
    private val GROUP = EventLogGroup("cplex.opl", 1)

    enum class ExecutionSource { ACTION, GUTTER, RUN_CONFIG }
    enum class ExitStatus { SUCCESS, SOLVER_ERROR, PATH_NOT_CONFIGURED, EXECUTION_CANCELLED, INTERNAL_EXCEPTION }
    enum class FileTypeEnum { MOD, DAT, OPS }
    enum class DetectionSource { AUTO_DETECTED_DEFAULT_DIR, ENV_VAR, CUSTOM_SETTINGS, NOT_FOUND }

    // Pola dla model.executed
    private val HAS_DAT_FIELD = EventFields.Boolean("has_dat_file")
    private val HAS_OPS_FIELD = EventFields.Boolean("has_ops_file")
    private val EXECUTION_SOURCE_FIELD = EventFields.Enum("execution_source", ExecutionSource::class.java)
    private val EXIT_STATUS_FIELD = EventFields.Enum("exit_status", ExitStatus::class.java)
    private val EXIT_CODE_FIELD = EventFields.Int("exit_code")

    // 1. Zdarzenie wykonania modelu solvera CPLEX (vararg dla >3 pól)
    private val MODEL_EXECUTED_EVENT = GROUP.registerVarargEvent(
        "model.executed",
        HAS_DAT_FIELD,
        HAS_OPS_FIELD,
        EXECUTION_SOURCE_FIELD,
        EXIT_STATUS_FIELD,
        EXIT_CODE_FIELD
    )

    // 2. Zdarzenie utworzenia nowego pliku OPL
    private val FILE_TYPE_FIELD = EventFields.Enum("file_type", FileTypeEnum::class.java)
    private val FILE_CREATED_EVENT = GROUP.registerEvent("file.created", FILE_TYPE_FIELD)

    // 3. Zdarzenie wygenerowania runnera Python (docplex)
    private val PYTHON_SUCCESS_FIELD = EventFields.Boolean("success")
    private val PYTHON_RUNNER_EVENT = GROUP.registerEvent("python.runner.generated", PYTHON_SUCCESS_FIELD)

    // 4. Zdarzenie detekcji/konfiguracji ścieżki CPLEX
    private val DETECTION_SOURCE_FIELD = EventFields.Enum("detection_source", DetectionSource::class.java)
    private val CPLEX_DETECTION_EVENT = GROUP.registerEvent("cplex.detection", DETECTION_SOURCE_FIELD)

    override fun getGroup(): EventLogGroup = GROUP

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
        runCatching {
            MODEL_EXECUTED_EVENT.log(
                HAS_DAT_FIELD.with(hasDat),
                HAS_OPS_FIELD.with(hasOps),
                EXECUTION_SOURCE_FIELD.with(source),
                EXIT_STATUS_FIELD.with(status),
                EXIT_CODE_FIELD.with(exitCode)
            )
        }.onFailure { e ->
            LOG.debug("Failed to record FUS model.executed event", e)
        }
    }

    /**
     * Rejestruje utworzenie pliku OPL (.mod / .dat / .ops).
     */
    fun logFileCreated(fileType: FileTypeEnum) {
        runCatching {
            FILE_CREATED_EVENT.log(fileType)
        }.onFailure { e ->
            LOG.debug("Failed to record FUS file.created event", e)
        }
    }

    /**
     * Rejestruje wygenerowanie skryptu uruchomieniowego Pythona.
     */
    fun logPythonRunnerGenerated(success: Boolean) {
        runCatching {
            PYTHON_RUNNER_EVENT.log(success)
        }.onFailure { e ->
            LOG.debug("Failed to record FUS python.runner.generated event", e)
        }
    }

    /**
     * Rejestruje źródło wykrytej lub ustawionej ścieżki CPLEX.
     */
    fun logCplexDetection(source: DetectionSource) {
        runCatching {
            CPLEX_DETECTION_EVENT.log(source)
        }.onFailure { e ->
            LOG.debug("Failed to record FUS cplex.detection event", e)
        }
    }
}
