"""
Script to automate building and testing of the cplex-opl-jetbrains plugin.

Usage:
    python scripts/test.py [mode]

Modes:
    generate  - generates lexer and parser from .flex and .bnf files
    build     - generate + compile Kotlin and Java
    test      - build + run JUnit tests
    package   - test + package plugin into .zip
    verify    - package + verify plugin compatibility with IDEs
    full      - run all steps sequentially (test FIRST, fail fast)

Example:
    python scripts/test.py test
    python scripts/test.py full

NOTE: runIde is intentionally omitted - run manually: gradlew.bat runIde (Windows)
"""

import subprocess
import sys
import platform
import re
from pathlib import Path
from datetime import datetime

if sys.stdout and hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')


# --- Configuration ---

GRADLEW = "gradlew.bat" if platform.system() == "Windows" else "./gradlew"

PROJECT_ROOT = Path(__file__).parent.parent
LOG_DIR = PROJECT_ROOT / "build" / "agent-logs"

STEPS = {
    "generate": [
        [GRADLEW, "generateLexer", "generateParser"],
    ],
    "build": [
        [GRADLEW, "classes", "testClasses"],
    ],
    "test": [
        [GRADLEW, "test", "--info"],
    ],
    "package": [
        [GRADLEW, "buildPlugin"],
    ],
    "verify": [
        [GRADLEW, "verifyPlugin"],
    ],
    "coverage": [
        [GRADLEW, "koverHtmlReport"],
    ],
    # test runs FIRST so a failing test suite is reported before spending time
    # packaging/verifying the plugin (fail fast, cheaper feedback loop).
    "full": [
        [GRADLEW, "test", "--info"],
        [GRADLEW, "buildPlugin"],
        [GRADLEW, "verifyPlugin"],
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


def run_step(command: list) -> bool:
    """Runs a single Gradle step. Returns True if successful."""
    name = " ".join(command)
    print(f"\n{'='*55}")
    print(f"  STEP: {name}")
    print(f"{'='*55}")

    result = subprocess.run(
        command,
        capture_output=True,
        text=True,
        encoding="utf-8",
        errors="replace",  # never crash on unexpected bytes (e.g. Windows locale mismatch)
        cwd=PROJECT_ROOT,
    )

    output = result.stdout + result.stderr
    log_path = save_full_log(name, output)

    print(filter_output(output))
    print(f"\n  📄 Pełny log zapisany: {log_path}")

    # Warning about missing tests (informs, but does not block execution)
    if "test" in command and check_zero_tests(output):
        print("\n  ⚠️  WARNING: No tests were found.")
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
        print("  generate  - generates lexer and parser from .flex and .bnf")
        print("  build     - generate + compile")
        print("  test      - build + run JUnit tests")
        print("  coverage  - generate HTML coverage report (Kover)")
        print("  package   - test + build plugin .zip package")
        print("  verify    - package + verify compatibility with IDEs")
        print("  full      - test first, then package + verify (fail fast)")
        sys.exit(0)

    mode = sys.argv[1].lower()

    if mode not in STEPS:
        print(f"Unknown mode: '{mode}'")
        print(f"Available: {available}")
        sys.exit(1)

    steps = STEPS[mode]
    print(f"\n🚀 Mode: {mode.upper()} ({len(steps)} steps) | OS: {platform.system()}")

    for command in steps:
        if not run_step(command):
            sys.exit(1)

    print(f"\n{'='*55}")
    print(f"  ✅ ALL STEPS COMPLETED SUCCESSFULLY [{mode.upper()}]")
    print(f"{'='*55}\n")


if __name__ == "__main__":
    main()
