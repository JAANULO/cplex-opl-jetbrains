package com.github.cplexopl.settings

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.util.ui.FormBuilder
import javax.swing.JPanel

class OplSettingsComponent {
    val panel: JPanel
    val cplexPathField = TextFieldWithBrowseButton()

    init {
        // Okno dialogowe do wyboru pliku wykonywalnego
        cplexPathField.addBrowseFolderListener(
            null,
            FileChooserDescriptorFactory.createSingleFileOrExecutableAppDescriptor().apply {
                title = "Select CPLEX Executable (oplrun)"
                description = "Specify the path to the oplrun executable."
            }
        )

        panel = FormBuilder.createFormBuilder()

            .addLabeledComponent("Path to oplrun:", cplexPathField, 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    var cplexPath: String
        get() = cplexPathField.text
        set(value) { cplexPathField.text = value }
}