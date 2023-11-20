import java.util.Random;

public enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    public Direction opposite() {
        // TODO: Snygga till

        if (this == UP) {
            return DOWN;
        } else if (this == RIGHT) {
            return LEFT;
        } else if (this == DOWN) {
            return UP;
        } else
            return RIGHT;
    }

    public static Direction random() {
        Random random = new Random();
        int randomdir = random.nextInt(4);
        Direction dir = UP;
        switch (randomdir) {
            case 0:
                break;
            case 1:
                dir = RIGHT;
                break;
            case 2:
                dir = DOWN;
                break;
            case 3:
                dir = LEFT;
                break;
        }

        return dir;
    }
}