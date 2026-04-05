package com.github.cplexopl.run

import com.github.cplexopl.OplFileType
import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.LazyRunConfigurationProducer
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement

// RunConfigurationProducer = automatycznie tworzy konfigurację Run
// gdy użytkownik kliknie prawym przyciskiem na plik .mod → "Run 'model.mod'"
class OplRunConfigurationProducer : LazyRunConfigurationProducer<OplRunConfiguration>() {

    override fun getConfigurationFactory(): ConfigurationFactory =
        OplRunConfigurationType.getInstance().configurationFactories[0]

    // Czy ten producer pasuje do danego elementu PSI (kursora w kodzie)?
    override fun isConfigurationFromContext(
        configuration: OplRunConfiguration,
        context: ConfigurationContext
    ): Boolean {
        val file = context.location?.virtualFile ?: return false
        return file.fileType == OplFileType && configuration.modelFile == file.path
    }

    // Utwórz konfigurację na podstawie kontekstu (kliknięty plik)
    override fun setupConfigurationFromContext(
        configuration: OplRunConfiguration,
        context: ConfigurationContext,
        sourceElement: Ref<PsiElement>
    ): Boolean {
        val file = context.location?.virtualFile ?: return false
        if (file.fileType != OplFileType) return false

        configuration.modelFile = file.path
        configuration.name = file.nameWithoutExtension

        // Spróbuj automatycznie znaleźć plik .dat o tej samej nazwie
        val dataFile = file.parent?.findChild("${file.nameWithoutExtension}.dat")
        if (dataFile != null) {
            configuration.dataFile = dataFile.path
        }

        // Wykryj ścieżkę CPLEX
        val detectedPath = OplRunConfiguration.detectCplexPath()
        if (detectedPath.isNotEmpty()) {
            configuration.cplexPath = detectedPath
        }

        return true
    }
}
