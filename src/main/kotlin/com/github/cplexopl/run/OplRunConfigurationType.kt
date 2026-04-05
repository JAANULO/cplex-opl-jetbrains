package com.github.cplexopl.run

import com.intellij.execution.configurations.*
import com.intellij.openapi.project.Project
import com.intellij.icons.AllIcons

// ConfigurationType = pozycja w liście "Add New Configuration" (+ w dropdown Run)
class OplRunConfigurationType : ConfigurationTypeBase(
    "OPL_RUN",                          // Unikalny identyfikator
    "OPL Model",                        // Nazwa wyświetlana
    "Run IBM CPLEX OPL model",         // Opis
    AllIcons.RunConfigurations.Application  // Ikona
) {
    init {
        // Każdy ConfigurationType musi mieć przynajmniej jedną fabrykę
        addFactory(OplConfigurationFactory(this))
    }

    companion object {
        // Singleton - łatwy dostęp do instancji z innych miejsc
        fun getInstance(): OplRunConfigurationType =
            ConfigurationTypeUtil.findConfigurationType(OplRunConfigurationType::class.java)
    }
}

// ConfigurationFactory = tworzy nowe instancje konfiguracji
class OplConfigurationFactory(type: ConfigurationType) : ConfigurationFactory(type) {
    override fun getId(): String = "OPL_CONFIGURATION_FACTORY"

    override fun createTemplateConfiguration(project: Project): RunConfiguration {
        return OplRunConfiguration(project, this, "OPL Model")
    }

    override fun getOptionsClass() = OplRunConfigurationOptions::class.java
}

// Options = klasa przechowująca ustawienia konfiguracji (serializowana do XML)
// Serializacja = zamiana obiektu Kotlin na XML do zapisania w pliku .idea/
class OplRunConfigurationOptions : RunConfigurationOptions() {
    private val _modelFile = string("").provideDelegate(this, ::modelFile)
    private val _dataFile = string("").provideDelegate(this, ::dataFile)
    private val _cplexPath = string("").provideDelegate(this, ::cplexPath)

    var modelFile: String? by _modelFile
    var dataFile: String? by _dataFile
    var cplexPath: String? by _cplexPath
}
