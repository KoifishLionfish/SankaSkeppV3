import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;

import java.awt.*;
import java.util.Random;

public class Board extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        primaryStage.setTitle("Battleships");
        primaryStage.setHeight(1000);
        primaryStage.setWidth(1000);

        // Button[][] buttons = new Button[10][10];
        Rectangle[][] rectangles = new Rectangle[10][10];

        //Gridpane som placeras i mitten av fönstret med själva spelplanen
        GridPane pane = new GridPane();
        int count = 0;

        for (int i = 0; i < 10; i++) {
            count++;
            for (int j = 0; j < 10; j++) {
                Rectangle rectangle = new Rectangle(50, 50);
                rectangle.setStroke(Color.BLACK);

                // buttons[i][j] = SeaGrid.SeaGridButton();

                rectangles[i][j] = rectangle;

                if (count % 2 == 0) {
                    rectangle.setFill(Color.ROYALBLUE);
                } else {
                    rectangle.setFill(Color.ROYALBLUE);
                }

                if (rectangles[i][j].getFill() == Color.ORANGE){
                    rectangle.setFill(Color.ORANGE);
                }
                pane.add(rectangle, j, i);
                // pane.add(buttons[i][j], j, i);
                count++;
            }
        }
        // Button[][] shipButtons = ShipPlacement.placeRandomShips(buttons, 4, 3);
        Ship.placeRandomShips(rectangles, 1, 1);

        Ship.placeRandomShips(rectangles, 1, 2);
        Ship.placeRandomShips(rectangles, 1, 3);
        Ship.placeRandomShips(rectangles, 1, 4);
        Ship.placeRandomShips(rectangles, 1, 5);

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
}
