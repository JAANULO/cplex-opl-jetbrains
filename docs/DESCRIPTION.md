# CPLEX OPL Support for JetBrains IDEs

Native, high-performance language support and solver integration for **IBM ILOG CPLEX Optimization Programming Language (OPL)** in IntelliJ IDEA, PyCharm, CLion, DataSpell, and other JetBrains IDEs.

A modern, fast, and feature-rich alternative to the legacy Eclipse-based CPLEX Studio IDE.

---

### 🚀 Key Features & Highlights

- **Intelligent Infeasibility Diagnosis:** Automatically captures solver conflict logs (`ctInfeasible at ...`) and generates direct, clickable console hyperlinks to conflicting constraints in `.mod` and `.dat` files.
- **Universal Run Integration:** Dedicated *Run Opl Model* configuration supporting **Local**, **WSL (Windows Subsystem for Linux)**, and **Docker** containers with automatic path translation.
- **Solver Auto-Detection:** Automatically discovers `oplrun` binaries in standard installation paths across Windows, Linux, and macOS.
- **Constraint Programming (CP) & Scheduling:** Full syntax support, keyword highlighting, and intelligent code completion for over 30 CP Optimizer functions (e.g. `span`, `alternative`, `synchronize`, `forbidStart`, `stepAt`, `cumulFunction`, `stateFunction`).
- **Semantic Analysis & Inspections:** Real-time validation including scope-aware variable resolution, type checks, missing semicolon warnings, and automatic quick-fixes (e.g. `Alt+Enter` to insert missing `using CP;`).
- **Python & Data Science Interoperability:** Context-menu action to generate executable `doopl`/`docplex` Python runner scripts directly from `.mod` files in PyCharm.
- **Dedicated File Types & Icons:** Full support and dedicated SVG icons for models (`.mod`), data (`.dat`), and settings (`.ops`) files with starter templates.
- **Dynamic `.ops` Settings Engine:** Parses and injects XML configuration files (e.g. memory limits, tuning parameters) directly into the execution pipeline with XXE security protection.
- **Developer Experience:** Structure view side panel (variables, objectives, constraints), smart code formatter, live templates, brace matching, and rename refactoring (`Shift+F6`).

---

### ⚡ Quick Start in 60 Seconds

1. **Create an OPL file:** Right-click your project folder &rarr; `New` &rarr; `OPL File` (choose *Model file* or *Data file*).
2. **Configure Solver:** Open `Settings` &rarr; `Tools` &rarr; `CPLEX OPL` and click **Auto-Detect** (or enter the path to `oplrun`).
3. **Run your model:** Press <kbd>Shift+F10</kbd> or click the green run triangle next to your model.

---

### 📦 Supported Environments & Solvers

- **JetBrains IDEs:** IntelliJ IDEA (Community & Ultimate), PyCharm (Community & Professional), CLion, DataSpell, WebStorm, and other IntelliJ-based IDEs (2024.3+ and 2025.x).
- **CPLEX Studio Versions:** IBM ILOG CPLEX Optimization Studio 12.x, 20.x, and 22.x.
- **Execution Environments:** Local Host, WSL (Ubuntu/Debian), and Docker.

---

### 💬 Community & Feedback

Found a bug or have an idea for a new feature?
- [GitHub Repository & Issue Tracker](https://github.com/JAANULO/CPLEX-Plugin)
- [Reference Models & Examples](https://github.com/JAANULO/cplex-opl-examples)

*If you find this plugin helpful for your research, studies, or industrial optimization projects, please consider leaving a rating and review on the JetBrains Marketplace!*
