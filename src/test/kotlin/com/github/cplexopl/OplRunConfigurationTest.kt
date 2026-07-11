package com.github.cplexopl

import com.github.cplexopl.run.OplRunConfiguration
import com.github.cplexopl.run.OplRunConfigurationType
import com.intellij.execution.configurations.ConfigurationTypeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import java.io.File

class OplRunConfigurationTest : BasePlatformTestCase() {

    fun testRunConfigurationRegistration() {
        val configurationType = ConfigurationTypeUtil.findConfigurationType(OplRunConfigurationType::class.java)
        assertNotNull("OplRunConfigurationType nie zostało poprawnie zarejestrowane", configurationType)
        assertEquals("OPL Model", configurationType.displayName)
    }

    fun testCommandLineConstruction() {
        val configurationType = OplRunConfigurationType.getInstance()
        val factory = configurationType.configurationFactories[0]
        val configuration = factory.createTemplateConfiguration(project) as OplRunConfiguration
        
        configuration.modelFile = "C:\\projects\\model.mod"
        configuration.dataFile = "C:\\projects\\data.dat"
        configuration.settingsFile = "C:\\projects\\settings.ops"
        configuration.cplexPath = "C:\\cplex\\bin\\oplrun.exe"

        val tempFile = java.io.File("C:\\projects\\_temp_model.mod")
        val commandLine = configuration.createCommandLine(tempFile)
        
        assertEquals("C:\\cplex\\bin\\oplrun.exe", commandLine.exePath)
        val parameters = commandLine.parametersList.list
        
        assertEquals(2, parameters.size)
        assertEquals("C:\\projects\\_temp_model.mod", parameters[0])
        assertEquals("C:\\projects\\data.dat", parameters[1])
    }

    fun testGenerateExecuteBlock() {
        val tempOpsFile = java.io.File.createTempFile("test_settings", ".ops")
        try {
            tempOpsFile.writeText("""
                <?xml version="1.0" encoding="UTF-8"?>
                <settings version="2">
                  <category name="cplex">
                    <setting name="workmem" value="4096"/>
                    <setting name="threads" value="true"/>
                    <setting name="logpath" value="cplex&quot;log.txt"/>
                  </category>
                </settings>
            """.trimIndent(), Charsets.UTF_8)

            val executeBlock = OplRunConfiguration.generateExecuteBlock(tempOpsFile.absolutePath)
            
            assertTrue("Blok execute nie zawiera workmem", executeBlock.contains("cplex.workmem = 4096;"))
            assertTrue("Blok execute nie zawiera threads", executeBlock.contains("cplex.threads = true;"))
            assertTrue("Blok execute nie zawiera ucieczki cudzysłowów w logpath", executeBlock.contains("cplex.logpath = \"cplex\\\"log.txt\";"))
        } finally {
            tempOpsFile.delete()
        }
    }
    
    fun testGenerateExecuteBlockWithAmpersand() {
        val tempOpsFile = java.io.File.createTempFile("test_settings_amp", ".ops")
        try {
            tempOpsFile.writeText("""
                <?xml version="1.0" encoding="UTF-8"?>
                <settings version="2">
                  <category name="cplex">
                    <setting name="path" value="file&amp;name.txt"/>
                    <setting name="quote" value="&quot;test&quot;"/>
                  </category>
                </settings>
            """.trimIndent(), Charsets.UTF_8)

            val executeBlock = OplRunConfiguration.generateExecuteBlock(tempOpsFile.absolutePath)
            
            assertTrue("Ampersand nie został zdekodowany prawidłowo", executeBlock.contains("cplex.path = \"file&name.txt\";"))
            assertTrue("Znaki cudzysłowu nie zostały zdekodowane prawidłowo", executeBlock.contains("cplex.quote = \"\\\"test\\\"\";"))
        } finally {
            tempOpsFile.delete()
        }
    }
    
    fun testParentFileNullHandling() {
        val configurationType = OplRunConfigurationType.getInstance()
        val factory = configurationType.configurationFactories[0]
        val configuration = factory.createTemplateConfiguration(project) as OplRunConfiguration
        
        configuration.modelFile = "model.mod"
        configuration.cplexPath = "C:\\cplex\\bin\\oplrun.exe"

        val tempFile = java.io.File("_temp_model.mod")
        val commandLine = configuration.createCommandLine(tempFile)
        
        assertNotNull("workDirectory nie powinien być null", commandLine.workDirectory)
    }

    fun testMissingDataFileValidation() {
        val configurationType = OplRunConfigurationType.getInstance()
        val factory = configurationType.configurationFactories[0]
        val configuration = factory.createTemplateConfiguration(project) as OplRunConfiguration
        
        val tempDir = createTempDir("opl_test")
        val modelFile = File(tempDir, "model.mod")
        modelFile.writeText("dvar int x;")
        
        configuration.modelFile = modelFile.absolutePath
        configuration.dataFile = File(tempDir, "nonexistent.dat").absolutePath
        configuration.cplexPath = "C:\\cplex\\bin\\oplrun.exe"
        
        try {
            configuration.checkConfiguration()
            fail("checkConfiguration() powinien rzucić wyjątek dla brakującego data file")
        } catch (e: Exception) {
            assertTrue("Wiadomość o błędzie powinna zawierać 'Data file'", 
                e.message?.contains("Data file") == true)
        } finally {
            modelFile.delete()
            tempDir.delete()
        }
    }

    private fun createTempDir(prefix: String): File {
        val tempDir = File.createTempFile(prefix, "")
        tempDir.delete()
        tempDir.mkdir()
        return tempDir
    }
}
