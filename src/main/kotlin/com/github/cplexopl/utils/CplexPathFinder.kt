package com.github.cplexopl.utils // Zmień na swój aktualny pakiet

import com.intellij.openapi.util.SystemInfo
import java.io.File

object CplexPathFinder {

    /**
     * Skanuje domyślne ścieżki instalacyjne w poszukiwaniu silnika CPLEX.
     * Zwraca bezwzględną ścieżkę do oplrun.exe lub null, jeśli nie znaleziono.
     */
    fun findDefaultOplrunPath(): String? {
        val baseDirs = mutableListOf<String>()
        val relativeBinPath: String
        val executableName: String

        // 1. Ustalenie strategii wyszukiwania na podstawie systemu operacyjnego
        when {
            SystemInfo.isWindows -> {
                baseDirs.add("C:\\Program Files\\IBM\\ILOG")
                relativeBinPath = "opl\\bin\\x64_win64"
                executableName = "oplrun.exe"
            }
            SystemInfo.isMac -> {
                baseDirs.add("/Applications/IBM/ILOG")
                baseDirs.add("/Applications") // Niektórzy instalują bezpośrednio tutaj
                relativeBinPath = "opl/bin/x86-64_osx"
                executableName = "oplrun"
            }
            else -> { // Systemy z rodziny Linux
                baseDirs.add("/opt/ibm/ILOG")
                baseDirs.add("/opt")
                relativeBinPath = "opl/bin/x86-64_linux"
                executableName = "oplrun"
            }
        }

        // 2. Przeszukiwanie zdefiniowanych katalogów bazowych
        for (baseDirPath in baseDirs) {
            val baseDir = File(baseDirPath)
            if (!baseDir.exists() || !baseDir.isDirectory) continue

            // 3. Pobranie wszystkich folderów, których nazwa zaczyna się od "CPLEX_Studio"
            val studioDirs = baseDir.listFiles { file ->
                file.isDirectory && file.name.startsWith("CPLEX_Studio")
            } ?: continue

            // 4. Sortowanie malejące - jeśli użytkownik ma zainstalowane kilka wersji,
            //    chcemy użyć najnowszej (np. 2212 zostanie sprawdzone przed 1210)
            val sortedDirs = studioDirs.sortedByDescending { it.name }

            // 5. Sprawdzenie, czy wewnątrz folderu wersji fizycznie istnieje plik wykonywalny
            for (studioDir in sortedDirs) {
                val oplrunFile = File(studioDir, "$relativeBinPath${File.separator}$executableName")
                if (oplrunFile.exists() && oplrunFile.canExecute()) {
                    return oplrunFile.absolutePath
                }
            }
        }

        return null // Zwraca null, jeśli heurystyka zawiodła
    }
}