# CPLEX-Plugin

Profesjonalna wtyczka do obsługi języka IBM ILOG CPLEX OPL w środowisku IntelliJ IDEA. 
Zapewnia kolorowanie składni, inteligentne podpowiedzi oraz integrację z silnikiem solvera.

[🇬🇧 English](#english) | [🇵🇱 Polski](#polski)

<p>Provides native support for IBM ILOG CPLEX Optimization Programming Language (OPL) in JetBrains IDEs.</p>
<ul>
  <li>Syntax highlighting for .mod and .dat files</li>
  <li>Run configuration for local oplrun solver</li>
  <li>Basic file type recognition and icons</li>
</ul>
---

<h2 id="english">🇬🇧 English</h2>

Plugin adding native support for IBM ILOG CPLEX Optimization Programming Language (OPL) in JetBrains environments (IntelliJ IDEA, PyCharm, etc.).

### Features
* **Syntax Highlighting:** Keyword, model structure, and operator highlighting for `.mod` and `.dat` files.
* **Compiler Integration:** Built-in run configuration (`Run Opl Model`), allowing direct execution of the local `oplrun` solver from the editor.

### Installation (Manual Install)
Before the plugin reaches the official JetBrains Marketplace, you can install it manually:
1. Go to the [Releases](../../releases) tab on GitHub and download the latest `.zip` package.
2. Open your IDE (e.g., IntelliJ IDEA, PyCharm).
3. Navigate to <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd>.
4. Click the gear icon (⚙️) at the top and select <kbd>Install plugin from disk...</kbd>.
5. Select the downloaded `.zip` file and restart the IDE.

### Requirements
A local installation of **IBM ILOG CPLEX Studio** is required for the compilation (Run Configuration) to work. The editor will prompt you to provide the path to the `oplrun` executable (or `oplrun.exe` on Windows).

---

<h2 id="polski">🇵🇱 Polski</h2>

Wtyczka dodająca natywne wsparcie dla języka IBM ILOG CPLEX Optimization Programming Language (OPL) w środowiskach JetBrains (IntelliJ IDEA, PyCharm, itp.).

### Funkcje
* **Kolorowanie składni:** Podświetlanie słów kluczowych, struktury modelu oraz operatorów dla plików `.mod` i `.dat`.
* **Integracja z kompilatorem:** Wbudowana konfiguracja uruchomieniowa (`Run Opl Model`), pozwalająca na bezpośrednie wywoływanie lokalnego solvera `oplrun` z poziomu edytora.

### Instalacja (Instalacja Ręczna)
Zanim wtyczka trafi do oficjalnego sklepu JetBrains Marketplace, możesz ją zainstalować ręcznie:
1. Przejdź do zakładki [Releases](../../releases) na GitHubie i pobierz najnowszą paczkę `.zip`.
2. Otwórz swoje środowisko (np. IntelliJ IDEA, PyCharm).
3. Wejdź w <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd>.
4. Kliknij ikonę zębatki (⚙️) na górze i wybierz <kbd>Install plugin from disk...</kbd>.
5. Wskaż pobrany plik `.zip` i zrestartuj IDE.

### Wymagania
Do poprawnego działania kompilacji (Run Configuration) wymagana jest lokalna instalacja **IBM ILOG CPLEX Studio**. Edytor poprosi o wskazanie ścieżki do pliku wykonywalnego `oplrun` (lub `oplrun.exe` na systemach Windows).