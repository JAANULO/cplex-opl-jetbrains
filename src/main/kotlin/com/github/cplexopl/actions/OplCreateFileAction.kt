package com.github.cplexopl.actions

import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.github.cplexopl.OplFileType
import com.github.cplexopl.OplDatFileType
import com.github.cplexopl.OplOpsFileType

import com.intellij.openapi.actionSystem.ActionUpdateThread

import com.github.cplexopl.statistics.OplUsageCollector
import com.intellij.psi.PsiFile

class OplCreateFileAction : CreateFileFromTemplateAction("OPL File", "Creates a new OPL file", OplFileType.icon) {
    override fun getActionUpdateThread() = ActionUpdateThread.BGT
    override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
        builder.setTitle("New OPL File")
            .addKind("Model file (.mod)", OplFileType.icon, "OplModel")
            .addKind("Data file (.dat)", OplDatFileType.icon, "OplData")
            .addKind("Settings file (.ops)", OplOpsFileType.icon, "OplSettings")
    }

    override fun createFile(name: String, templateName: String, dir: PsiDirectory): PsiFile? {
        val file = super.createFile(name, templateName, dir)
        if (file != null) {
            when (templateName) {
                "OplModel" -> OplUsageCollector.logFileCreated(OplUsageCollector.FileTypeEnum.MOD)
                "OplData" -> OplUsageCollector.logFileCreated(OplUsageCollector.FileTypeEnum.DAT)
                "OplSettings" -> OplUsageCollector.logFileCreated(OplUsageCollector.FileTypeEnum.OPS)
            }
        }
        return file
    }

    override fun getActionName(directory: PsiDirectory, newName: String, templateName: String): String {
        return "Create OPL File: $newName"
    }
}