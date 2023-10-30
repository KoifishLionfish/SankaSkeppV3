import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Ship {
    public static Rectangle[][] placeRandomShips(Rectangle[][] rectangles, int numberOfShips, int shipSize) {
        Random random = new Random();
        int gridSize = rectangles.length;

        for (int ship = 0; ship < numberOfShips; ship++) {
            int row = random.nextInt(gridSize);
            int col = random.nextInt(gridSize);

            boolean canPlaceShip = true;

            for (int i = 0; i < shipSize; i++) {
                if (col + i >= gridSize || rectangles[row][col + i].getFill() == Color.ORANGE) {
                    canPlaceShip = false;
                    break;
                }
            }

            if (canPlaceShip) {
                for (int i = 0; i < shipSize; i++) {
                    rectangles[row][col + i].setFill(Color.ORANGE);
                }
            } else {
                ship--;
            }
        }
        return rectangles;
    }
}
