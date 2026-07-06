package com.github.cplexopl

import com.github.cplexopl.run.OplRunConfigurationType
import com.intellij.execution.configurations.ConfigurationTypeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplRunConfigurationTest : BasePlatformTestCase() {

    fun testRunConfigurationRegistration() {
        val configurationType = ConfigurationTypeUtil.findConfigurationType(OplRunConfigurationType::class.java)
        assertNotNull("OplRunConfigurationType nie zostało poprawnie zarejestrowane", configurationType)
        assertEquals("OPL Model", configurationType.displayName)
    }
}
