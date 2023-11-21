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
import java.util.Objects;
import java.util.Random;




public class StartingBoard extends Application {
    private boolean server;


    @Override
    public void start(Stage primaryStage) throws Exception {



        primaryStage.setTitle("Start battleship");
        primaryStage.setHeight(338);
        primaryStage.setWidth(600);

        VBox mainVbox = new VBox();
        mainVbox.setAlignment(Pos.CENTER);
        mainVbox.setSpacing(20);


        //Lägger till högst upp i mainvbox
        Label welcome = new Label("Welcome to battleship");
        welcome.setId("startlabel");
        welcome.setTextFill(Color.ROYALBLUE);
        welcome.setFont(Font.font("Verdana", 30));
        VBox vBox = new VBox(welcome);
        vBox.setId("vboxlabel");
        welcome.getStyleClass().add("outlined-text");



        Label select = new Label("");



        //lägger till i buttonvbox och skapar Id för objekt
        Button buttonServer = new Button("Server");
        buttonServer.setId("serverButton");
        Button buttonClient = new Button("Client");
        buttonClient.setId("buttonClient");
        Button buttonStart = new Button("Start game");
        buttonStart.setId("startBtn");



        //startknappen inaktiv innan man valt server/klient
        //När man trycker på startknappen skapas en server/klient
        //start fönstret stängs och det nya fönstret öppnas ifrån server/client klassen
        buttonStart.setDisable(true);
        buttonStart.setOnAction(event -> {
                    primaryStage.close();
                    System.out.println("is server: " + server);
                    ServerClient serverClient = new ServerClient(server);
                    try {
                        serverClient.run();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
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
        buttonQuit.setId("quit");
        buttonQuit.setOnAction(event -> {
                    primaryStage.close();
                }
        );


        VBox vBoxButtons = new VBox();
        vBoxButtons.getChildren().addAll(buttonServer, buttonClient, buttonStart, buttonQuit);
        vBoxButtons.setAlignment(Pos.CENTER);
        vBoxButtons.setSpacing(15);


        mainVbox.getChildren().addAll(welcome, select, vBoxButtons);

        mainVbox.setId("mainVbox");
        Scene scene = new Scene(mainVbox);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
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