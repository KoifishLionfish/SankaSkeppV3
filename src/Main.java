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

//
//        System.out.println(startingBoard.getServer());
//        if (startingBoard.getServer()){
//            System.out.println("Här");
//            Server server=new Server();
//            server.start();
//        }
//        else {
//            System.out.println("eller här?");
//            Client client=new Client();
//            client.start();
//
//        }


//        ServerClient serverClient = new ServerClient();
//        serverClient.startGame();


//serverClient.boardStart();
        // serverClient.startGame();


//        Client client = new Client();
//        Server server = new Server();
//
//        client.boardStart();
//        server.boardStart();
//        server.start();
//        client.start();


//ha som trådar som körs samtidigt?
        // Board battelBoard = new Board();
        //battelBoard.start(new Stage());


    }
}