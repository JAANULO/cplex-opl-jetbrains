// Prosty model do weryfikacji drzewa PSI
dvar int x;
dvar float y;

minimize x + y;

subject to {
    x >= 0;
    y >= 0;
}
