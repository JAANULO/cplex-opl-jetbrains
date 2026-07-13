# Plan implementacji: Integracja Python + docplex (JetBrains)

## Cel
Dodanie komendy "Generate Python Runner" która na podstawie otwartego pliku `.mod`
generuje gotowy plik `.py` używający biblioteki `docplex` do uruchomienia modelu.

---

## Etap 1: Komenda "Generate Python Runner" (2-3 godziny) ⭐ Zacznij tutaj

### Krok 1: Utwórz plik `src/main/kotlin/com/github/cplexopl/actions/GeneratePythonRunnerAction.kt`

```kotlin
package com.github.cplexopl.actions

import com.github.cplexopl.OplFileType
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import java.io.File

class GeneratePythonRunnerAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        val dir = file.parent.path
        val baseName = file.nameWithoutExtension
        val pyFile = File("$dir/${baseName}_runner.py")

        // Sprawdź czy plik .dat istnieje obok .mod
        val datFile = File("$dir/$baseName.dat")
        val hasDat = datFile.exists()

        // Jeśli plik już istnieje - zapytaj o nadpisanie
        if (pyFile.exists()) {
            val result = Messages.showYesNoDialog(
                project,
                "Plik ${baseName}_runner.py już istnieje. Nadpisać?",
                "Generate Python Runner",
                Messages.getQuestionIcon()
            )
            if (result != Messages.YES) return
        }

        // Generuj zawartość pliku Python
        val content = generatePythonContent(baseName, hasDat)

        // Zapisz plik
        ApplicationManager.getApplication().runWriteAction {
            pyFile.writeText(content, Charsets.UTF_8)

            // Odśwież VFS żeby IntelliJ zobaczył nowy plik
            // VFS = Virtual File System - warstwa abstrakcji IntelliJ nad systemem plików
            LocalFileSystem.getInstance().refreshAndFindFileByPath(pyFile.absolutePath)?.let { vf ->
                FileEditorManager.getInstance(project).openFile(vf, true)
            }
        }

        // Pokaż powiadomienie z przyciskiem instalacji docplex
        NotificationGroupManager.getInstance()
            .getNotificationGroup("CPLEX OPL")
            .createNotification(
                "Wygenerowano ${baseName}_runner.py",
                "Zainstaluj docplex: <a href='pip'>pip install docplex</a>",
                NotificationType.INFORMATION
            )
            .notify(project)
    }

    // Akcja dostępna tylko dla plików .mod
    override fun update(e: AnActionEvent) {
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)
        e.presentation.isEnabledAndVisible = file?.fileType == OplFileType
    }

    private fun generatePythonContent(baseName: String, hasDat: Boolean): String {
        val datLine = if (hasDat) "DATA_FILE  = \"$baseName.dat\"" 
                      else "# DATA_FILE = \"$baseName.dat\"  # Odkomentuj jeśli używasz pliku .dat"
        val datArg  = if (hasDat) ", DATA_FILE" else ""

        return """
# Wygenerowano przez CPLEX OPL Plugin dla JetBrains
# Wymagania: pip install docplex
# Dokumentacja: https://ibmdecisionoptimization.github.io/docplex-doc/

import os
import subprocess

# === KONFIGURACJA ===
MODEL_FILE = "$baseName.mod"
$datLine

def run_with_oplrun():
    \"\"\"Uruchamia model przez oplrun (wymaga CPLEX Studio)\"\"\"
    cmd = ["oplrun", MODEL_FILE$datArg]
    result = subprocess.run(cmd, capture_output=True, text=True)
    print(result.stdout)
    if result.returncode != 0:
        print("BŁĄD:", result.stderr)

if __name__ == "__main__":
    run_with_oplrun()
""".trimIndent()
    }
}
```

---

### Krok 2: Zarejestruj akcję w `plugin.xml`

Dodaj w sekcji `<actions>`:

```xml
<action id="OPL.GeneratePythonRunner"
        class="com.github.cplexopl.actions.GeneratePythonRunnerAction"
        text="Generate Python Runner"
        description="Generate a Python docplex runner script for this OPL model">
    <!-- Dodaj do menu prawego przycisku myszy na pliku .mod -->
    <add-to-group group-id="ProjectViewPopupMenu" anchor="last"/>
    <!-- Dodaj do menu Tools -->
    <add-to-group group-id="ToolsMenu" anchor="last"/>
</action>
```

---

### Krok 3: Zarejestruj grupę powiadomień w `plugin.xml`

Dodaj w sekcji `<extensions>`:

```xml
<notificationGroup id="CPLEX OPL"
                   displayType="BALLOON"
                   isLogByDefault="true"/>
```

---

## Etap 2: Autouzupełnianie docplex w plikach `.py` (4-6 godzin)

### Co robi:
Gdy plik `.py` zawiera `from docplex` lub `import docplex` — dodaje podpowiedzi
metod CPLEX w autouzupełnianiu.

### Utwórz `src/main/kotlin/com/github/cplexopl/completion/DocplexCompletionContributor.kt`:

