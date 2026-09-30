# CPLEX OPL Support for JetBrains IDEs

Native language support for IBM ILOG CPLEX Optimization Programming Language (OPL), including syntax highlighting, code completion, and a built-in runner for the *oplrun* solver.

- **File Types & Icons:** Full support and dedicated icons for models (`.mod`), data (`.dat`), and settings (`.ops`) files, including file templates.
- **Syntax Highlighting:** Keywords, model structure, and operators for `.mod` files.
- **Code Completion:** Keyword and built-in function completion.
- **Run Configuration:** Built-in "Run Opl Model" configuration supporting Local, WSL, and Docker execution environments.
- **Auto-Pairing:** Automatic `.dat` and `.ops` file pairing when matching file names exist.
- **Console Navigation:** Clickable solver error links in the run console for `.mod`, `.dat`, and `.ops` files.
- **Structure View:** Side panel listing declarations, objective, and constraints.
- **Semantic Analysis & Inspections:** Type validation, duplicate variable detection, and non-linearity warnings for MIP.
- **Editor Utilities:** Live templates, commenter, brace matching, and a smart code formatter.

*Requires an installation of IBM ILOG CPLEX Studio (Local / WSL) or a Docker image with oplrun. The path to oplrun can be set manually or auto-detected from common install locations.*
