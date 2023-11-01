import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Board extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {


    primaryStage.setTitle("Battleships");
    primaryStage.setHeight(1000);
    primaryStage.setWidth(1000);

    RectangleCell[][] rectangleCells = new RectangleCell[10][10];
//id för rektanglarna
    char idChar;
    int idNumber;
    String rektangelId;

    //Gridpane som placeras i mitten av fönstret med själva spelplanen
    GridPane pane = new GridPane();


    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        RectangleCell rectangleCell = new RectangleCell();
        rectangleCells[i][j] = rectangleCell;
        pane.add(rectangleCells[i][j].getRectangelCell(), i, j);


        //id för varje rektangel
        idChar = (char) (65 + j);
        idNumber = i;
        rektangelId = String.valueOf(idChar) + idNumber;
        rectangleCells[i][j].setRectangleId(rektangelId);
      }
    }


    // Button[][] shipButtons = ShipPlacement.placeRandomShips(buttons, 4, 3);
//        Ship.placeRandomShips(rectangleCells, 1, 1);
//
    Ship.placeRandomShips(rectangleCells, 2, 2);
    Ship.placeRandomShips(rectangleCells, 1, 3);
    Ship.placeRandomShips(rectangleCells, 1, 4);
    Ship.placeRandomShips(rectangleCells, 1, 5);
    shoot(rectangleCells);

    //skriver ut buttons listan för att se om den är rätt
    // for (int row = 0; row < 10; row++) {
    //   System.out.println();
    // for (int col = 0; col < 10; col++) {
    //    System.out.print(rectangles[row][col].getText() + " ");
    //      }
    // }

    //En hBox som läggs högst upp i fönstret (med position)
    HBox hbox = new HBox();
    hbox.setSpacing(0);
    Label emptyLabel = new Label();
    emptyLabel.setPrefSize(50, 50);
    hbox.getChildren().add(emptyLabel);
    for (int i = 0; i < 10; i++) {
      String place = "";

      Label l = new Label();
      l.setAlignment(Pos.CENTER_LEFT);
      l.setPrefSize(50, 50);
      l.setText(place + i);
      l.setAlignment(Pos.BASELINE_CENTER);
      l.setTextFill(Color.BROWN);
//            Rectangle r = new Rectangle(50, 50);
//            r.setFill(Color.CADETBLUE);
      hbox.getChildren().addAll(l);
    }

    //En vBox som läggslängst till vänster i fönstret (med position)
    VBox vbox = new VBox();
    vbox.setSpacing(0);
    for (int i = 0; i < 10; i++) {
      char ascii = (char) (65 + i);
      Label l = new Label();
      l.setAlignment(Pos.CENTER_LEFT);
      l.setPrefSize(50, 50);
      l.setText(String.valueOf(ascii));
      l.setAlignment(Pos.BASELINE_CENTER);
      l.setTextFill(Color.BROWN);
      //  Rectangle r = new Rectangle(50, 50);
      //r.setFill(Color.CADETBLUE);
      vbox.getChildren().add(l);
    }


    //Skapar en borderpane och placerar in vår Gridpane, V&HBox
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(hbox);
    borderPane.setLeft(vbox);
    borderPane.setCenter(pane);

    Scene scene = new Scene(borderPane);
    primaryStage.setScene(scene);
    primaryStage.show();

  }

  public void shoot(RectangleCell[][] rectangles) {
    Random random = new Random();
    int x = random.nextInt(10);
    int y = random.nextInt(10);
    RectangleCell cell = rectangles[x][y];

    if (!isCellOrange(rectangles, x, y)) {
      shoot(rectangles);
    } else {

      Direction previousDirection = Direction.UNKNOWN;

      // if shot misses
      if (isCellBlue(rectangles, x, y)) {
        makeCellBlack(rectangles, x, y);
        System.out.println("Shot missed at: " + cell.getRectangleId());
      }
      // if shot hits
      else if (isCellOrange(rectangles, x, y)) {
        makeCellRed(rectangles, x, y);
        System.out.println("Shot hit at: " + cell.getRectangleId());
        aimRandomDirection(rectangles, x, y, previousDirection); // choose random direction
      }
      // if shot hits a previously hit rectangle, retry
      else if (isCellRed(rectangles, x, y) || isCellBlack(rectangles, x, y)) {
        shoot(rectangles);
      }
    }
  }

  // followup shots if first hit is successful
  public void followUpShot(RectangleCell[][] rectangles, int x, int y, Direction previousDirection) {
    RectangleCell cell = new RectangleCell();

    // if hits, go to the same direction again
    if (isCellBlue(rectangles, x, y)) {
      makeCellBlack(rectangles, x, y);
      System.out.println("miss at: " + cell.getRectangleId());
    } else if (isCellOrange(rectangles, x, y)) {
      makeCellRed(rectangles, x, y);
      System.out.println("hit: " + cell.getRectangleId());
      if (previousDirection == Direction.UP) {
        aimUp(rectangles, x, y);
      } else if (previousDirection == Direction.RIGHT) {
        aimRight(rectangles, x, y);
      } else if (previousDirection == Direction.DOWN) {
        aimDown(rectangles, x, y);
      } else {
        aimLeft(rectangles, x, y);
      }
    }
  }

  // Choose random direction
  public void aimRandomDirection(RectangleCell[][] rectangles, int x, int y, Direction previousDirection) {
    Random random = new Random();
    int randomNr = random.nextInt(4)+1;

    if (randomNr == 1) {
      System.out.println("up");
      aimUp(rectangles, x, y);
    } else if (randomNr == 2) {
      System.out.println("right");
      aimRight(rectangles, x, y);
    } else if (randomNr == 3) {
      System.out.println("down");
      aimDown(rectangles, x, y);
    } else {
      System.out.println("left");
      aimLeft(rectangles, x, y);
    }
  }

  // Methods to choose direction
  public void aimUp(RectangleCell[][] rectangles, int x, int y) {
    if (y - 1 > 0) {
      y--;
      followUpShot(rectangles, x, y, Direction.UP);
    } else {
      aimRandomDirection(rectangles, x, y, Direction.UNKNOWN);
    }
  }

  public void aimRight(RectangleCell[][] rectangles, int x, int y) {
    if (x + 1 < 10) {
      x++;
      followUpShot(rectangles, x, y, Direction.RIGHT);
    } else {
      aimRandomDirection(rectangles, x, y, Direction.UNKNOWN);
    }
  }

  public void aimDown(RectangleCell[][] rectangles, int x, int y) {
    if (y + 1 < 10) {
      y++;
      followUpShot(rectangles, x, y, Direction.DOWN);
    } else {
      aimRandomDirection(rectangles, x, y, Direction.UNKNOWN);
    }
  }

  public void aimLeft(RectangleCell[][] rectangles, int x, int y) {
    if (x - 1 > 0) {
      x--;
      followUpShot(rectangles, x, y, Direction.LEFT);
    } else {
      aimRandomDirection(rectangles, x, y, Direction.UNKNOWN);
    }
  }


  // Methods to easier make cell black and red
  public void makeCellBlack(RectangleCell[][] rectangles, int x, int y) {
    RectangleCell cell = rectangles[x][y];
    cell.getRectangelCell().setFill(Color.BLACK);
  }

  public void makeCellRed(RectangleCell[][] rectangles, int x, int y) {
    RectangleCell cell = rectangles[x][y];
    cell.getRectangelCell().setFill(Color.RED);
  }

  // Check color of rectangle
  public boolean isCellBlue (RectangleCell[][] rectangles, int x, int y) {
    Rectangle cell = rectangles[x][y].getRectangelCell();
    return cell.getFill() == Color.ROYALBLUE;
  }

  public boolean isCellOrange (RectangleCell[][] rectangles, int x, int y) {
    Rectangle cell = rectangles[x][y].getRectangelCell();
    return cell.getFill() == Color.ORANGE;
  }

  public boolean isCellRed (RectangleCell[][] rectangles, int x, int y) {
    Rectangle cell = rectangles[x][y].getRectangelCell();
    return cell.getFill() == Color.RED;
  }

  public boolean isCellBlack (RectangleCell[][] rectangles, int x, int y) {
    Rectangle cell = rectangles[x][y].getRectangelCell();
    return cell.getFill() == Color.BLACK;
  }
}