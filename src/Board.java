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
        primaryStage.setWidth(1500);
        GridPane pane = new GridPane();

        Rectangle[][] rectangles = new Rectangle[10][10];

        //Gridpane som placeras i mitten av fönstret med själva spelplanen
        GameBoard.Rectangles(pane, rectangles);

        GridPane pane1 = new GridPane();
        GameBoard.Rectangles(pane1, rectangles);


        //En hBox som läggs högst upp i fönstret (med position)
        HBox hbox = hBox.gethBox();

        //En vBox som läggslängst till vänster i fönstret (med position)
        VBox vbox = vBox.getvBox();


        //Skapar en borderpane och placerar in vår Gridpane, V&HBox
        BorderPane borderPane = new BorderPane();
        BorderPane borderPane1 = new BorderPane();

        borderPane.setTop(hbox);
        borderPane.setLeft(vbox);
        borderPane.setCenter(pane);
        borderPane.setRight(pane1);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }




}

