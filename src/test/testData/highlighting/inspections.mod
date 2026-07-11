dvar int x;
dvar interval myTask;

<warning descr="Missing timing function (e.g. endOf) in objective function in scheduling.">minimize</warning> x;

subject to {
    <warning descr="Using non-linear function 'max' may increase computation time (MIP).">max</warning>(x, 5) <= 10;
}
