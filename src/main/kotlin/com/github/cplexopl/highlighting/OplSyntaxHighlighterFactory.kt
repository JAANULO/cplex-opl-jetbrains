package com.github.cplexopl.highlighting

import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

// Factory Pattern = wzorzec projektowy, gdzie jedna klasa tworzy instancje innych klas
// IntelliJ wymaga Fabryki zamiast bezpośredniego rejestrowania SyntaxHighlightera
class OplSyntaxHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
        return OplSyntaxHighlighter()
    }
}
