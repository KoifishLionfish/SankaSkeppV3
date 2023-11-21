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
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//    Board battelBoard = new Board();
//    battelBoard.start(new Stage());

    StartingBoard startingBoard = new StartingBoard();
    startingBoard.start(new Stage());


//har kvar guess xy i cannon
        //testar ändrar y när man är i sendguess metoden

        String randomshooot="2"+"3";
        String []guessIndexSplit=randomshooot.split("");
        String indexYAsLetter=Letters.intToLetter(Integer.parseInt(guessIndexSplit[1]));
        System.out.println(indexYAsLetter);
//
//
//        String gissning= "h "+"shoot "+guessIndexSplit[0]+indexYAsLetter;
//        System.out.println(gissning);




//        //i cannon ser en gissning ut 12 nu
//        //ska göra så det blir 1c
//        int x = 1;
//        int y = 2;
//        String guess;
//        guess = (x) + "" + Letters.intToLetter(y);
//        // System.out.println(guess);
//
//
////i server client
//        //ser ut "m shoot 1c
//        //vill få till 12
//
//        String guesstest = "m shoot 1c";
////
//        //Delar upp gamla gissningen
//        String[] oldGuessList = guesstest.split("");
//        int oldX = Integer.parseInt(oldGuessList[8]);
//        int oldY = Letters.valueOf((oldGuessList[9])).ordinal();
//
//
//        System.out.println("odlx: "+oldX+"oldY: "+oldY);
    }
}