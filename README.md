# CPLEX OPL Support for JetBrains IDEs

[![JetBrains Plugin](https://img.shields.io/badge/JetBrains%20Marketplace-CPLEX%20OPL-blue?logo=jetbrains)](https://plugins.jetbrains.com/plugin/31125-cplex-opl)
[![Platform Version](https://img.shields.io/badge/Platform-2024.3%20%7C%202025.x-green.svg)](https://plugins.jetbrains.com/docs/intellij/build-number-ranges.html)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1-7F52FF.svg?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![License](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](LICENSE)
[![Examples](https://img.shields.io/badge/Examples-cplex--opl--examples-orange)](https://github.com/JAANULO/cplex-opl-examples)

Native language support and solver integration for **IBM ILOG CPLEX Optimization Programming Language (OPL)** in IntelliJ IDEA, PyCharm, CLion, DataSpell, and other JetBrains IDEs.

A modern, fast, and feature-rich alternative to the legacy Eclipse-based CPLEX Studio IDE.

[🇬🇧 English Version](#english) | [🇵🇱 Wersja Polska](#polski)

---

<h2 id="english">🇬🇧 English</h2>

### 💡 Why CPLEX OPL for JetBrains?

| Feature | Legacy CPLEX Studio (Eclipse) | JetBrains CPLEX OPL Plugin |
| :--- | :---: | :---: |
| **Modern Editor & Keymaps** | ❌ Outdated UI | ✅ IntelliJ/PyCharm modern UX |
| **Infeasibility Diagnostics** | ⚠️ Plain text logs | ✅ **Clickable links directly to conflicting constraints** |
| **Constraint Programming (CP)** | ⚠️ Basic syntax | ✅ **Autocomplete for 30+ CP & Scheduling functions** |
| **Execution Environments** | ⚠️ Local only | ✅ **Local, WSL (Linux), and Docker containers** |
| **Solver Path Discovery** | ❌ Manual setup | ✅ **One-click Auto-Detect button** |
| **Python Interoperability** | ❌ None | ✅ **Generate `doopl`/`docplex` Python runners** |
| **Version Control & AI** | ❌ Limited | ✅ **Full Git, GitHub & JetBrains AI integration** |

---

### 🚀 Key Features

* **File Types & Starter Templates:** Dedicated SVG icons and templates for `.mod` (models), `.dat` (data), and `.ops` (settings).
* **Syntax Highlighting & Completion:** Full keywords highlighting, built-in math, constraint programming (`span`, `alternative`, `synchronize`, `forbidStart`), and IBM ILOG Scripting (`thisOplModel`, `writeln`).
* **Run Integration (`Shift+F10`):** Native execution supporting **Local**, **WSL**, and **Docker** with automatic path translation.
* **Auto-Pairing:** Automatically pairs `.dat` and `.ops` files with matching names in the same folder.
* **Smart Console Navigation:** Clickable solver error links and real-time conflict refiner hints for infeasible models.
* **Inspections & Quick-Fixes (`Alt+Enter`):** Scope-aware variable validation, missing semicolons, MIP non-linearity warnings (`min`/`max`), and auto-insertion of `using CP;`.
* **Structure View:** Hierarchical side tree displaying decision variables (`dvar`), objectives (`minimize`/`maximize`), and labeled constraints.
* **Productivity Tools:** Live templates (`model`, `interval`, `span`, `knap`), block commenting (`Ctrl+/`), smart code formatter, and rename refactoring (`Shift+F6`).

---

### ⚡ Quick Start

1. **Install from Marketplace:**
   `Settings/Preferences` &rarr; `Plugins` &rarr; `Marketplace` &rarr; search **"CPLEX OPL"** &rarr; click **Install**.
2. **Configure Solver Path:**
   Go to `Settings/Preferences` &rarr; `Tools` &rarr; `CPLEX OPL` &rarr; click **Auto-Detect** (or provide path to `oplrun`).
3. **Create and Run a Model:**
   Right-click project tree &rarr; `New` &rarr; `OPL File` &rarr; Press <kbd>Shift+F10</kbd> to run!

---

### ⌨️ Useful Shortcuts

| Shortcut | Action |
| :--- | :--- |
| <kbd>Shift</kbd> + <kbd>F10</kbd> | Run current OPL Model |
| <kbd>Alt</kbd> + <kbd>Enter</kbd> | Show Context Actions / Quick-Fixes (e.g. add `using CP;`) |
| <kbd>Ctrl</kbd> + <kbd>B</kbd> / <kbd>Ctrl</kbd> + Click | Go to Variable / Declaration Definition |
| <kbd>Shift</kbd> + <kbd>F6</kbd> | Rename Refactoring across project |
| <kbd>Ctrl</kbd> + <kbd>/</kbd> | Toggle Line / Block Comment |
| `model` + <kbd>Tab</kbd> | Insert full OPL model skeleton |
| `interval` + <kbd>Tab</kbd> | Insert Constraint Programming interval variable |
| `knap` + <kbd>Tab</kbd> | Insert runnable Knapsack optimization model |

---

### ❓ FAQ & Troubleshooting

<details>
<summary><b>How does Auto-Detect find my CPLEX installation?</b></summary>
Auto-detect scans standard installation paths (e.g., <code>C:\Program Files\IBM\ILOG\CPLEX_Studio*\opl\bin\x64_win64\oplrun.exe</code> on Windows, <code>/opt/ibm/ILOG/CPLEX_Studio*/opl/bin/x86-64_linux/oplrun</code> on Linux/WSL, and macOS applications folder).
</details>

<details>
<summary><b>How to run models in WSL or Docker?</b></summary>
In your Run Configuration (<code>Run</code> &rarr; <code>Edit Configurations...</code>), change <b>Execution Mode</b> to <i>WSL</i> or <i>Docker</i>. The plugin automatically converts Windows paths to Unix paths (e.g., <code>/mnt/c/...</code>) before launching <code>oplrun</code>.
</details>

---

<h2 id="polski">🇵🇱 Polski</h2>

Wtyczka dodająca natywne wsparcie dla języka **IBM ILOG CPLEX Optimization Programming Language (OPL)** w środowiskach JetBrains (IntelliJ IDEA, PyCharm, CLion, DataSpell i pokrewnych).

Nowoczesna, szybka i lekka alternatywa dla przestarzałego środowiska IBM CPLEX Studio opartego na Eclipse.

---

### 💡 Dlaczego warto wybrać wtyczkę CPLEX OPL dla JetBrains?

| Funkcjonalność | Klasyczne CPLEX Studio (Eclipse) | Wtyczka JetBrains CPLEX OPL |
| :--- | :---: | :---: |
| **Nowoczesny edytor** | ❌ Przestarzały interfejs | ✅ Nowoczesne środowisko IntelliJ/PyCharm |
| **Nawigacja błędów sprzeczności** | ⚠️ Surowy tekst w logach | ✅ **Klikalne linki prosto do sprzecznych ograniczeń** |
| **Constraint Programming (CP)** | ⚠️ Podstawowe kolorowanie | ✅ **Autouzupełnianie ponad 30 funkcji CP i harmonogramowania** |
| **Środowiska wykonawcze** | ⚠️ Tylko lokalnie | ✅ **Lokalnie, WSL (Linux) oraz kontenery Docker** |
| **Wykrywanie ścieżki solvera** | ❌ Ręczna konfiguracja | ✅ **Przycisk automatycznej detekcji (Auto-Detect)** |
| **Współpraca z Pythonem** | ❌ Brak | ✅ **Generowanie runnerów `doopl`/`docplex` dla PyCharm** |
| **Kontrola wersji i AI** | ❌ Ograniczona | ✅ **Pełna integracja z Git, GitHub i JetBrains AI** |

---

### 🚀 Główne Możliwości

* **Typy plików i szablony startowe:** Dedykowane ikony SVG oraz szablony startowe dla plików `.mod` (modele), `.dat` (dane) oraz `.ops` (ustawienia).
* **Kolorowanie składni i autouzupełnianie:** Pełne wsparcie dla słów kluczowych OPL, funkcji matematycznych, ograniczeń Constraint Programming (`span`, `alternative`, `synchronize`, `forbidStart`) oraz obiektów ILOG Script (`thisOplModel`, `writeln`).
* **Integracja uruchamiania (`Shift+F10`):** Wygodna konfiguracja *Run Opl Model* z obsługą środowiska **Lokalnego**, **WSL** oraz **Docker** z automatyczną translacją ścieżek.
* **Auto-parowanie plików:** Automatyczne powiązanie plików `.dat` i `.ops` o odpowiadających nazwach.
* **Inteligentna nawigacja błędów:** Bezpośrednie linki w konsoli do niespełnionych ograniczeń i podpowiedzi przy modelach sprzecznych (*infeasible*).
* **Inspekcje i Quick-Fixy (`Alt+Enter`):** Walidacja zasięgów zmiennych, ostrzeżenia o nieliniowości MIP (`min`/`max`) oraz automatyczne wstawianie `using CP;`.
* **Widok struktury (Structure View):** Boczne drzewo elementów modelu (zmienne decyzyjne `dvar`, cele `maximize`/`minimize`, sekcje ograniczeń).
* **Narzędzia programistyczne:** Szablony Live Templates (`model`, `interval`, `span`, `knap`), komentowanie (`Ctrl+/`), inteligentny formatter kodu i zmiana nazwy we wszystkich plikach (`Shift+F6`).

---

### ⚡ Szybki Start

1. **Instalacja z Marketplace:**
   `Settings/Preferences` &rarr; `Plugins` &rarr; `Marketplace` &rarr; wpisz **"CPLEX OPL"** &rarr; kliknij **Install**.
2. **Wskazanie ścieżki CPLEX:**
   Przejdź do `Settings/Preferences` &rarr; `Tools` &rarr; `CPLEX OPL` &rarr; kliknij **Auto-Detect** (lub podaj ścieżkę do `oplrun`).
3. **Uruchomienie modelu:**
   Kliknij prawym przyciskiem myszy na plik `.mod` i wciśnij <kbd>Shift+F10</kbd>.

---

### 🤝 Społeczność i Zgłaszanie Uwagg

Znalazłeś błąd lub masz pomysł na nową funkcję?
- [Zgłoś problem na GitHub Issues](https://github.com/JAANULO/CPLEX-Plugin/issues)
- [Baza przykładowych modeli OPL](https://github.com/JAANULO/cplex-opl-examples)
- [Strona ocen w JetBrains Marketplace](https://plugins.jetbrains.com/plugin/31125-cplex-opl/reviews)
