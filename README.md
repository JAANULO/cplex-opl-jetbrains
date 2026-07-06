# CPLEX-Plugin

Profesjonalna wtyczka do obsługi języka IBM ILOG CPLEX OPL w środowisku IntelliJ IDEA. 
Zapewnia kolorowanie składni, inteligentne podpowiedzi oraz integrację z silnikiem solvera.

[🇬🇧 English](#english) | [🇵🇱 Polski](#polski)

<p>Provides native support for IBM ILOG CPLEX Optimization Programming Language (OPL) in JetBrains IDEs.</p>
<ul>
  <li>Syntax highlighting and code completion for OPL</li>
  <li>Run configuration for local oplrun solver with .mod/.dat auto-pairing</li>
  <li>Clickable solver errors in console, structure view, templates, and formatter</li>
</ul>
---

<h2 id="english">🇬🇧 English</h2>

Plugin adding native support for IBM ILOG CPLEX Optimization Programming Language (OPL) in JetBrains environments (IntelliJ IDEA, PyCharm, etc.).

### Features
* **Syntax Highlighting:** Keyword, model structure, and operator highlighting for `.mod` files.
* **Code Completion:** Basic keyword and built-in function completion.
* **Run Integration:** Built-in run configuration (`Run Opl Model`) for direct `oplrun` execution from the editor.
* **Auto-Pairing:** Automatic `.dat` pairing when a matching file name exists.
* **Path Support:** Manual `oplrun` path setup plus auto-detect button for default install locations.
* **Console Navigation:** Clickable error links (for `.mod`) in run console.
* **Structure View:** Side panel listing declarations, objective, and constraints.
* **Editor Utilities:** Live templates, commenter (`Ctrl+/`), brace matcher, and basic code formatter.
* **Refactoring:** Rename refactoring (`Shift+F6`) and Find Usages support.

### Installation (Manual Install)
Before the plugin reaches the official JetBrains Marketplace, you can install it manually:
1. Go to the [Releases](../../releases) tab on GitHub and download the latest `.zip` package.
2. Open your IDE (e.g., IntelliJ IDEA, PyCharm).
3. Navigate to <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd>.
4. Click the gear icon (⚙️) at the top and select <kbd>Install plugin from disk...</kbd>.
5. Select the downloaded `.zip` file and restart the IDE.

### Requirements
A local installation of **IBM ILOG CPLEX Studio** is required for Run Configuration execution. You can set `oplrun` manually in Run Configuration or use auto-detect from common install paths.

---

<h2 id="polski">🇵🇱 Polski</h2>

Wtyczka dodająca natywne wsparcie dla języka IBM ILOG CPLEX Optimization Programming Language (OPL) w środowiskach JetBrains (IntelliJ IDEA, PyCharm, itp.).

### Funkcje
* **Kolorowanie składni:** Podświetlanie słów kluczowych, struktury modelu oraz operatorów dla plików `.mod`.
* **Code Completion:** Podstawowe autouzupełnianie słów kluczowych i funkcji wbudowanych.
* **Integracja uruchamiania:** Wbudowana konfiguracja `Run Opl Model`, wywołująca lokalny `oplrun` bezpośrednio z IDE.
* **Auto-parowanie plików:** Automatyczne podpinanie `.dat`, gdy istnieje plik o tej samej nazwie.
* **Obsługa ścieżki:** Ręczne wskazanie `oplrun` oraz przycisk auto-detekcji domyślnych lokalizacji.
* **Nawigacja błędów:** Klikalne linki błędów solvera (dla `.mod`) w konsoli uruchomienia.
* **Structure View:** Boczne drzewo elementów modelu (deklaracje, cel, ograniczenia).
* **Narzędzia edytora:** Live templates, komentowanie (`Ctrl+/`), pary nawiasów i podstawowy formatter.
* **Refaktoryzacja:** Zmiana nazwy (`Shift+F6`) we wszystkich plikach projektu oraz wyszukiwanie użyć (Find Usages).

### Instalacja (Instalacja Ręczna)
Zanim wtyczka trafi do oficjalnego sklepu JetBrains Marketplace, możesz ją zainstalować ręcznie:
1. Przejdź do zakładki [Releases](../../releases) na GitHubie i pobierz najnowszą paczkę `.zip`.
2. Otwórz swoje środowisko (np. IntelliJ IDEA, PyCharm).
3. Wejdź w <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd>.
4. Kliknij ikonę zębatki (⚙️) na górze i wybierz <kbd>Install plugin from disk...</kbd>.
5. Wskaż pobrany plik `.zip` i zrestartuj IDE.

### Wymagania
Do działania Run Configuration wymagana jest lokalna instalacja **IBM ILOG CPLEX Studio**. Ścieżkę do `oplrun` (lub `oplrun.exe` na Windows) można wskazać ręcznie albo wykryć automatycznie dla typowych lokalizacji.
