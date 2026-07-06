using cp;

dvar interval task1 in 1..10 size 5;
dvar sequence seq in all(i in 1..5) task[i];

minimize max(i in 1..5) endOf(task[i]);

subject to {
    noOverlap(seq);
}

execute {
    var str = "CP Model";
    writeln(str);
}
