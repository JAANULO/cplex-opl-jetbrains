package com.github.cplexopl.run

import com.intellij.execution.configurations.*
import com.intellij.openapi.project.Project
import com.intellij.icons.AllIcons

// ConfigurationType = entry in "Add New Configuration" list (+ in Run dropdown)
class OplRunConfigurationType : ConfigurationTypeBase(
    "OPL_RUN",                          // Unique identifier
    "OPL Model",                        // Displayed name
    "Run IBM CPLEX OPL model",         // Description
    AllIcons.RunConfigurations.Application  // Icon
) {
    init {
        // Each ConfigurationType must have at least one factory
        addFactory(OplConfigurationFactory(this))
    }

    companion object {
        // Singleton - easy access to instance from other places
        fun getInstance(): OplRunConfigurationType =
            ConfigurationTypeUtil.findConfigurationType(OplRunConfigurationType::class.java)
    }
}

// ConfigurationFactory = creates new configuration instances
class OplConfigurationFactory(type: ConfigurationType) : ConfigurationFactory(type) {
    override fun getId(): String = "OPL_CONFIGURATION_FACTORY"

    override fun createTemplateConfiguration(project: Project): RunConfiguration {
        return OplRunConfiguration(project, this, "OPL Model")
    }

    override fun getOptionsClass() = OplRunConfigurationOptions::class.java
}

// Options = class storing configuration settings (serialized to XML)
// Serialization = conversion of Kotlin object to XML to save in .idea/ file
class OplRunConfigurationOptions : RunConfigurationOptions() {
    private val _modelFile = string("").provideDelegate(this, ::modelFile)
    private val _dataFile = string("").provideDelegate(this, ::dataFile)
    private val _settingsFile = string("").provideDelegate(this, ::settingsFile)
    private val _cplexPath = string("").provideDelegate(this, ::cplexPath)

    var modelFile: String? by _modelFile
    var dataFile: String? by _dataFile
    var settingsFile: String? by _settingsFile
    var cplexPath: String? by _cplexPath
}
