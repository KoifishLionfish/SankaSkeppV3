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


//Board board=new Board();
//board.start(new Stage());
//
//
//        Board oard = new Board();
//        try {
//            oard.startBoard(new Stage(), "titel");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
         //


        //den som är för att starta från startboard och med serverklient
        StartingBoard startingBoard = new StartingBoard();
        startingBoard.start(new Stage());
    }


}