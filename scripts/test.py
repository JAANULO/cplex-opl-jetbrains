"""
Script to automate building and testing of the cplex-opl-jetbrains plugin.

Usage:
    python scripts/test.py [mode]

Modes:
    generate    - generates lexer and parser from .flex and .bnf files
    build       - generate + compile Kotlin and Java
    test        - build + run JUnit tests
    perf        - run Performance and stress tests
    test:all    - run all tests (Unit, Platform and Performance)
    package     - test + package plugin into .zip
    verify      - package + verify plugin compatibility with IDEs
    regression  - build plugin + run regression tests in cplex-opl-examples
    coverage    - generate HTML coverage report (Kover)
    full        - run all steps sequentially (test FIRST, fail fast)

Example:
    python scripts/test.py test
    python scripts/test.py regression
    python scripts/test.py full

NOTE: runIde is intentionally omitted - run manually: gradlew.bat runIde (Windows)
"""

import subprocess
import sys
import platform
import re
import json
from pathlib import Path
from datetime import datetime

if sys.stdout and hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')


# --- Configuration ---

GRADLEW = "gradlew.bat" if platform.system() == "Windows" else "./gradlew"

PROJECT_ROOT = Path(__file__).parent.parent
LOG_DIR = PROJECT_ROOT / "build" / "agent-logs"
EXAMPLES_ROOT = PROJECT_ROOT.parent / "cplex-opl-examples"

EXAMPLES_GRADLEW = "gradlew.bat" if platform.system() == "Windows" else "./gradlew"

STEPS = {
    "generate": [
        (PROJECT_ROOT, [GRADLEW, "generateLexer", "generateParser"]),
    ],
    "build": [
        (PROJECT_ROOT, [GRADLEW, "classes", "testClasses"]),
    ],
    "test": [
        (PROJECT_ROOT, [GRADLEW, "test", "--info"]),
    ],
    "perf": [
        (PROJECT_ROOT, [GRADLEW, "test", "-Psuite=perf", "--info"]),
    ],
    "test:all": [
        (PROJECT_ROOT, [GRADLEW, "test", "-Psuite=all", "--info"]),
    ],
    "package": [
        (PROJECT_ROOT, [GRADLEW, "buildPlugin"]),
    ],
    "verify": [
        (PROJECT_ROOT, [GRADLEW, "verifyPlugin"]),
    ],
    "coverage": [
        (PROJECT_ROOT, [GRADLEW, "koverHtmlReport"]),
    ],
    "regression": [
        (PROJECT_ROOT, [GRADLEW, "buildPlugin"]),
        (EXAMPLES_ROOT, [EXAMPLES_GRADLEW, ":test-harness:test", "--info"]),
    ],
    # test runs FIRST so a failing test suite is reported before spending time
    # packaging/verifying the plugin (fail fast, cheaper feedback loop).
    "full": [
        (PROJECT_ROOT, [GRADLEW, "test", "-Psuite=all", "--info"]),
        (PROJECT_ROOT, [GRADLEW, "buildPlugin"]),
        (PROJECT_ROOT, [GRADLEW, "verifyPlugin"]),
    ],
}

# Keyword-based filter used for the console preview. Kept intentionally broad;
# the FULL unfiltered output is always saved to disk (see LOG_DIR), so nothing
# is ever truly lost even if a message doesn't match these keywords.
IMPORTANT_KEYWORDS = [
    "ERROR", "FAILED", "FAILURE", "Exception", "error:",
    "warning:", "WARNING", "WARN",
    "Tests run:", "tests were run", "passed", "failed", "skipped",
    "BUILD SUCCESSFUL", "BUILD FAILED",
    "> Task", "Caused by:",
]

# Kotlin compiler diagnostics use short line prefixes ("e: ", "w: ") instead
# of the word "error"/"warning" - matched separately to avoid false positives
# from the generic 1-2 char prefixes appearing mid-sentence elsewhere.
KOTLIN_DIAGNOSTIC_PREFIXES = ("e: ", "w: ")


# --- Helper Functions ---

def filter_output(text: str) -> str:
    """
    Filters verbose Gradle/Kotlin output to show only relevant details in the
    console. This is a PREVIEW only - the full raw output is always written
    to a log file by run_step(), so nothing is permanently lost if a message
    doesn't match the keyword list below.
    """
    important = []
    for line in text.splitlines():
        l = line.strip()
        if l.startswith(KOTLIN_DIAGNOSTIC_PREFIXES) or any(kw in l for kw in IMPORTANT_KEYWORDS):
            important.append(line)
    return "\n".join(important) if important else text[-2000:]  # fallback: last 2000 characters


def check_zero_tests(text: str) -> bool:
    """Returns True if Gradle reported 0 tests."""
    return bool(re.search(r"0 tests", text)) or "no tests were run" in text.lower()


def save_full_log(name: str, output: str) -> Path:
    """Always persist the complete, unfiltered output to disk for later inspection."""
    LOG_DIR.mkdir(parents=True, exist_ok=True)
    timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
    safe_name = re.sub(r"[^\w\-]+", "_", name)
    log_path = LOG_DIR / f"{timestamp}_{safe_name}.log"
    log_path.write_text(output, encoding="utf-8", errors="replace")
    return log_path


