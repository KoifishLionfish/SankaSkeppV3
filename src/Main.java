import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.crypto.spec.PSource;
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


//        ServerClient testa = new ServerClient(true);
//        System.out.println(
//                testa.xFromNewGuess());
//
//        System.out.println( testa.yFromNewGuess());
    }



    public String test() {


        String incomingmessage = "h 7,3new guess 0,9";

        String[] incomingMessageList;
        String hitMissAnswerAndOldGuess = "";
        String theNewGuess;


        //dela upp incomingmessage i två ("m x,y" & "a,b")
        incomingMessageList = incomingmessage.split("new guess ");
        hitMissAnswerAndOldGuess = incomingMessageList[0];
        System.out.println("motspelaren skriver " + incomingmessage);
        //System.out.println("Första delen av meningen ovan:"+hitMissAnswerAndOldGuess);
        theNewGuess = incomingMessageList[1];
        //  System.out.println("Andra delen av meningen ovan"+theNewGuess);


        String[] cordiantes = hitMissAnswerAndOldGuess.split((" "));
        String[] kordinater = cordiantes[1].split(",");
//                        String a = kordinater[0];
//                        String b = kordinater[1];


        int a = Integer.parseInt(kordinater[0]);
        int b = Integer.parseInt(kordinater[1]);
        System.out.println("." + a + "." + " är x, " + "." + b + "." + " är y");
        return incomingMessageList[0];
    }


//    String incomingGuess = "3,5";
//
//
//    String[] stringXY = incomingGuess.split(",");
//    int x = Integer.parseInt(stringXY[0]);
//    int y = Integer.parseInt(stringXY[1]);
//
////    String x=stringXY [0];
////    String y=stringXY[1];
//        System.out.println(x);
//        System.out.println(y);
//
//}


}