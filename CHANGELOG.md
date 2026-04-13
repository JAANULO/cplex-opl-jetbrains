<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# CPLEX-Plugin Changelog

## [Unreleased]
### Added
- Initial support for IBM ILOG CPLEX OPL.
- Syntax highlighting for .mod and .dat files.
- Run configuration for oplrun solver with .dat file support.
- Auto-Detect mechanism for automatic oplrun executable path discovery (Zero-Config).
- Global settings state to persistently save the CPLEX path across configurations.
- XML-based Live Templates for quick OPL code generation (model skeleton, forall, sum, etc.).
- **Console Issue Navigation:** Clickable file links in execution console that map solver errors directly to specific lines in the code.
- **File Type Support:** Auto-pairing of `.mod` and `.dat` files with the same name during run configuration setup.
- **Code Completion:** Basic autocompletion for OPL keywords and operators via `CompletionContributor`.
- **Structure View:** Interactive side-panel tree view for navigating variables, objectives, and constraints.
- **Code Formatter:** Automatic code formatting (`Ctrl + Alt + L`) for correct indentation and spacing in constraints and loops.
- **Custom Icons:** Unique SVG icons to visually distinguish `.mod` and `.dat` files in the project tree.

### Fixed
- Qodana linting issues.
- Plugin description in README.md.
- **Annotator:** Removed an incorrect Java-style naming validation rule that flagged uppercase OPL variable names (like `NbItems`) as errors.
- Removed duplicated `configurationType` registration from `plugin.xml`.

### Changed
- Updated `README.md` feature lists (EN/PL) to match current implementation.
- Updated `do_zrobienia.md` with explicit `DONE` / `PARTIAL` / `TODO` status markers.
