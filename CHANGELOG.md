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

### Fixed
- Qodana linting issues.
- Plugin description in README.md.