package com.github.cplexopl.actions

import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.github.cplexopl.OplFileType
import com.github.cplexopl.OplDatFileType

class OplCreateFileAction : CreateFileFromTemplateAction("OPL File", "Creates a new OPL file", OplFileType.icon) {
    override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
        builder.setTitle("New OPL File")
            .addKind("Model file (.mod)", OplFileType.icon, "OplModel")
            .addKind("Data file (.dat)", OplDatFileType.icon, "OplData")
    }

    override fun getActionName(directory: PsiDirectory, newName: String, templateName: String): String {
        return "Create OPL File: $newName"
    }
}