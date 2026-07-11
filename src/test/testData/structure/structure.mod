tuple Item {
  int id;
};

dvar int x;
var int y;

minimize x + y;

subject to {
  ct1: x <= 10;
}
