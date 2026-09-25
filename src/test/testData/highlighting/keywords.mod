// Test for newly added OPL keywords
dvar int ex;
dvar int db;
dvar cumulFunction c;
dvar stateFunction s;
dvar stepFunction st;
int x = infinity;
int y = maxint;

execute {
    // These should be parsed as executeTokens (keywords), not IDs.
    // If they were parsed as IDs, the annotator would flag them as undefined variables.
    SheetConnection;
    DBConnection;
    SheetRead;
    SheetWrite;
    DBRead;
    DBExecute;
    DBUpdate;

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
