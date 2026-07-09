using cp;

// Definicje typów i danych
range Tasks = 1..4;
int Duration[Tasks] = ...;

// Zmienne przedziałowe - określają zadanie w czasie
dvar interval Task[t in Tasks] size Duration[t];

// Sekwencja zadań na jednej maszynie (brak nakładania się)
dvar sequence Machine in all(t in Tasks) Task[t];

// Minimalizacja całkowitego czasu zakończenia wszystkich zadań (Makespan)
minimize max(t in Tasks) endOf(Task[t]);

subject to {
  // Zadania nie mogą nakładać się w czasie na maszynie
  noOverlap(Machine);
  
  // Przykładowe ograniczenie kolejności: zadanie 1 musi zakończyć się przed rozpoczęciem zadania 3
  OrderConstraint:
    endBeforeStart(Task[1], Task[3]);
}

execute DISPLAY {
  writeln("=== WYNIKI SZEREGOWANIA CP ===");
  for(var t in Tasks) {
    writeln("Zadanie ", t, " -> Start: ", Task[t].start, ", Koniec: ", Task[t].end, ", Czas trwania: ", Task[t].size);
  }
}
