package com.github.cplexopl.run

import com.intellij.execution.configurations.ConfigurationTypeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Test
import java.io.File

class OplRunConfigurationIntegrationTest : BasePlatformTestCase() {

    @Test
    fun testGeneratedCommandLines() {
        val factory = ConfigurationTypeUtil.findConfigurationType(OplRunConfigurationType::class.java)
            .configurationFactories[0]
        val config = OplRunConfiguration(project, factory, "TestConfig")

        // Set up dummy files
        val tempModelFile = File.createTempFile("temp_model", ".mod")
        tempModelFile.deleteOnExit()
        
        config.cplexPath = "/opt/ibm/ILOG/CPLEX_Studio/opl/bin/x86-64_linux/oplrun"
        config.modelFile = "C:\\MyProject\\model.mod"
        config.dataFile = "C:\\MyProject\\data.dat"

        // 1. LOCAL
        config.executionMode = ExecutionMode.LOCAL
        config.cplexPath = "C:\\Program Files\\IBM\\ILOG\\CPLEX_Studio\\opl\\bin\\x64_win64\\oplrun.exe"
        val cmdLocal = config.createCommandLine(tempModelFile)
        assertNotNull(cmdLocal.commandLineString)

        // 2. WSL
        config.executionMode = ExecutionMode.WSL
        config.wslDistribution = "" // Default
        config.cplexPath = "/opt/ibm/ILOG/CPLEX_Studio/opl/bin/x86-64_linux/oplrun"
        val cmdWsl = config.createCommandLine(tempModelFile)

        // 3. DOCKER
        config.executionMode = ExecutionMode.DOCKER
        config.dockerImage = "cplex-image:latest"
        val cmdDocker = config.createCommandLine(tempModelFile)
        
        assertNotNull(cmdWsl.commandLineString)
        assertTrue(cmdWsl.commandLineString.contains("wsl.exe"))
        assertTrue(cmdDocker.commandLineString.contains("docker run"))
    }
}
