package com.github.cplexopl.highlighting

import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

// Factory Pattern = design pattern where one class creates instances of other classes
// IntelliJ requires Factory instead of direct registration of SyntaxHighlighter
class OplSyntaxHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
        return OplSyntaxHighlighter()
    }
}
