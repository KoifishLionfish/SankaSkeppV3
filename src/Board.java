import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class Board extends Application {

  private RectangleCell[][] rectangleCells;

  @Override
  public void start(Stage primaryStage) throws Exception {

    primaryStage.setTitle("Battleships");
    primaryStage.setHeight(610);
    primaryStage.setWidth(600);

    char idChar;
    int idNumber;
    String rektangelId;

    rectangleCells = new RectangleCell[10][10];

    initializeBoard(rectangleCells);

    int[] shipsPerSize = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

    boolean success = Ship.placeRandomShips(rectangleCells, shipsPerSize);

    // Gridpane to hold the game cells
    GridPane pane = new GridPane();

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        pane.add(rectangleCells[i][j].getRectangelCell(), i, j);

        // id för varje rektangel
        idChar = (char) (65 + j);
        idNumber = i;
        rektangelId = String.valueOf(idChar) + idNumber;
        rectangleCells[i][j].setRectangleId(rektangelId);
      }
    }

    Cannon cannon = new Cannon(); // Create an instance of Cannon outside the button action

    if (success) {
      System.out.println("Ships placed successfully!");
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

    // Button for BOOM!
    Button boomButton = new Button("BOOM!");
    boomButton.setOnAction(e -> {
      cannon.randomShot(rectangleCells);
      updateGridPane(pane);
    }); // Call randomShot when the button is clicked
    boomButton.setDisable(!success); // Disable the button if success is false

    // BorderPane to structure the layout
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(hbox);
    borderPane.setLeft(vbox);
    borderPane.setCenter(pane);
    borderPane.setBottom(boomButton);  // Set the BOOM! button at the bottom

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

  private void updateGridPane(GridPane pane) {
    // Clear the existing nodes in the GridPane
    pane.getChildren().clear();

    // Add the updated RectangleCell nodes
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        pane.add(rectangleCells[i][j].getRectangelCell(), i, j);
      }
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
