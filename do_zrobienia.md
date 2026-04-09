# ROADMAP.md: Strategia rozwoju i automatyzacji wtyczki OPL

Dokument ten definiuje priorytety rozwojowe wtyczki.

---

## Poziom 1: Fundamenty i Estetyka (Bardzo łatwe)

### 1. Ikony i Branding (Zadanie 4.2)
- **Opis:** Implementacja ikon SVG dla plików `.mod` i `.dat`.
- **Inżynierski zysk:** Poprawa orientacji przestrzennej użytkownika w drzewie projektu.

---

## Poziom 2: Automatyzacja i Walidacja (Średnio łatwe)

### 2. Poprawa Annotatora (Zadanie 3.2)
- **Opis:** Usunięcie błędu wielkich liter i walidacja duplikatów nazw.
- **Inżynierski zysk:** Zgodność ze standardem OPL i czystość logiczna modelu.

---

## Poziom 3: Inteligencja Edytora (Wymagające)

### 3. Inteligentne Autouzupełnianie (Zadanie 1.1)
- **Opis:** Kontekstowe podpowiedzi słów kluczowych i zmiennych.
- **Mechanizm:** Wykorzystanie klasy `CompletionContributor`.

### 4. Przygotowanie paczki i Metadata (Zadanie 4.1)
- **Opis:** Optymalizacja projektu pod JetBrains Marketplace (ikona wtyczki, opis, changelog).
- **Zasada:** Poprawna konfiguracja Gradle i unikalne FQCN.

---

## Poziom 4: Zaawansowana Analiza Systemowa (Trudne)

### 5. Widok struktury / Structure View (Zadanie 2.2)
- **Opis:** Wizualna hierarchia modelu w bocznym panelu IDE.
- **Implementacja:** Mapowanie drzewa PSI na komponenty UI.

### 6. Inspekcja nieliniowości: min, max, abs (Zadanie 3.1)
- **Opis:** Analiza wpływu na złożoność obliczeniową (MIP).
- **Logika:** Weryfikacja typu parametrów (dvar vs float/int) i ostrzeganie o niejawnej linearyzacji.

---

## Poziom 5: Architektura Referencji (Bardzo trudne)

### 7. Skakanie do definicji / Go to Declaration (Zadanie 2.1)
- **Opis:** Globalna nawigacja po identyfikatorach w projekcie.
- **Mechanizm:** `PsiReference`.
- **Wyzwanie:** Obsługa zasięgów (Scopes) i indeksowanie plików projektu.

---

## Słowniczek techniczny
- **Boilerplate code:** Fragmenty kodu, które muszą być powielane w wielu miejscach przy niewielkich zmianach.
- **Heurystyka ścieżek:** Algorytm szukający danych w najbardziej prawdopodobnych lokalizacjach zamiast skanowania całego systemu.
- **Zero-Config:** Cel projektowy, w którym oprogramowanie nie wymaga konfiguracji początkowej od użytkownika.