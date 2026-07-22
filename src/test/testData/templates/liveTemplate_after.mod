// OPL Model: MyModel
// Description: Model description

// === DATA ===
int n = 10;
range R = 1..n;

// === DECISION VARIABLES ===
dvar float+ x[R];

// === OBJECTIVE ===
minimize
sum(i in R) x[i];

// === CONSTRAINTS ===
subject to {
forall(i in R) {
ct_constraint:
x[i] >= 0;
}
}

