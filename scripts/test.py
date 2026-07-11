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
    full      - run all steps sequentially

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

if sys.stdout and hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')


# --- Configuration ---

GRADLEW = "gradlew.bat" if platform.system() == "Windows" else "./gradlew"

KROKI = {
    "generate": [
        [GRADLEW, "generateLexer"],
        [GRADLEW, "generateParser"],
    ],
    "build": [
        [GRADLEW, "generateLexer"],
        [GRADLEW, "generateParser"],
        [GRADLEW, "compileKotlin"],
        [GRADLEW, "compileJava"],
    ],
    "test": [
        [GRADLEW, "generateLexer"],
        [GRADLEW, "generateParser"],
        [GRADLEW, "compileKotlin"],
        [GRADLEW, "compileJava"],
        [GRADLEW, "test", "--info"],
    ],
    "package": [
        [GRADLEW, "generateLexer"],
        [GRADLEW, "generateParser"],
        [GRADLEW, "compileKotlin"],
        [GRADLEW, "compileJava"],
        [GRADLEW, "test"],
        [GRADLEW, "buildPlugin"],
    ],
    "verify": [
        [GRADLEW, "generateLexer"],
        [GRADLEW, "generateParser"],
        [GRADLEW, "compileKotlin"],
        [GRADLEW, "compileJava"],
        [GRADLEW, "test"],
        [GRADLEW, "buildPlugin"],
        [GRADLEW, "verifyPlugin"],
    ],
    "full": [
        [GRADLEW, "generateLexer"],
        [GRADLEW, "generateParser"],
        [GRADLEW, "compileKotlin"],
        [GRADLEW, "compileJava"],
        [GRADLEW, "test", "--info"],
        [GRADLEW, "buildPlugin"],
        [GRADLEW, "verifyPlugin"],
    ],
}


# --- Helper Functions ---

def filtruj_output(tekst: str) -> str:
    """
    Filters verbose Gradle output to show only relevant details.
    Keeps lines with errors, warnings, test results, and build summaries.
    """
    wazne = []
    for linia in tekst.splitlines():
        l = linia.strip()
        if any(kw in l for kw in [
            "ERROR", "FAILED", "FAILURE", "Exception", "error:",
            "warning:", "WARNING", "WARN",
            "Tests run:", "tests were run", "passed", "failed", "skipped",
            "BUILD SUCCESSFUL", "BUILD FAILED",
            "> Task", "Caused by:",
        ]):
            wazne.append(linia)
    return "\n".join(wazne) if wazne else tekst[-2000:]  # fallback: last 2000 characters


def sprawdz_zero_testow(tekst: str) -> bool:
    """Returns True if Gradle reported 0 tests."""
    return bool(re.search(r"0 tests", tekst)) or "no tests were run" in tekst.lower()


def uruchom_krok(komenda: list) -> bool:
    """Runs a single Gradle step. Returns True if successful."""
    nazwa = " ".join(komenda)
    print(f"\n{'='*55}")
    print(f"  STEP: {nazwa}")
    print(f"{'='*55}")

    wynik = subprocess.run(
        komenda,
        capture_output=True,
        text=True,
        cwd=Path(__file__).parent.parent  # project root directory
    )

    output = wynik.stdout + wynik.stderr
    print(filtruj_output(output))

    # Warning about missing tests (informs, but does not block execution)
    if "test" in komenda and sprawdz_zero_testow(output):
        print("\n  ⚠️  WARNING: No tests were found.")
        print("     Add tests in src/test/kotlin/ inheriting from BasePlatformTestCase.")

    if wynik.returncode != 0:
        print(f"\n  ❌ ERROR in: {nazwa}")
        print("  Stopping execution.")
        return False

    print(f"\n  ✅ OK: {nazwa}")
    return True


# --- Main Logic ---

def main():
    dostepne = list(KROKI.keys())

    if len(sys.argv) < 2:
        print(f"Usage: python scripts/test.py [{' | '.join(dostepne)}]")
        print("\nMode descriptions:")
        print("  generate  - generates lexer and parser from .flex and .bnf")
        print("  build     - generate + compile")
        print("  test      - build + run JUnit tests")
        print("  package   - test + build plugin .zip package")
        print("  verify    - package + verify compatibility with IDEs")
        print("  full      - all of the above steps")
        sys.exit(0)

    tryb = sys.argv[1].lower()

    if tryb not in KROKI:
        print(f"Unknown mode: '{tryb}'")
        print(f"Available: {dostepne}")
        sys.exit(1)

    kroki = KROKI[tryb]
    print(f"\n🚀 Mode: {tryb.upper()} ({len(kroki)} steps) | OS: {platform.system()}")

    for komenda in kroki:
        if not uruchom_krok(komenda):
            sys.exit(1)

    print(f"\n{'='*55}")
    print(f"  ✅ ALL STEPS COMPLETED SUCCESSFULLY [{tryb.upper()}]")
    print(f"{'='*55}\n")


if __name__ == "__main__":
    main()
