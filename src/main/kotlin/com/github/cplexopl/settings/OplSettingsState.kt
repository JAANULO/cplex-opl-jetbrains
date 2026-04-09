package com.github.cplexopl.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(
    name = "OplSettings",
    storages = [Storage("OplPluginSettings.xml")]
)
class OplSettingsState : PersistentStateComponent<OplSettingsState> {

    // Zmienna przechowująca ścieżkę do oplrun.exe na dysku użytkownika
    var savedCplexPath: String = ""

    override fun getState(): OplSettingsState = this

    override fun loadState(state: OplSettingsState) {
        this.savedCplexPath = state.savedCplexPath
    }

    companion object {
        val instance: OplSettingsState
            get() = ApplicationManager.getApplication().getService(OplSettingsState::class.java)
    }
}