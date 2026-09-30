# Raport Pokrycia Testami (Test Coverage) dla Pluginu CPLEX OPL

Poniższy raport stanowi analizę architektury testowej projektu, dzieląc zaimplementowane funkcjonalności na te weryfikowane przez wbudowane **testy jednostkowe i platformowe (zautomatyzowane w Pluginie)** oraz te wymagające środowiska **poligonu doświadczalnego** z repozytorium zewnętrznego (`cplex-opl-examples`).

---

## 1. Wsparcie Językowe (Edytor kodu)
**Pokrycie zautomatyzowane: ~100%** | **Lokalizacja testów: Wewnętrzne testy Pluginu (`src/test/kotlin/com/github/cplexopl/`)**

Ta sekcja jest w pełni i bezpośrednio chroniona przez zautomatyzowane ramy testowe (*IntelliJ Platform Test Framework*). Jakakolwiek zmiana w gramatyce, która psuje kompatybilność wsteczną, zostaje natychmiast wychwycona podczas buildu pluginu.
Klasy testowe zorganizowane są w strukturze *Package Mirroring*, odpowiadającej pakietom w `src/main/`:
* **Parsowanie i Lexer** (`parser/OplParsingTest.kt`, `parser/OplPiecewiseParsingTest.kt`) – weryfikuje poprawne budowanie drzew składniowych AST z kodu źródłowego oraz obsługę funkcji kawałkami (piecewise).
* **Formatowanie i Komentowanie** (`formatter/OplFormattingTest.kt`, `features/OplCommenterTest.kt`) – zapobiega psuciu wcięć kodu i komentowania linii/bloków.
* **Widok struktury i Szablony** (`structure/OplStructureViewTest.kt`, `templates/OplLiveTemplatesTest.kt`) – gwarantuje, że podgląd plików (Structure View) i szybkie skróty tekstowe (Live Templates) ładują się prawidłowo.
* **Nawigacja i Dołączanie plików** (`reference/OplReferenceTest.kt`, `reference/OplIncludeTest.kt`) – testuje przechodzenie do definicji metod i zmiennych oraz dyrektywy `include`.
* **Podświetlanie i Analiza Semantyczna** (`highlighting/OplHighlightingTest.kt`) – weryfikuje poprawność lekserów i annotatorów podświetlania składni.
* **Autouzupełnianie kodu** (`completion/OplCompletionTest.kt`) – testuje autouzupełnianie słów kluczowych oraz zmiennych kontekstowych z drzewa PSI.

---

## 2. Uruchamianie (Run Configurations)
**Pokrycie zautomatyzowane: ~85%, Weryfikacja manualna: 15%**

* **Parsowanie ustawień `.ops` i ochrona XXE** (`run/OplRunConfigurationTest.kt`) | **Lokalizacja testów: Wewnętrzne testy Pluginu** 
  Przetestowane rygorystycznie w pliku `OplRunConfigurationTest.kt`. Sprawdza dekodowanie symboli specjalnych (np. `&amp;`), a także dedykowane zabezpieczenie wyłapujące ataki `XXE` (wstrzykiwanie encji zewnętrznych XML).
* **Środowiska WSL i Docker oraz translacja ścieżek** (`run/OplPathTranslatorTest.kt`, `run/OplRunConfigurationIntegrationTest.kt`) | **Lokalizacja testów: Wewnętrzne testy Pluginu**
  Weryfikuje poprawne budowanie komend CLI dla `LOCAL`, `WSL` (`wsl.exe`) oraz kontenerów `DOCKER`, w tym automatyczną translację ścieżek Windows (`C:\...`) na format linuksowy (`/mnt/c/...` lub `/workspace/...`).
* **Auto-wiązanie plików i walidacja** (`run/OplRunConfigurationTest.kt`) | **Lokalizacja testów: Wewnętrzne testy Pluginu** 
  Logika pilnująca spójności rzuca odpowiednie wyjątki w środowisku testowym (np. gdy załączono nieistniejący plik z danymi).
* **Auto-wykrywanie instalacji CPLEX** (`utils/CplexPathFinderTest.kt`) | **Lokalizacja testów: Wewnętrzne testy Pluginu**
  W pełni przetestowane z użyciem tymczasowych struktur folderów (`TemporaryFolder`), sprawdzające wybór najwyższej wersji CPLEX oraz obsługę zmiennej środowiskowej `CPLEX_STUDIO_DIR` dla Windows i Linux.
