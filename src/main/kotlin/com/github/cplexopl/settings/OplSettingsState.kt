package com.github.cplexopl.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service(Service.Level.APP)
@State(
    name = "OplSettings",
    storages = [Storage("OplPluginSettings.xml")]
)
class OplSettingsState : PersistentStateComponent<OplSettingsState> {

    // Variable storing path to oplrun.exe on user's disk
    var savedCplexPath: String = ""
    var successfulRunCount: Int = 0
    var nextRatePromptRunCount: Int = 5
    var neverShowRatePrompt: Boolean = false
    var hasShownWelcomeNotification: Boolean = false

    override fun getState(): OplSettingsState = this

    override fun loadState(state: OplSettingsState) {
        this.savedCplexPath = state.savedCplexPath
        this.successfulRunCount = state.successfulRunCount
        this.nextRatePromptRunCount = if (state.nextRatePromptRunCount > 0) state.nextRatePromptRunCount else 5
        this.neverShowRatePrompt = state.neverShowRatePrompt
        this.hasShownWelcomeNotification = state.hasShownWelcomeNotification
    }

    companion object {
        val instance: OplSettingsState
            get() = ApplicationManager.getApplication().getService(OplSettingsState::class.java)
    }
}