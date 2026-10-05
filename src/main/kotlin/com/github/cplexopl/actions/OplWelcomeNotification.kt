package com.github.cplexopl.actions

import com.github.cplexopl.OplBundle
import com.github.cplexopl.settings.OplSettingsConfigurable
import com.github.cplexopl.settings.OplSettingsState
import com.intellij.ide.BrowserUtil
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.Project

object OplWelcomeNotification {
    fun showIfFirstTime(project: Project) {
        val settings = OplSettingsState.instance
        if (settings.hasShownWelcomeNotification) return

        val group = NotificationGroupManager.getInstance().getNotificationGroup("CPLEX OPL")
        val notification = group.createNotification(
            OplBundle.message("welcome.prompt.title"),
            OplBundle.message("welcome.prompt.message"),
            NotificationType.INFORMATION
        )

        notification.addAction(NotificationAction.createSimple(OplBundle.message("welcome.prompt.action.configure")) {
            settings.hasShownWelcomeNotification = true
            notification.expire()
            ShowSettingsUtil.getInstance().showSettingsDialog(project, OplSettingsConfigurable::class.java)
        })

        notification.addAction(NotificationAction.createSimple(OplBundle.message("welcome.prompt.action.examples")) {
            settings.hasShownWelcomeNotification = true
            notification.expire()
            BrowserUtil.browse("https://github.com/JAANULO/cplex-opl-examples")
        })

        notification.addAction(NotificationAction.createSimple(OplBundle.message("welcome.prompt.action.dismiss")) {
            settings.hasShownWelcomeNotification = true
            notification.expire()
        })

        settings.hasShownWelcomeNotification = true
        notification.notify(project)
    }
}