```kotlin
package com.github.cplexopl.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiFile
import com.intellij.util.ProcessingContext

class DocplexCompletionContributor : CompletionContributor() {

    init {
        // Aktywuj tylko w plikach Python z importem docplex
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(),
            DocplexCompletionProvider()
        )
    }
}

class DocplexCompletionProvider : CompletionProvider<CompletionParameters>() {

    // Metody docplex z opisami
    private val docplexMethods = listOf(
        "continuous_var"    to "mdl.continuous_var(lb=0, ub=1e30, name='x')",
        "integer_var"       to "mdl.integer_var(lb=0, ub=100, name='n')",
        "binary_var"        to "mdl.binary_var(name='b')",
        "minimize"          to "mdl.minimize(expr)",
        "maximize"          to "mdl.maximize(expr)",
        "add_constraint"    to "mdl.add_constraint(ct, name='ct_name')",
        "add_constraints"   to "mdl.add_constraints(cts)",
        "solve"             to "mdl.solve(log_output=True)",
        "print_solution"    to "mdl.print_solution()",
        "get_solve_status"  to "mdl.get_solve_status()",
        "sum"               to "mdl.sum(exprs)"
    )

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        // Sprawdź czy plik importuje docplex
        if (!hasDocplexImport(parameters.originalFile)) return

        docplexMethods.forEach { (method, signature) ->
            result.addElement(
                LookupElementBuilder.create(method)
                    .withTypeText("docplex")
                    .withTailText(" $signature", true)
                    .bold()
            )
        }
    }

    private fun hasDocplexImport(file: PsiFile): Boolean {
        val text = file.text
        return text.contains("from docplex") || text.contains("import docplex")
    }
}
```

### Zarejestruj w `plugin.xml`:

```xml
<!-- Wymaga pluginu Python - dodaj do <depends> -->
<depends optional="true" config-file="python-support.xml">com.intellij.modules.python</depends>
```

Utwórz plik `src/main/resources/META-INF/python-support.xml`:

```xml
<idea-plugin>
    <extensions defaultExtensionNs="com.intellij">
        <completion.contributor
                language="Python"
                implementationClass="com.github.cplexopl.completion.DocplexCompletionContributor"/>
    </extensions>
</idea-plugin>
```

---

## Etap 3: Walidacja spójności .mod ↔ .py (8-12 godzin, zaawansowane)

### Co robi:
Parsuje plik `.mod` (masz już PSI!) i sprawdza czy zmienne używane w `.py`
istnieją w modelu OPL. Podkreśla błędy w pliku Python.

### Przykład działania:
- W `model.mod`: `dvar float x;`
- W `model_runner.py`: `solution.get_value("y")` → ⚠️ zmienna `y` nie istnieje w modelu

### Implementacja — `src/main/kotlin/com/github/cplexopl/inspection/OplPythonConsistencyInspection.kt`:

```kotlin
package com.github.cplexopl.inspection

import com.intellij.codeInspection.*
import com.intellij.psi.PsiFile

// LocalInspectionTool = klasa sprawdzająca kod w jednym pliku
// Inspection = bardziej zaawansowana wersja Annotatora (ma Quick Fix, ustawienia)
class OplPythonConsistencyInspection : LocalInspectionTool() {

    override fun getDisplayName() = "OPL variable referenced in Python not defined in .mod file"
    override fun getGroupDisplayName() = "CPLEX OPL"
    override fun getShortName() = "OplPythonConsistency"

    override fun checkFile(file: PsiFile, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor> {
        // Sprawdź tylko pliki Python
        if (file.language.id != "Python") return ProblemDescriptor.EMPTY_ARRAY

        // Znajdź plik .mod obok pliku .py
        val modFile = findModFile(file) ?: return ProblemDescriptor.EMPTY_ARRAY

        // Wyciągnij zmienne z .mod (przez PSI)
        val declaredVars = extractDeclaredVariables(modFile)

        // Znajdź wywołania get_value("...") w .py i sprawdź czy zmienna istnieje
        val problems = mutableListOf<ProblemDescriptor>()
        // TODO: implementacja parsowania get_value() z PSI Pythona

        return problems.toTypedArray()
    }

    private fun findModFile(pyFile: PsiFile): PsiFile? {
        val baseName = pyFile.name.removeSuffix("_runner.py").removeSuffix(".py")
        return pyFile.containingDirectory?.findFile("$baseName.mod")
    }

    private fun extractDeclaredVariables(modFile: PsiFile): Set<String> {
        // Użyj istniejącego PSI żeby wyciągnąć nazwy dvar
        // Implementacja zależy od struktury Twojego PSI
        return emptySet() // TODO
    }
}
```

### Zarejestruj w `python-support.xml`:

```xml
<localInspection
    language="Python"
    displayName="OPL variable not defined in .mod file"
    groupName="CPLEX OPL"
    implementationClass="com.github.cplexopl.inspection.OplPythonConsistencyInspection"
    enabledByDefault="true"
    level="WARNING"/>
```

---

## Kolejność implementacji

1. **Etap 1** — Generate Python Runner — zacznij tu, 2-3h, największa wartość
2. **Etap 2** — Autocomplete docplex — 4-6h, wymaga PyCharm lub IntelliJ Ultimate
3. **Etap 3** — Walidacja spójności — zostaw na koniec, najtrudniejsze

---

## Ważna uwaga

Etapy 2 i 3 wymagają żeby użytkownik miał **PyCharm** lub **IntelliJ Ultimate**
(z pluginem Python). Dlatego rejestrujemy je jako `optional` — wtyczka działa
bez nich, a funkcje Python aktywują się automatycznie gdy IDE ma obsługę Pythona.

---

## Wymagania dla użytkownika końcowego

- CPLEX Studio (do uruchamiania przez `oplrun`)
- Python 3.8+ z `pip install docplex` (opcjonalnie)
- PyCharm lub IntelliJ Ultimate (dla Etapów 2 i 3)

---

## Linki

- Dokumentacja docplex: https://ibmdecisionoptimization.github.io/docplex-doc/
- Przykłady IBM: https://github.com/IBMDecisionOptimization/docplex-examples
- IntelliJ Platform SDK — Optional Dependencies: https://plugins.jetbrains.com/docs/intellij/plugin-dependencies.html
