execute {
  cplex.workmem = 4096;
  cplex.threads = 2;
}

// Classical Knapsack Problem
// Demonstrates array usage, decision variables, and objective function in OPL.

// Input data definition
int Capacity = ...;
range Items = 1..5;

int Value[Items] = ...;
int Weight[Items] = ...;

// Decision variables: 1 - take item, 0 - leave item
dvar boolean Take[Items];

// Objective: maximize the total value of items taken
maximize
  sum(i in Items) Value[i] * Take[i];

// Constraints
subject to {
  CapacityConstraint:
    sum(i in Items) Weight[i] * Take[i] <= Capacity;
}

// Post-processing script (JavaScript) to output results
execute DISPLAY {
  writeln("=== KNAPSACK OPTIMIZATION RESULTS ===");
  writeln("Knapsack Capacity: ", Capacity);
  var totalWeight = 0;
  for(var i in Items) {
    if(Take[i] == 1) {
      writeln("Item ", i, " (Weight: ", Weight[i], ", Value: ", Value[i], ")");
      totalWeight += Weight[i];
    }
  }
  writeln("Total Weight: ", totalWeight);
}
