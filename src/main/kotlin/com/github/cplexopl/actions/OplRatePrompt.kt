package com.github.cplexopl.actions

import com.github.cplexopl.OplBundle
import com.github.cplexopl.settings.OplSettingsState
import com.intellij.ide.BrowserUtil
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

object OplRatePrompt {
    fun showNotification(project: Project) {
        val settings = OplSettingsState.instance
        val group = NotificationGroupManager.getInstance().getNotificationGroup("CPLEX OPL")
        val notification = group.createNotification(
            OplBundle.message("rate.prompt.title"),
            OplBundle.message("rate.prompt.message", settings.successfulRunCount),
            NotificationType.INFORMATION
        )

        notification.addAction(NotificationAction.createSimple(OplBundle.message("rate.prompt.action.rate")) {
            BrowserUtil.browse("https://plugins.jetbrains.com/plugin/31125-cplex-opl/reviews")
            settings.neverShowRatePrompt = true
            notification.expire()
        })

        notification.addAction(NotificationAction.createSimple(OplBundle.message("rate.prompt.action.feedback")) {
            BrowserUtil.browse("https://github.com/JAANULO/CPLEX-Plugin/issues")
            settings.neverShowRatePrompt = true
            notification.expire()
        })

        notification.addAction(NotificationAction.createSimple(OplBundle.message("rate.prompt.action.remindLater")) {
            settings.nextRatePromptRunCount = settings.successfulRunCount + 10
            notification.expire()
        })

        notification.notify(project)
    }
}
