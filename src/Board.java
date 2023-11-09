import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class Board extends Application {


  @Override
  public void start(Stage primaryStage) throws Exception {

    primaryStage.setTitle("Battleships");
    primaryStage.setHeight(1000);
    primaryStage.setWidth(1000);

    RectangleCell[][] rectangleCells = new RectangleCell[10][10];

    initializeBoard(rectangleCells);

    Cannon cannon = new Cannon();

    int[] shipsPerSize = {1,1,1,1,1,1};

    boolean success = Ship.placeRandomShips(rectangleCells, shipsPerSize);

    // Gridpane to hold the game cells
    GridPane pane = new GridPane();
    // gammalt
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        pane.add(rectangleCells[i][j].getRectangelCell(), i, j);
      }
    }

    if (success) {
      System.out.println("Ships placed successfully!");
      cannon.randomShot(rectangleCells);
    } else {
      System.out.println("Failed to place ships.");
    }




    // HBox for the top labels
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
      hbox.getChildren().addAll(l);
    }

    // VBox for the side labels
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
      vbox.getChildren().add(l);
    }

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


    // BorderPane to structure the layout
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(hbox);
    borderPane.setLeft(vbox);
    borderPane.setCenter(pane);

    Scene scene = new Scene(borderPane);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private static void initializeBoard(RectangleCell[][] rectangles) {
    for (int i = 0; i < rectangles.length; i++) {
      for (int j = 0; j < rectangles[i].length; j++) {
        rectangles[i][j] = new RectangleCell();
      }
    }
  }
}