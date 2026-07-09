// Knapsack Problem Demo - Demonstrates OPL syntax, structure parsing, JS scripting, and solver integration

// Input Data
int Capacity = 15;
range Items = 1..5;

int Value[Items] = [4, 2, 1, 2, 10];
int Weight[Items] = [12, 1, 2, 1, 4];

// Decision Variables
dvar boolean Take[Items];

// Objective: Maximize total value
maximize
sum(i in Items) Value[i] * Take[i];

// Constraints
subject to {
  CapacityConstraint:
  sum(i in Items) Weight[i] * Take[i] <= Capacity;
}

// Post-processing Script
execute DISPLAY_RESULTS {
  writeln(" = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = ");
  writeln(" = = = KNAPSACK SOLVER RESULTS = = = ");
  writeln(" = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = ");
  writeln("Knapsack Capacity: ", Capacity);

  var totalWeight = 0;
  var totalValue = 0;

  for(var i in Items) {
    if(Take[i] = = 1) {
      writeln("Item ", i, " -> Weight: ", Weight[i], ", Value: ", Value[i]);
      totalWeight + = Weight[i];
      totalValue + = Value[i];
    }
  }

  writeln("-----------------------------------------");
  writeln("Total Weight Used: ", totalWeight, " / ", Capacity);
  writeln("Total Optimized Value: ", totalValue);
  writeln(" = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = ");
}





