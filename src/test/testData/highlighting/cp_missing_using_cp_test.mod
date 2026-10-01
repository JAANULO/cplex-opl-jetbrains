// Model using CP functions without 'using CP;' declaration
dvar interval a;
dvar interval b[1..5];

subject to {
    <warning descr="Constraint Programming function 'span' requires 'using CP;' declaration">span</warning>(a, all(i in 1..5) b[i]);
    <warning descr="Constraint Programming function 'alternative' requires 'using CP;' declaration">alternative</warning>(a, all(i in 1..5) b[i]);
}
