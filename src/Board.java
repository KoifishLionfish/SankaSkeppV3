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
//Malin

public class Board extends Application {
    RectangleCell[][] rectangleCells;
    RectangleCell[][] rectangleCellsEnemy;
    private String textLable = "Console output: ";
    private TextArea consoleTextArea;
    private final int MAX_GUESSES = 10;
    private LinkedList<String> recentGuesses = new LinkedList<>();


    public void startBoard(Stage primaryStage, String titel) throws Exception {

        primaryStage.setTitle(titel);
        primaryStage.setHeight(350);
        primaryStage.setWidth(750);


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


        //testar att loopa igenom placering av skepp
        boolean success = false;
        int[] shipsPerSize = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        while (!success) {
            success = Ship.placeRandomShips(rectangleCells, shipsPerSize);
            if (success) {
                System.out.println("alla skepp placerades");
            } else {
                System.out.println("Misslyckades med att placera skepp");
            }
        }


        //En hBox som läggs högst upp i fönstret (med position) för min karta
        HBox hbox = new HBox();
        hbox.setSpacing(1);
        Label emptyLabel = new Label();
        emptyLabel.setPrefSize(24.9, 25);
        hbox.getChildren().add(emptyLabel);
        for (int i = 0; i < 10; i++) {
            String place = "";


            Label l = new Label();
            l.setAlignment(Pos.CENTER_LEFT);
            l.setPrefSize(25, 25);
            l.setText(place + i);
            l.setAlignment(Pos.BASELINE_CENTER);
            l.setTextFill(Color.BROWN);
            hbox.getChildren().addAll(l);
        }


        //En vBox som läggslängst till vänster i fönstret (med position) för min karta
        VBox vbox = new VBox();
        vbox.setSpacing(1.48);
        for (int i = 0; i < 10; i++) {
            char ascii = (char) (65 + i);
            Label l = new Label();
            l.setAlignment(Pos.CENTER_LEFT);
            l.setPrefSize(25, 25);
            l.setText(String.valueOf(ascii));
            l.setAlignment(Pos.BASELINE_CENTER);
            l.setTextFill(Color.BROWN);
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


        //En hBox som läggs högst upp i fönstret (med position) för fiendekarta
        HBox hboxEnemy = new HBox();
        hboxEnemy.setSpacing(1);
        emptyLabel = new Label();
        emptyLabel.setPrefSize(24.9, 25);
        hboxEnemy.getChildren().add(emptyLabel);
        for (int i = 0; i < 10; i++) {
            String place = "";


            Label l = new Label();
            l.setAlignment(Pos.CENTER_LEFT);
            l.setPrefSize(25, 25);
            l.setText(place + i);
            l.setAlignment(Pos.BASELINE_CENTER);
            l.setTextFill(Color.BROWN);
            hboxEnemy.getChildren().addAll(l);
        }


        //En vBox som läggslängst till vänster i fönstret (med position) för fiendekarta
        VBox vboxEnemy = new VBox();
        vboxEnemy.setSpacing(1.48);
        for (int i = 0; i < 10; i++) {
            char ascii = (char) (65 + i);
            Label l = new Label();
            l.setAlignment(Pos.CENTER_LEFT);
            l.setPrefSize(25, 25);
            l.setText(String.valueOf(ascii));
            l.setAlignment(Pos.BASELINE_CENTER);
            l.setTextFill(Color.BROWN);
            vboxEnemy.getChildren().add(l);
        }

        consoleTextArea = new TextArea();
        consoleTextArea.setEditable(false);
        consoleTextArea.setWrapText(true);
        consoleTextArea.setPrefRowCount(5);
        consoleTextArea.setPrefSize(150, 180);

        //vbox för utskrivt med saker
        VBox vboxText = new VBox();
        Label labelText = new Label(textLable);
        vboxText.getChildren().addAll(labelText, consoleTextArea);

        BorderPane borderPaneEnemy = new BorderPane();

        borderPaneEnemy.setTop(hboxEnemy);
        borderPaneEnemy.setLeft(vboxEnemy);
        borderPaneEnemy.setCenter(paneEnemy);


        HBox hBoxTotal = new HBox();
        hBoxTotal.getChildren().addAll(borderPane, borderPaneEnemy, vboxText);
        hBoxTotal.setSpacing(10);


        Scene scene = new Scene(hBoxTotal);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
    }

    //Linus
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

