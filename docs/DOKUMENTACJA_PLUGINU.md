# Dokumentacja Funkcjonalności Pluginu CPLEX OPL dla JetBrains

Niniejszy dokument podsumowuje wszystkie zaimplementowane dotychczas funkcjonalności dla wtyczki wspierającej język CPLEX OPL (Optimization Programming Language) w środowiskach bazujących na IntelliJ Platform (np. IntelliJ IDEA, PyCharm, itp.).

> Zapewniam Cię na wstępie – **wszystkie wprowadzone przez nas zmiany w kodzie są zapisane na dysku**, przetestowane (przechodzą skrypt `gradle check`) i gotowe do ewentualnego wdrożenia (zbudowania ostatecznej paczki `.zip` pluginu).

---

## 1. Wsparcie Językowe i Pliki (Edytor kodu)
Plugin dodaje pełnoprawną obsługę plików z rozszerzeniami `.mod`, `.dat` oraz `.ops`.

* **Typy plików i ikony**: Każdy z formatów (`.mod` – model, `.dat` – dane, `.ops` – ustawienia) posiada własny zarejestrowany `FileType`, dedykowaną ikonę SVG oraz szablon w menu *New -> OPL File*.
* **Podświetlanie Składni (Syntax Highlighting)**: Słowa kluczowe OPL, typy danych (`int`, `float`, `dvar`), komentarze i ciągi znaków posiadają własne kolory spójne z używanym motywem IDE. Zaimplementowano specjalny Lexer (plik `opl.flex`).
* **Autouzupełnianie (Code Completion)**: Edytor podpowiada słowa kluczowe OPL, pełen zestaw funkcji i ograniczeń Constraint Programming / Scheduling (np. `span`, `alternative`, `synchronize`, `forbidStart`, `stepAt`, `count`, `distribute`, `inverse`), funkcje matematyczne oraz obiekty skryptowe IBM ILOG Script (`thisOplModel`, `cplex`, `cp`, `writeln`, `IloOpl*`) wraz z inteligentnym wstawianiem nawiasów `()`.
* **Inspekcje i Quick-Fixy**:
  * Ostrzeżenie przed użyciem funkcji CP Optimizer (np. `span`, `alternative`) w plikach bez zadeklarowanego silnika `using CP;` z akcją naprawczą `Alt+Enter` (wstawia `using CP;` na początku pliku).
  * Ostrzeżenie przed przestarzałym słowem kluczowym `struct` z Quick-Fixem zamieniającym na `tuple`.
  * Walidacja funkcji celu w modelach szeregowania zadań (brak funkcji czasowych typu `endOf`).
  * Ostrzeżenie przed funkcjami nieliniowymi (`min`, `max`, `abs`) w problemach MIP.
* **Formatowanie Kodu (Code Formatter)**: Zaimplementowano automatyczne wcięcia (indentację) kodu zgodnie z regułami języka (np. zawartość w klamrach `{ ... }` jest automatycznie wyrównywana).
* **Nawigacja (References & Go To Definition)**: Możliwość kliknięcia na zmienną z wciśniętym `Ctrl` (lub `Cmd`), aby przeskoczyć do miejsca jej definicji w obrębie pliku.
* **Widok Struktury (Structure View)**: Po lewej stronie w zakładce *Structure* IDE generuje "drzewo" pliku – pokazuje listę zadeklarowanych zmiennych decyzyjnych (`dvar`), celów (np. `maximize`) i sekcji ograniczeń. Ułatwia to nawigację po ogromnych modelach matematycznych.
* **Szablony (Live Templates i File Starter Templates)**:
  * **File Templates**: Nowe szablony plików `.mod` i `.dat` zawierają od razu gotowy, uruchamialny model optymalizacyjny z deklaracją danych, zmiennych decyzyjnych (`dvar`), funkcją celu i ograniczeniami.
  * **Live Templates**: Wpisanie krótkich skrótów automatycznie rozwija się w większe bloki kodu (`model`, `rng`, `dv`, `st`, `fa`, `sm`, `tup`, `exec`, `interval`, `span`, `knap`).
