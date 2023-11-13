import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.text.Position;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        StartingBoard startingBoard = new StartingBoard();
        startingBoard.start(new Stage());
    }


    //Använde för att dela sträng på rätt ställe och omvandla till int
    //Kan bara strunta i den
    public void test() {

        String incomingGuess = "m A1 Guess 3,5";

        String guess = incomingGuess.substring(11);
        String[] stringXY = guess.split(",");
        int x = Integer.parseInt(stringXY[0]);
        int y = Integer.parseInt(stringXY[1]);


//    String x=stringXY [0];
//    String y=stringXY[1];
        System.out.println(x);
        System.out.println(y);

    }


}