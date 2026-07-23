<error descr="Type 'boolean' cannot have a range ('in' clause)">dvar boolean b in 0..1;</error>

dvar int x;
dvar int <error descr="Variable 'x' is already defined">x</error>;

dvar interval myTask;

<warning descr="Missing timing function (e.g. endOf) in objective function in scheduling.">minimize</warning> x;

subject to {
    <warning descr="Using non-linear function 'max' may increase computation time (MIP).">max</warning>(x, 5) <= 10;
    x == <error descr="Undefined variable: 'undefinedVar'">undefinedVar</error>;
}
