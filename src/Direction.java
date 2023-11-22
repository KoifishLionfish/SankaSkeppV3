//Fredrik
public enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    //Malin
    public Direction opposite() {
        if (this == UP) {
            return DOWN;
        } else if (this == RIGHT) {
            return LEFT;
        } else if (this == DOWN) {
            return UP;
        } else
            return RIGHT;
    }


}