* **Komentowanie kodu**: Skrót `Ctrl+/` prawidłowo komentuje i odkomentowuje linie lub bloki za pomocą komentarzy `//` lub `/* */`.

## 2. Uruchamianie (Run Configurations)
Plugin dodaje możliwość uruchamiania modeli OPL prosto z IDE przy pomocy wbudowanego "zielonego trójkąta" (Run). Zbudowaliśmy cały potężny system konfiguracji:

* **OPL Run Configuration**: Nowy typ konfiguracji pozwalający podpiąć plik modelu (`.mod`), plik z danymi (`.dat`) oraz plik z ustawieniami (`.ops`).
* **Automatyczne wiązanie plików (Auto-Pairing & Fallback)**: Gdy wybierzesz plik `model.mod` lub uruchomisz model z menu kontekstowego (`RunOplModelAction`), plugin automatycznie przeszuka folder i dopasuje `model.dat` / `model.ops`, pliki standardowe (`data.dat`, `settings.ops`) lub pojedynczy plik z danymi/ustawieniami w katalogu.
* **Auto-wykrywanie `oplrun`**: Przycisk "Auto-Detect" w konfiguracji uruchomienia potrafi automatycznie przeszukać standardowe lokalizacje instalacji IBM CPLEX na dysku i odnaleźć plik wykonywalny `oplrun.exe`. Zapisze to ustawienie globalnie dla całego środowiska.
* **Dynamiczne parametry `.ops`**: Ponieważ CLI środowiska `oplrun` ignoruje pliki `.ops`, zbudowaliśmy parser XML z zabezpieczeniami (ochrona przed atakami XXE). Plugin przed uruchomieniem dekoduje ustawienia z pliku `.ops` (np. limit pamięci) i wstrzykuje je jako wygenerowany w locie blok `execute { ... }` do pliku uruchomieniowego w pamięci tymczasowej (temp).
* **Limit czasu (Watchdog/Timeout)**: Zabezpieczenie chroniące przed zawieszeniem komputera przez nieskończenie długie obliczenia solvera. Użytkownik w ustawieniach konfiguracji może określić np. 60 sekund. Jeśli `oplrun` przekroczy ten czas, plugin wstrzykuje czerwony komunikat błędu do konsoli i bezpiecznie ubija proces solvera.
* **Dodatkowe flagi (Additional CLI Args)**: Możliwość wstrzyknięcia dowolnych, specjalnych parametrów wywołania bezpośrednio do CLI polecenia (np. flagi tuningu).
* **Conflict Refiner**: Checkbox pozwalający na łatwe "włączenie" analizatora konfliktów.
* **Zdalne Środowisko (Execution Environment)**: Oprócz lokalnego wykonania, plugin wspiera bezproblemowe izolowane instancje solvera. Pozwala to na przetwarzanie modeli z bezpośrednim wywołaniem instancji systemu pod sub-systemem linuksowym (np. Ubuntu dla `WSL`) bądź w wyizolowanym kontenerze deweloperskim (`Docker`), w pełni maskując translację systemowych ścieżek Windowsa.

## 3. Konsola, Logi i Debugowanie
Ogromny nacisk położyliśmy na polepszenie tzw. "Developer Experience" przy szukaniu błędów w modelach.

* **Filtry linków (OplLinkFilter)**: Kiedy zwykły błąd wyskakuje na konsoli, podajemy odpowiednie koordynaty błędu, a wideo/konsola generuje niebieski, klikalny link (Hyperlink). Jego kliknięcie ustawia kursor IDE w dokładnie zepsutej linijce w odpowiednim pliku.
* **Inteligentne parsowanie Infeasibility (OplInfeasibilityFilter)**: Gdy model matematyczny jest "sprzeczny" (infeasible), zwykły `oplrun` drukuje długi i skomplikowany blok logów.
  * Nasz filtr potrafi to przechwycić, analizuje przy pomocy wyrażeń regularnych precyzyjne koordynaty (np. `ctInfeasible at 4:17-25 model.mod`) i generuje bezpośrednie linki do ograniczających (sprzecznych) równań.
  * Obsługiwane są zarówno pliki modeli (`.mod`), jak i pliki danych (`.dat`).
