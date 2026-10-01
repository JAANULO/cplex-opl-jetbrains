// Test for CPLEX OPL built-in functions, CP constraints, and script symbols
using CP;

dvar interval a;
dvar interval b[1..5];
dvar sequence seq in all(i in 1..5) b[i];
cumulFunction c = stepAt(0, 10) + pulse(a, 5);

subject to {
    span(a, all(i in 1..5) b[i]);
    alternative(a, all(i in 1..5) b[i]);
    synchronize(a, all(i in 1..5) b[i]);
    forbidStart(a, stepFunction(0, 100));
    startBeforeStart(a, b[1]);
    endBeforeEnd(a, b[1]);
    noOverlap(seq);
    alwaysIn(c, 0, 100, 0, 20);
}

execute {
    writeln("Testing ILOG script globals");
    write("Model: ");
    thisOplModel.generate();
    cplex;
    cp;
    Opl;
    IloOplOutputFile;
    IloOplInputFile;
    IloOplModel;
    IloOplDataElements;
    IloOplCallJava;
    IloOplImportJava;
}
