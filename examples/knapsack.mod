// Klasyczny problem plecakowy (Knapsack Problem)
// Pokazuje obsługę tablic, zmiennych decyzyjnych oraz funkcji celu w OPL.

// Definicja danych wejściowych
int Capacity = ...;
range Items = 1..5;

int Value[Items] = ...;
int Weight[Items] = ...;

// Zmienne decyzyjne: 1 - bierzemy przedmiot, 0 - zostawiamy
dvar boolean Take[Items];

// Funkcja celu: maksymalizacja wartości zabranych przedmiotów
maximize
  sum(i in Items) Value[i] * Take[i];

// Ograniczenia
subject to {
  CapacityConstraint:
    sum(i in Items) Weight[i] * Take[i] <= Capacity;
}

// Blok skryptowy OPL (JS) do wyświetlenia wyników w konsoli
execute DISPLAY {
  writeln("=== WYNIKI OPTYMALIZACJI PLECAKA ===");
  writeln("Pojemność plecaka: ", Capacity);
  var totalWeight = 0;
  for(var i in Items) {
    if(Take[i] == 1) {
      writeln("Przedmiot ", i, " (Waga: ", Weight[i], ", Wartosc: ", Value[i], ")");
      totalWeight += Weight[i];
    }
  }
  writeln("Całkowita waga: ", totalWeight);
}
