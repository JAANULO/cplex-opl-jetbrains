package com.github.cplexopl.settings

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class OplSettingsConfigurable : Configurable {
    private var component: OplSettingsComponent? = null

    // Nazwa zakładki w panelu Settings
    override fun getDisplayName(): String = "CPLEX OPL"

    override fun createComponent(): JComponent {
        component = OplSettingsComponent()
        return component!!.panel
    }

    // Sprawdza, czy wpisana ścieżka różni się od tej zapisanej w XML
    override fun isModified(): Boolean {
        val state = OplSettingsState.instance
        return component?.cplexPath != state.savedCplexPath
    }

    // Wykonywane po kliknięciu "Apply" lub "OK"
    override fun apply() {
        val state = OplSettingsState.instance
        state.savedCplexPath = component?.cplexPath ?: ""
    }

    // Wczytuje dane z XML przy otwieraniu okna Settings
    override fun reset() {
        val state = OplSettingsState.instance
        component?.cplexPath = state.savedCplexPath
    }

    // Zwalnia pamięć po zamknięciu okna
    override fun disposeUIResources() {
        component = null
    }
}