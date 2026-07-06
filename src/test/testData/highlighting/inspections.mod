dvar int x;
dvar interval myTask;

<warning descr="Brak funkcji czasu (np. endOf) w funkcji celu przy szeregowaniu.">minimize</warning> x;

subject to {
    <warning descr="Użycie funkcji nieliniowej 'max' może wydłużyć czas obliczeń (MIP).">max</warning>(x, 5) <= 10;
}
