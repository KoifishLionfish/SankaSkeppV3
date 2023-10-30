import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameBoard {
    static void Rectangles(GridPane pane, Rectangle[][] rectangles) {
        int count = 0;
        for (int i = 0; i < 10; i++) {
            count++;
            for (int j = 0; j < 10; j++) {
                Rectangle rectangle = new Rectangle(50, 50);
                rectangle.setStroke(Color.BLACK);
                rectangle.setFill(Color.ROYALBLUE);

                rectangles[i][j] = rectangle;

                pane.add(rectangle, j, i);
                count++;
            }
        }
        Ship.placeRandomShips(rectangles, 1, 1);
        Ship.placeRandomShips(rectangles, 1, 2);
        Ship.placeRandomShips(rectangles, 1, 3);
        Ship.placeRandomShips(rectangles, 1, 4);
        Ship.placeRandomShips(rectangles, 1, 5);
    }
}
