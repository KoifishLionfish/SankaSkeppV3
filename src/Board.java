import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.util.LinkedList;


public class Board extends Application {
    RectangleCell[][] rectangleCells;
    RectangleCell[][] rectangleCellsEnemy;
    private String textLable ="Welcome";
    private TextArea consoleTextArea;
    private final int MAX_GUESSES = 2;
    private LinkedList<String> recentGuesses = new LinkedList<>();





    public void startBoard(Stage primaryStage, String titel) throws Exception {




        primaryStage.setTitle(titel);
        primaryStage.setHeight(350);
        primaryStage.setWidth(700);


        rectangleCells = new RectangleCell[10][10];
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
        int[] shipsPerSize = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        boolean success = Ship.placeRandomShips(rectangleCells, shipsPerSize);


        //från jacob
        if (success) {
            System.out.println("Ships placed successfully!");
        } else {
            System.out.println("Failed to place ships.");
        }




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
        emptyLabel.setPrefSize(25, 25);
        hbox.getChildren().add(emptyLabel);
        for (int i = 0; i < 10; i++) {
            String place = "";


            Label l = new Label();
            l.setAlignment(Pos.CENTER_LEFT);
            l.setPrefSize(25, 25);
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
            l.setPrefSize(25, 25);
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




        //nytt dubbel---------------------------------------------------------------------


        //ska ha en annan pane här så det är olika listor för rektanglarna
        //gridpane med rektanglar
        rectangleCellsEnemy = new RectangleCell[10][10];
        GridPane paneEnemy = new GridPane();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                RectangleCell rectangleCellEnemy = new RectangleCell();
                rectangleCellsEnemy[i][j] = rectangleCellEnemy;
                paneEnemy.add(rectangleCellsEnemy[i][j].getRectangelCell(), i, j);


            }


        }




        //En hBox som läggs högst upp i fönstret (med position)
        HBox hboxEnemy = new HBox();
        hbox.setSpacing(0);
        emptyLabel = new Label();
        emptyLabel.setPrefSize(25, 25);
        hboxEnemy.getChildren().add(emptyLabel);
        for (int i = 0; i < 10; i++) {
            String place = "";


            Label l = new Label();
            l.setAlignment(Pos.CENTER_LEFT);
            l.setPrefSize(25, 25);
            l.setText(place + i);
            l.setAlignment(Pos.BASELINE_CENTER);
            l.setTextFill(Color.BROWN);
//            Rectangle r = new Rectangle(50, 50);
//            r.setFill(Color.CADETBLUE);
            hboxEnemy.getChildren().addAll(l);
        }


        //En vBox som läggslängst till vänster i fönstret (med position)
        VBox vboxEnemy = new VBox();
        vbox.setSpacing(0);
        for (int i = 0; i < 10; i++) {
            char ascii = (char) (65 + i);
            Label l = new Label();
            l.setAlignment(Pos.CENTER_LEFT);
            l.setPrefSize(25, 25);
            l.setText(String.valueOf(ascii));
            l.setAlignment(Pos.BASELINE_CENTER);
            l.setTextFill(Color.BROWN);
            //  Rectangle r = new Rectangle(50, 50);
            //r.setFill(Color.CADETBLUE);
            vboxEnemy.getChildren().add(l);
        }

        consoleTextArea = new TextArea();
        consoleTextArea.setEditable(false);
        consoleTextArea.setWrapText(true);
        consoleTextArea.setPrefRowCount(5);






        //vbox för utskrivt med saker
        VBox vboxText = new VBox();
        Label labelText = new Label(textLable);
        vboxText.getChildren().addAll(labelText, consoleTextArea);






        BorderPane borderPaneEnemy = new BorderPane();


        borderPaneEnemy.setTop(hboxEnemy);
        borderPaneEnemy.setLeft(vboxEnemy);
        borderPaneEnemy.setCenter(paneEnemy);




        HBox hBoxTotal = new HBox();
        hBoxTotal.getChildren().addAll(borderPane, borderPaneEnemy,vboxText);
        hBoxTotal.setSpacing(10);


        Scene scene = new Scene(hBoxTotal);
        primaryStage.setScene(scene);
        primaryStage.show();


//        Scene scene = new Scene(borderPaneEnemy);
//        primaryStage.setScene(scene);
//        primaryStage.show();
        //-----------------


//        Scene scene = new Scene(borderPane);
//        primaryStage.setScene(scene);
//        primaryStage.show();


    }


    @Override
    public void start(Stage primaryStage) throws Exception {


    }
    public void appendToConsole(String message) {
        recentGuesses.add(message);
        if (recentGuesses.size() > MAX_GUESSES) {
            recentGuesses.removeFirst(); // Remove oldest guess
        }
        updateConsole();
    }
    private void updateConsole() {
        StringBuilder text = new StringBuilder();
        for (String guess : recentGuesses) {
            text.append(guess).append("\n");
        }
        consoleTextArea.setText(text.toString());
    }






}


// initialize

