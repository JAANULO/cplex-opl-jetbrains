package com.github.cplexopl.console

import com.intellij.execution.filters.Filter
import com.intellij.execution.filters.OpenFileHyperlinkInfo
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import java.util.regex.Pattern

class OplLinkFilter(private val project: Project) : Filter {
    // Wzorzec wyłapujący ścieżkę pliku i linię, np. "C:\test.mod:15" lub "test.mod:15"
    private val pattern = Pattern.compile("(?<path>[a-zA-Z]:[/\\\\][^:]+\\.mod|[^:]+\\.mod):(?<line>\\d+)")

    override fun applyFilter(line: String, entireLength: Int): Filter.Result? {
        val matcher = pattern.matcher(line)
        if (matcher.find()) {
            val filePath = matcher.group("path")
            val lineNumber = matcher.group("line").toInt() - 1 // IntelliJ liczy od 0

            val normalizedPath = filePath.replace('\\', '/')
            val virtualFile = LocalFileSystem.getInstance().findFileByPath(normalizedPath)
                ?: project.basePath?.let { basePath ->
                    val fullPath = "$basePath/$normalizedPath".replace('\\', '/')
                    LocalFileSystem.getInstance().findFileByPath(fullPath)
                }

            if (virtualFile != null) {
                val info = OpenFileHyperlinkInfo(project, virtualFile, lineNumber)
                val start = entireLength - line.length + matcher.start()
                val end = entireLength - line.length + matcher.end()
                return Filter.Result(start, end, info)
            }
        }
        return null
    }
}