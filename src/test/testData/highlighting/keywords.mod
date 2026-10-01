// Test for newly added OPL keywords
using CPLEX;
dvar int ex;
dvar int db;
dvar cumulFunction c;
dvar stateFunction s;
dvar stepFunction st;
constraint myCt[1..5];
{int} baseSet = {1, 2, 3};
{int} subset = setof(i in baseSet : i > 1) i;
int x = infinity;
int y = maxint;

<warning descr="Keyword 'struct' is deprecated in OPL; use 'tuple' instead">struct</warning> DeprecatedStruct {
    int val;
};

constraints {
    forall(i in 1..5)
        myCt[i]: ex >= 0;
}

execute {
    // These should be parsed as executeTokens (keywords), not IDs.
    // If they were parsed as IDs, the annotator would flag them as undefined variables.
    cplex;
    SheetConnection;
    DBConnection;
    SheetRead;
    SheetWrite;
    DBRead;
    DBExecute;
    DBUpdate;

    initial;
    constraint;
    constraints;
    setof;
    struct;
    template;
    prepare;
    invoke;
    ordered;
    sorted;
    reversed;
    symdiff;
    optional;
    from;
    intensity;
    types;
    stepwise;
    div;
    inter;
    union;
    diff;
    prod;
    and;
    or;
    true;
    false;
}
