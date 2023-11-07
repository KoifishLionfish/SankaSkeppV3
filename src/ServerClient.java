import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClient extends Thread {

    BufferedReader reader;
    PrintWriter writer;
    private boolean gameIsRunning = true;
    private int startConnection;
    StartingBoard startingBoard;

    private boolean server;


    @Override
    public void run() {
        startGame(server);
    }

    public void startGame(boolean server) {

//startar upp ett bräde
// (ev lägga till så man ändrar viss text på brädet om server/klient)
        Board battelBoard = new Board();
        try {
            battelBoard.start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        //Startar upp server/client beroende på val
        try {
            if (!server) {
                //CLIENT**********************************************************************
                //Skapa connection mellan klient och server

                Socket socket = new Socket("localhost", 8080);
                System.out.println("Connected to server");
                //för att ta emot och hantera data vi får från servern
                InputStream inputStream = socket.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                //för att ta hand om data vi vill skicka vidare till Servern
                OutputStream outputStream = socket.getOutputStream();
                writer = new PrintWriter(outputStream, true);


                while (gameIsRunning) {

                    //client
                    //börja gissa alltså skicka iväg data

                    //Vänta på/ta emot svar om miss/träff

                    //om träff skjut igen ha kvar hit som true
                    //skicka tillbaka svar

                    //om miss vänta på svar från servern och
                    // sätt hit till false


                    gameIsRunning = false;
                }


            } else {
                //SERVER**********************************************************************
                //skapa connection mellan client och server
                ServerSocket serverSocket = new ServerSocket(8080);
                System.out.println("Waiting for client");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");


                //för att ta emot och hantera data vi får från servern
                InputStream input = socket.getInputStream();
                reader = new BufferedReader(new InputStreamReader(input));

                //för att ta hand om data vi vill skicka vidare till Servern
                OutputStream output = socket.getOutputStream();
                writer = new PrintWriter(output, true);

                while (gameIsRunning) {
                    //server
                    //börjar med att vänta på gissning från clienten
                    //hantera den

                    //skicka tillbaka meddelande om miss/träff

                    gameIsRunning = false;
                }
            }


        } catch (
                Exception e) {
            System.out.println(e.getMessage());
        }
    }
}



