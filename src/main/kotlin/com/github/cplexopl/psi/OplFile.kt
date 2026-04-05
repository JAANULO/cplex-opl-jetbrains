package com.github.cplexopl.psi

import com.github.cplexopl.OplFileType
import com.github.cplexopl.OplLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class OplFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, OplLanguage) {
    override fun getFileType(): FileType = OplFileType
    override fun toString(): String = "OPL File"
}