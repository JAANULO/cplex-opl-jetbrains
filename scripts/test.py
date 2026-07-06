"""
Skrypt do automatyzacji budowania i testowania pluginu cplex-opl-jetbrains.

Uzycie:
    python scripts/test.py [tryb]

Tryby:
    generate  - generuje lexer i parser z plikow .flex i .bnf
    build     - generate + kompilacja Kotlin i Java
    test      - build + uruchomienie testow JUnit
    package   - test + budowanie .zip pluginu
    verify    - package + weryfikacja zgodnosci z IDE
    full      - wszystkie kroki po kolei

Przyklad:
    python scripts/test.py test
    python scripts/test.py full

UWAGA: runIde jest celowo pominiete - odpal recznie: gradlew.bat runIde (Windows)
"""

import subprocess
import sys
import platform
import re
from pathlib import Path

if sys.stdout and hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')


# --- Konfiguracja ---

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


# --- Pomocnicze funkcje ---

def filtruj_output(tekst: str) -> str:
    """
    Filtruje gadatliwy output Gradle do samego sedna.
    Zostawia linie z bledem, ostrzezeniem, wynikiem testu i podsumowaniem.
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
    return "\n".join(wazne) if wazne else tekst[-2000:]  # fallback: ostatnie 2000 znaków


def sprawdz_zero_testow(tekst: str) -> bool:
    """Zwraca True jesli Gradle zglosil 0 testow."""
    return bool(re.search(r"0 tests", tekst)) or "no tests were run" in tekst.lower()


def uruchom_krok(komenda: list) -> bool:
    """Uruchamia jeden krok Gradle. Zwraca True jesli sukces."""
    nazwa = " ".join(komenda)
    print(f"\n{'='*55}")
    print(f"  KROK: {nazwa}")
    print(f"{'='*55}")

    wynik = subprocess.run(
        komenda,
        capture_output=True,
        text=True,
        cwd=Path(__file__).parent.parent  # katalog glowny projektu
    )

    output = wynik.stdout + wynik.stderr
    print(filtruj_output(output))

    # Ostrzezenie o braku testow (nie blokuje, ale informuje)
    if "test" in komenda and sprawdz_zero_testow(output):
        print("\n  ⚠️  UWAGA: Nie znaleziono zadnych testow.")
        print("     Dodaj testy w src/test/kotlin/ dziedziczac po BasePlatformTestCase.")

    if wynik.returncode != 0:
        print(f"\n  ❌ BLAD w: {nazwa}")
        print("  Zatrzymuje wykonanie.")
        return False

    print(f"\n  ✅ OK: {nazwa}")
    return True


# --- Glowna logika ---

def main():
    dostepne = list(KROKI.keys())

    if len(sys.argv) < 2:
        print(f"Uzycie: python scripts/test.py [{' | '.join(dostepne)}]")
        print("\nOpis trybow:")
        print("  generate  - generuje lexer i parser z .flex i .bnf")
        print("  build     - generate + kompilacja")
        print("  test      - build + testy JUnit")
        print("  package   - test + budowanie .zip")
        print("  verify    - package + weryfikacja zgodnosci z IDE")
        print("  full      - wszystkie kroki")
        sys.exit(0)

    tryb = sys.argv[1].lower()

    if tryb not in KROKI:
        print(f"Nieznany tryb: '{tryb}'")
        print(f"Dostepne: {dostepne}")
        sys.exit(1)

    kroki = KROKI[tryb]
    print(f"\n🚀 Tryb: {tryb.upper()} ({len(kroki)} krokow) | System: {platform.system()}")

    for komenda in kroki:
        if not uruchom_krok(komenda):
            sys.exit(1)

    print(f"\n{'='*55}")
    print(f"  ✅ WSZYSTKIE KROKI ZAKONCZONE SUKCESEM [{tryb.upper()}]")
    print(f"{'='*55}\n")


if __name__ == "__main__":
    main()
