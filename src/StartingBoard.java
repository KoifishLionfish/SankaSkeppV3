import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import javafx.scene.control.Label;
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
        primaryStage.setHeight(450);
        primaryStage.setWidth(400);


        VBox mainVbox = new VBox();
        mainVbox.setAlignment(Pos.CENTER);
        mainVbox.setSpacing(20);


        //Lägger till högst upp i mainvbox
        Label welcome = new Label("Welcome to battleship");
        welcome.setTextFill(Color.ROYALBLUE);
        welcome.setFont(Font.font("Lucida Calligraphy", 30));


        Label select = new Label("Select Server or Client to start the game");
        select.setFont(Font.font("Lucida Calligraphy", 14));

        //lägger till i buttonvbox
        Button buttonServer = new Button("Server");
        Button buttonClient = new Button("Client");
        Button buttonStart = new Button("Start game");


        buttonServer.setShape(new Circle(1.5));
        //startknappen inaktiv innan man valt server/klient
        //När man trycker på startknappen skapas en server/klient
        //start fönstret stängs och det nya fönstret öppnas ifrån server/client klassen
        buttonStart.setDisable(true);
        buttonStart.setOnAction(event -> {
                    primaryStage.close();
                    System.out.println("is server: " + server);
                    ServerClient serverClient = new ServerClient(server);
                    serverClient.start();

//
//                Board battelBoard = new Board();
//                try {
//                    battelBoard.start(new Stage());
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }

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

        VBox vBoxButtons = new VBox();
        vBoxButtons.getChildren().addAll(buttonServer, buttonClient, buttonStart, buttonQuit);
        vBoxButtons.setAlignment(Pos.CENTER);
        vBoxButtons.setSpacing(15);

        mainVbox.getChildren().addAll(welcome, select, vBoxButtons);

        Scene scene = new Scene(mainVbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public boolean getServer() {
        return server;
    }

    public void setServer(boolean server) {
        this.server = server;
    }
}