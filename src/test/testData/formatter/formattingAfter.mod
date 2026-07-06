subject to {
    x >= 0;
    forall(i in R) {
        y[i] <= 10;
    }
}

execute {
    writeln("Hello");
}
