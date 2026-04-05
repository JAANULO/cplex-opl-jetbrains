package com.github.cplexopl.run

import com.intellij.execution.Executor
import com.intellij.execution.configurations.*
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessHandlerFactory
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.project.Project
import java.io.File

// RunConfiguration = konfiguracja uruchomieniowa (to co widzisz w dropdown obok przycisku Run)
// Implementuje logikę co się stanie po kliknięciu zielonego przycisku ▶

class OplRunConfiguration(
    project: Project,
    factory: ConfigurationFactory,
    name: String
) : RunConfigurationBase<OplRunConfigurationOptions>(project, factory, name) {

    override fun getOptions() = super.getOptions() as OplRunConfigurationOptions

    var modelFile: String
        get() = options.modelFile ?: ""
        set(value) { options.modelFile = value }

    var dataFile: String
        get() = options.dataFile ?: ""
        set(value) { options.dataFile = value }

    var cplexPath: String
        get() = options.cplexPath ?: detectCplexPath()
        set(value) { options.cplexPath = value }

    override fun getConfigurationEditor() = OplRunConfigurationEditor(project)

    override fun checkConfiguration() {
        if (modelFile.isEmpty()) throw RuntimeConfigurationError("Model file (.mod) not specified")
        if (!File(modelFile).exists()) throw RuntimeConfigurationError("Model file does not exist: $modelFile")
        if (cplexPath.isEmpty()) throw RuntimeConfigurationError(
            "CPLEX installation not found. Install IBM ILOG CPLEX or set path manually."
        )
    }

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState {
        return OplRunState(environment, this)
    }

    companion object {
        // Automatyczne wykrywanie ścieżki CPLEX na różnych systemach operacyjnych
        fun detectCplexPath(): String {
            val os = System.getProperty("os.name").lowercase()
            
            // Typowe ścieżki instalacji CPLEX na różnych systemach
            val candidates = when {
                os.contains("windows") -> listOf(
                    "C:\\Program Files\\IBM\\ILOG\\CPLEX_Studio2211\\opl\\bin\\x64_win64\\oplrun.exe",
                    "C:\\Program Files\\IBM\\ILOG\\CPLEX_Studio221\\opl\\bin\\x64_win64\\oplrun.exe",
                    "C:\\Program Files\\IBM\\ILOG\\CPLEX_Studio2010\\opl\\bin\\x64_win64\\oplrun.exe"
                )
                os.contains("mac") -> listOf(
                    "/Applications/CPLEX_Studio2211/opl/bin/x86-64_osx/oplrun",
                    "/Applications/CPLEX_Studio221/opl/bin/x86-64_osx/oplrun"
                )
                else -> listOf( // Linux
                    "/opt/ibm/ILOG/CPLEX_Studio2211/opl/bin/x86-64_linux/oplrun",
                    "/opt/ibm/ILOG/CPLEX_Studio221/opl/bin/x86-64_linux/oplrun",
                    System.getProperty("user.home") + "/CPLEX_Studio2211/opl/bin/x86-64_linux/oplrun"
                )
            }
            
            return candidates.firstOrNull { File(it).exists() } ?: ""
        }
    }
}

// RunProfileState = klasa wykonująca faktyczny proces (uruchamia oplrun jako subprocess)
class OplRunState(
    environment: ExecutionEnvironment,
    private val config: OplRunConfiguration
) : CommandLineState(environment) {

    override fun startProcess(): OSProcessHandler {
        // GeneralCommandLine = klasa budująca komendę systemową
        val commandLine = GeneralCommandLine().apply {
            exePath = config.cplexPath
            // oplrun przyjmuje: oplrun model.mod [data.dat]
            addParameter(config.modelFile)
            if (config.dataFile.isNotEmpty()) {
                addParameter(config.dataFile)
            }
            // Ustaw katalog roboczy na folder pliku modelu
            workDirectory = File(config.modelFile).parentFile
            // Przekaż środowisko systemowe (ważne dla licencji CPLEX)
            withEnvironment(System.getenv())
        }

        val handler = ProcessHandlerFactory.getInstance()
            .createColoredProcessHandler(commandLine)
        
        // Automatycznie wypisz kod wyjścia w konsoli
        ProcessTerminatedListener.attach(handler)
        return handler
    }
}