def print_test_summary_report():
    """Finds the most recent test summary JSON in src/test/reports and displays a concise execution summary."""
    reports_dir = PROJECT_ROOT / "src" / "test" / "reports"
    if not reports_dir.exists():
        return
    json_files = sorted(reports_dir.glob("test-summary-*.json"), key=lambda p: p.stat().st_mtime, reverse=True)
    if not json_files:
        return
    latest_report = json_files[0]
    try:
        data = json.loads(latest_report.read_text(encoding="utf-8"))
        total = data.get("totalTests", 0)
        passed = data.get("successfulTests", 0)
        failed = data.get("failedTests", 0)
        skipped = data.get("skippedTests", 0)
        duration_ms = data.get("durationMs", 0)
        duration_s = duration_ms / 1000.0

        status_symbol = "✅" if failed == 0 else "❌"
        print(f"\n{status_symbol} Test Execution Summary:")
        print(f"    Duration: {duration_s:.2f} s ({duration_ms} ms)")
        print(f"    Total Tests: {total} (Passed: {passed}, Failed: {failed}, Skipped: {skipped})")
        print(f"    JSON Report: {latest_report.resolve()}")
    except Exception:
        pass


def print_regression_summary_report():
    """Finds or generates the regression report from cplex-opl-examples and prints it."""
    if not EXAMPLES_ROOT.exists():
        print(f"\n⚠️  Folder {EXAMPLES_ROOT} not found.")
        return

    gen_script = EXAMPLES_ROOT / "scripts" / "generate_github_summary.py"
    if gen_script.exists():
        try:
            subprocess.run([sys.executable, str(gen_script)], cwd=EXAMPLES_ROOT, capture_output=True, text=True)
        except Exception:
            pass

    summary_file = EXAMPLES_ROOT / "reports" / "local_summary.md"
    if summary_file.exists():
        print(f"\n{'='*55}")
        print("  📊 REGRESSION SUMMARY (cplex-opl-examples)")
        print(f"{'='*55}\n")
        try:
            print(summary_file.read_text(encoding="utf-8"))
        except Exception as e:
            print(f"Could not read summary: {e}")


def run_step(cwd: Path, command: list) -> bool:
    """Runs a single Gradle step in the specified directory. Returns True if successful."""
    name = " ".join(command)
    print(f"\n{'='*55}")
    print(f"  STEP: {name} (in {cwd.name})")
    print(f"{'='*55}")

    if not cwd.exists():
        print(f"\n  ❌ ERROR: Directory does not exist: {cwd}")
        return False

    result = subprocess.run(
        command,
        capture_output=True,
        text=True,
        encoding="utf-8",
        errors="replace",  # never crash on unexpected bytes (e.g. Windows locale mismatch)
        cwd=cwd,
    )

    output = result.stdout + result.stderr
    log_path = save_full_log(name, output)

    print(filter_output(output))
    print(f"\n   Pełny log zapisany: {log_path}")

    # Warning about missing tests (informs, but does not block execution)
    if "test" in command and check_zero_tests(output):
        print("\n    WARNING: No tests were found.")
        print("     Add tests in src/test/kotlin/ inheriting from BasePlatformTestCase.")

    if result.returncode != 0:
        print(f"\n  ❌ ERROR in: {name}")
        print(f"     Pełny log: {log_path}")
        print("  Stopping execution.")
        return False

    print(f"\n  ✅ OK: {name}")
    return True


# --- Main Logic ---

def main():
    available = list(STEPS.keys())

    if len(sys.argv) < 2:
        print(f"Usage: python scripts/test.py [{' | '.join(available)}]")
        print("\nMode descriptions:")
        print("  generate    - generates lexer and parser from .flex and .bnf")
        print("  build       - generate + compile")
        print("  test        - build + run fast Unit & Platform tests")
        print("  perf        - run Performance and stress tests")
        print("  test:all    - run all tests (Unit, Platform and Performance)")
        print("  package     - test + build plugin .zip package")
        print("  verify      - package + verify compatibility with IDEs")
        print("  regression  - build plugin + run regression tests on OPL models (cplex-opl-examples)")
        print("  coverage    - generate HTML coverage report (Kover)")
        print("  full        - all tests first, then package + verify (fail fast)")
        sys.exit(0)

    mode = sys.argv[1].lower()

    if mode not in STEPS:
        print(f"Unknown mode: '{mode}'")
        print(f"Available: {available}")
        sys.exit(1)

    steps = STEPS[mode]
    print(f"\n🚀 Mode: {mode.upper()} ({len(steps)} steps) | OS: {platform.system()}")

    for cwd, command in steps:
        if not run_step(cwd, command):
            sys.exit(1)

    if mode in ("test", "perf", "test:all", "full"):
        print_test_summary_report()
    elif mode == "regression":
        print_regression_summary_report()

    print(f"\n{'='*55}")
    print(f"  ✅ ALL STEPS COMPLETED SUCCESSFULLY [{mode.upper()}]")
    print(f"{'='*55}\n")


if __name__ == "__main__":
    main()
