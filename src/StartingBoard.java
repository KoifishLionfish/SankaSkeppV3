import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;

import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;

import java.awt.*;
import java.util.Random;


public class StartingBoard extends Application {
    private boolean server;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Start battleship");
        primaryStage.setHeight(500);
        primaryStage.setWidth(500);

        AnchorPane anchorpane = new AnchorPane();


        //Lägger till högst upp
        Label welcome = new Label("Welcome to battleship");
        welcome.setTextFill(Color.ROYALBLUE);
        welcome.setFont(Font.font(18));

        HBox hBoxTop = new HBox();
        hBoxTop.getChildren().add(welcome);
        AnchorPane.setTopAnchor(welcome, 20.0);


        //lägger till mitt i
        Button buttonServer = new Button("Server");
        Button buttonClient = new Button("Client");
        Button buttonStart = new Button("Start game");


        //startknappen inaktiv innan man valt server/klient
        //När man trycker på startknappen skapas en server/klient
        //och den startas upp
        //start fönstret stängs och det nya fönstret öppnas ifrån server/client klassen
        buttonStart.setDisable(true);
        buttonStart.setOnAction(event -> {

                    System.out.println("is server: " + server);
                    ServerClient serverClient = new ServerClient();
                    serverClient.startGame(getServer());
                    primaryStage.close();
                }
        );


        buttonServer.setOnAction(event -> {
            server = true;
            buttonStart.setDisable(false);
        });


        buttonClient.setOnAction(event -> {
            server = false;
            buttonStart.setDisable(false);
        });

        Button buttonQuit = new Button("Quit Game");
        buttonQuit.setOnAction(event -> {
                    primaryStage.close();
                }
        );



        HBox hBoxCenter = new HBox();
        hBoxCenter.getChildren().addAll(buttonServer, buttonClient, buttonStart, buttonQuit);

        anchorpane.getChildren().addAll(hBoxTop, hBoxCenter);
        AnchorPane.setTopAnchor(hBoxTop, 20.0);
        AnchorPane.setBottomAnchor(hBoxCenter, 20.0);

        Scene scene = new Scene(anchorpane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public boolean getServer() {
        return server;
    }

    public void setServer(boolean firstPlayer) {
        this.server = firstPlayer;
    }
}