* **Pro-aktywne wskazówki (Hints)**: Kiedy plugin zauważy na wyjściu konsoli komunikat `<<< no solution`, ale zobaczy, że nie użyłeś Conflict Refinera, to wstrzyknie żółty komunikat (na STDERR) o treści przypominającej Ci: `[Hint: To diagnose infeasibility, enable 'Run conflict refiner' in Run Configuration settings and label your constraints]`.

## 4. Ustawienia Globalne
* **Global Configuration (Settings -> Tools -> CPLEX OPL)**: Konfiguracja IDE przechowuje zadaną przez Ciebie ścieżkę do instalacji CPLEX-a globalnie. Jeśli tworzysz nowy projekt z modelem lub nową konfigurację Run, system automatycznie zaimportuje to ustawienie. Nie musisz szukać ścieżki za każdym razem.

## 5. Informacje zwrotne, Onboarding i Raportowanie Błędów (Feedback & Onboarding)
* **Powiadomienie powitalne (OplWelcomeNotification)**: Dyskretny dymek powitalny przy pierwszym uruchomieniu projektu z plikami OPL, wskazujący auto-detekcję solvera, skrót <kbd>Shift+F10</kbd> oraz repozytorium przykładowych modeli.
* **Automatyczne Raportowanie Błędów (OplErrorReportSubmitter)**: Integracja z natywnym dialogiem błędów JetBrains IDE. W przypadku wystąpienia nieobsłużonego wyjątku w pluginie, użytkownik otrzymuje przycisk "Report Issue on GitHub", który bezpośrednio otwiera przeglądarkę ze sformatowanym zgłoszeniem (stacktrace, wersja IDE, system operacyjny).
* **System Oceniania Pluginu (OplRateAction & OplRatePrompt)**:
  * Dedykowana opcja w menu: `Help -> Rate CPLEX OPL Plugin...`.
  * Inteligentne powiadomienie (Balloon): Po 10. pomyślnym uruchomieniu modelu wtyczka wyświetla powiadomienie z trzema opcjami: *Rate on Marketplace*, *Report Issue / Feedback* (GitHub) oraz *Remind me later*.

## 6. Anonimowa Telemetria (JetBrains FUS)
* **Moduł `OplUsageCollector`**: Zbudowany w oparciu o framework JetBrains *Feature Usage Statistics* (FUS) do zbierania w pełni anonimowych metryk użycia.
* **Prywatność i Zero PII**: Plugin nie zbiera danych osobowych, nazw projektów, ścieżek do plików ani kodu źródłowego modeli matematycznych.
* **Rejestrowane metryki**: Wykonanie modelu (`model.executed` – obecność plików .dat/.ops, źródło uruchomienia, kod wyjścia), tworzenie plików (`file.created`), generowanie skryptu Pythona (`python.runner.generated`) oraz metoda detekcji solvera (`cplex.detection`).
* **Respektowanie preferencji użytkownika**: Zbieranie danych działa wyłącznie, jeśli włączona jest opcja w `Settings -> Appearance & Behavior -> System Settings -> Data Sharing`.

## Podsumowanie stanu technicznego
Wszystkie systemy – w tym parsowanie konsoli z filtrem `OplInfeasibilityFilter`, silnik watchdoga, wykrywanie plików `.dat`, telemetria FUS, powiadomienia powitalne, raportowanie błędów `OplErrorReportSubmitter`, obsługa środowisk WSL/Docker oraz budowanie dynamicznych komend CLI – posiadają pełne pokrycie testami jednostkowymi, platformowymi i wydajnościowymi. 

Architektura testowa w `src/test/kotlin/com/github/cplexopl/` została zorganizowana w strukturze *Package Mirroring* i podzielona na dedykowane suity (`OplTestSuite`, `OplPerformanceTestSuite`, `OplAllTestSuite`), a cały proces budowania i weryfikacji jest zarządzany przez skrypt `scripts/test.py`. Wszystkie 56 testów (23 klasy) przechodzą w 100% pomyślnie.

