tuple Item {
  int id;
};

dvar int x;
int y;

minimize x + y;

subject to {
  ct1: x <= 10;
}
