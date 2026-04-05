package com.github.cplexopl.run

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

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

    // Budujemy panel formularza
    private val panel: JPanel = FormBuilder.createFormBuilder()
        .addLabeledComponent("Model file (.mod):", modelFileField)
        .addLabeledComponent("Data file (.dat):", dataFileField)
        .addLabeledComponent("Oplrun path:", cplexPathField)
        .addComponentFillVertically(JPanel(), 0)
        .panel

    override fun resetEditorFrom(config: OplRunConfiguration) {
        // Wczytaj wartości z konfiguracji do pól UI
        modelFileField.text = config.modelFile
        dataFileField.text = config.dataFile
        cplexPathField.text = config.cplexPath
    }

    override fun applyEditorTo(config: OplRunConfiguration) {
        // Zapisz wartości z pól UI do konfiguracji
        config.modelFile = modelFileField.text
        config.dataFile = dataFileField.text
        config.cplexPath = cplexPathField.text
    }

    override fun createEditor(): JComponent = panel
}
