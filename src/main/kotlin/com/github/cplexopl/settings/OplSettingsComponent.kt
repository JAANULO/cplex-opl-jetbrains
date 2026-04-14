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
            "Wybierz plik wykonywalny CPLEX (oplrun)",
            "Wskaż ścieżkę do pliku oplrun.",
            null,
            FileChooserDescriptorFactory.createSingleFileOrExecutableAppDescriptor()
        )

        panel = FormBuilder.createFormBuilder()
            .addLabeledComponent("Ścieżka do oplrun:", cplexPathField, 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    var cplexPath: String
        get() = cplexPathField.text
        set(value) { cplexPathField.text = value }
}