* **Ustawienia Globalne IDE** (`settings/OplSettingsTest.kt`) | **Lokalizacja testów: Wewnętrzne testy Pluginu**
  Przetestowane w oparciu o `BasePlatformTestCase`. Weryfikuje cykl życia `OplSettingsConfigurable`, utrwalanie stanu `OplSettingsState` oraz działanie metody `isModified()`.
* **Watchdog / Timeout i przekazywanie Flag CLI** | **Lokalizacja testów: Zewnętrzne repozytorium `cplex-opl-examples`** 
  Moduły bezpośredniej interakcji z procesem solvera są dodatkowo weryfikowane w boju z użyciem modeli testowych z zewnętrznego repozytorium `cplex-opl-examples`.

---

## 3. Konsola, Logi, Błędy i Wydajność
**Pokrycie zautomatyzowane: 98%, Weryfikacja manualna: 2%**

* **Filtry Konsoli i Rozwiązywanie Ścieżek** (`console/OplConsoleFilterTest.kt`) | **Lokalizacja testów: Wewnętrzne testy Pluginu** 
  Sprawdzają mapowanie ścieżek plików tymczasowych do workspace, podświetlanie ograniczeń sprzecznych (*infeasibility*) oraz linkowanie błędów w `.mod` i `.dat`.
* **Automatyczne Raportowanie Błędów** (`error/OplErrorReportSubmitterTest.kt`) | **Lokalizacja testów: Wewnętrzne testy Pluginu**
  Weryfikuje integrację wyjątków pluginu z formularzem zgłoszeń issue na GitHubie.
* **Dedykowany Zestaw Testów Wydajnościowych** (`performance/`) | **Lokalizacja testów: `OplPerformanceTestSuite`**
  - `performance/OplParserPerformanceTest.kt` – benchmark parsowania skomplikowanych wyrażeń matematycznych (Pratt Parser) oparty na `PlatformTestUtil.startPerformanceTest`.
  - `performance/OplConsoleFilterPerformanceTest.kt` – filtrowanie 100 000 linii logów konsolowych oraz ochrona przed *Catastrophic Backtracking* na gigantycznych liniach.
  - `performance/OplAnnotatorPerformanceTest.kt` – wydajność analizy semantycznej dużych plików modeli.

---

## 4. Architektura Testów i Podział Suit

Aby zapewnić szybki cykl deweloperski (*Fast Feedback Loop*), testy zostały rozdzielone na odrębne zestawy uruchomieniowe (Suites):

| Zestaw Testowy | Klasa Suity | Liczba Testów | Średni Czas | Przeznaczenie |
| :--- | :--- | :--- | :--- | :--- |
| **Szybkie testy (Unit / Platform)** | `OplTestSuite` | 40 testów (17 klas) | **~19 s** | Domyślny tryb lokalny (`python scripts/test.py test`) |
| **Testy wydajnościowe (Perf)** | `OplPerformanceTestSuite` | 4 testy (3 klasy) | **~21 s** | Benchmarki i stress-testy (`python scripts/test.py perf` / CI tag `[perf]`) |
| **Pełna agregacja (All)** | `OplAllTestSuite` | 44 testy (20 klas) | **~26 s** | Przed commitem i release (`python scripts/test.py test:all` / `full`) |

---

## 5. Raportowanie i Metryki (Format JSON)

Po każdym uruchomieniu testów generowany jest ujednolicony, zwięzły raport w katalogu `src/test/reports/test-summary-<data>.json` z tabelaryczną strukturą wyników:

```json
{
  "timestamp": "2026-09-30 19:22:28 (Europe/Warsaw)",
  "pluginVersion": "1.4.9.7",
  "result": "SUCCESS",
  "totalTests": 44,
  "successfulTests": 44,
  "failedTests": 0,
  "skippedTests": 0,
  "durationMs": 26383,
  "environment": {
    "os": "Windows 10",
    "arch": "amd64",
    "availableProcessors": 16
  },
  "columns": ["class", "method", "result", "durationMs"],
  "tests": [
    ["OplParsingTest", "testSimpleModel", "SUCCESS", 1261],
    ...
  ]
}
```

Skrypt `scripts/test.py` automatycznie parsuje powyższy raport i drukuje czytelne podsumowanie metryk w terminalu.

---

## Podsumowanie i Wnioski
1. **Wewnętrzne testy Pluginu (`src/test/kotlin/...`)**: Zabezpieczają 100% logiki statycznej, gramatyki, analizy semantycznej, konfiguracji uruchomieniowych (Local/WSL/Docker), filtrów konsoli oraz regresji wydajnościowych.
2. **Repozytorium `cplex-opl-examples`**: Pełni rolę zewnętrznego poligonu integracyjnego dla manualnych testów E2E z żywym silnikiem CPLEX `oplrun`.
