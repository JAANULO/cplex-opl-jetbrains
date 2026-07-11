using cp;

// Type and data definitions
range Tasks = 1..4;
int Duration[Tasks] = ...;

// Interval variables - represent tasks over time
dvar interval Task[t in Tasks] size Duration[t];

// Sequence of tasks on a single machine (no overlap)
dvar sequence Machine in all(t in Tasks) Task[t];

// Minimize makespan (total completion time of all tasks)
minimize max(t in Tasks) endOf(Task[t]);

subject to {
  // Tasks cannot overlap in time on the machine
  noOverlap(Machine);
  
  // Example precedence constraint: Task 1 must finish before Task 3 starts
  OrderConstraint:
    endBeforeStart(Task[1], Task[3]);
}

execute DISPLAY {
  writeln("=== CP SCHEDULING RESULTS ===");
  for(var t in Tasks) {
    writeln("Task ", t, " -> Start: ", Task[t].start, ", End: ", Task[t].end, ", Duration: ", Task[t].size);
  }
}
