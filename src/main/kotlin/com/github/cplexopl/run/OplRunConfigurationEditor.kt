package com.github.cplexopl.run

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.ui.Messages
import com.intellij.util.ui.FormBuilder
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel
import java.awt.BorderLayout
import com.github.cplexopl.utils.CplexPathFinder
import com.github.cplexopl.settings.OplSettingsState

// SettingsEditor = panel UI wyświetlany w oknie "Edit Run Configuration"
// FormBuilder = pomocnik IntelliJ do budowania formularzy (etykieta + pole)
class OplRunConfigurationEditor(private val project: Project) : SettingsEditor<OplRunConfiguration>() {

    // Pola formularza - TextFieldWithBrowseButton = pole tekstowe z przyciskiem "..." do wyboru pliku
    private val modelFileField = TextFieldWithBrowseButton().apply {
        addBrowseFolderListener(
            project,
            FileChooserDescriptorFactory.createSingleFileDescriptor("mod")
                .withTitle("Select OPL Model File")
                .withDescription("Select the .mod file to run")
        )
    }

    private val dataFileField = TextFieldWithBrowseButton().apply {
        addBrowseFolderListener(
            project,
            FileChooserDescriptorFactory.createSingleFileDescriptor("dat")
                .withTitle("Select OPL Data File")
                .withDescription("Select the .dat data file (optional)")
        )
    }

    private val cplexPathField = TextFieldWithBrowseButton().apply {
        addBrowseFolderListener(
            project,
            FileChooserDescriptorFactory.createSingleFileOrExecutableAppDescriptor()
                .withTitle("Select Oplrun Executable")
                .withDescription("Select the oplrun binary from your CPLEX installation")
        )
    }

    private val autoDetectButton = JButton("Auto-Detect").apply {
        addActionListener {
            val foundPath = CplexPathFinder.findDefaultOplrunPath()
            if (foundPath != null) {
                cplexPathField.text = foundPath
                OplSettingsState.instance.savedCplexPath = foundPath // Zapis globalny!
            } else {
                Messages.showWarningDialog(
                    "Could not automatically find the oplrun executable in default locations.\nPlease select the path manually.",
                    "Auto-Detect Failed"
                )
            }
        }
    }

    private val pathPanel = JPanel(BorderLayout()).apply {
        add(cplexPathField, BorderLayout.CENTER)
        add(autoDetectButton, BorderLayout.EAST)
    }

    // Budujemy panel formularza
    private val panel: JPanel = FormBuilder.createFormBuilder()
        .addLabeledComponent("Model file (.mod):", modelFileField)
        .addLabeledComponent("Data file (.dat):", dataFileField)
        .addLabeledComponent("Oplrun path:", pathPanel)
        .addComponentFillVertically(JPanel(), 0)
        .panel

    override fun resetEditorFrom(config: OplRunConfiguration) {
        // Wczytaj wartości z konfiguracji do pól UI
        modelFileField.text = config.modelFile
        dataFileField.text = config.dataFile

        // Jeśli lokalna konfiguracja nie ma ścieżki (nowy profil), ładujemy zapisaną globalnie
        if (config.cplexPath.isEmpty() && OplSettingsState.instance.savedCplexPath.isNotEmpty()) {
            cplexPathField.text = OplSettingsState.instance.savedCplexPath
        } else {
            cplexPathField.text = config.cplexPath
        }
    }

    override fun applyEditorTo(config: OplRunConfiguration) {
        // Zapisz wartości z pól UI do konfiguracji
        config.modelFile = modelFileField.text
        config.dataFile = dataFileField.text
        config.cplexPath = cplexPathField.text

        // Przy zatwierdzeniu formularza "Apply", aktualizujemy też ścieżkę globalną
        if (cplexPathField.text.isNotEmpty()) {
            OplSettingsState.instance.savedCplexPath = cplexPathField.text
        }
    }

    override fun createEditor(): JComponent = panel
}
