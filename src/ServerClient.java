import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClient extends Thread{


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


        Board battelBoard = new Board();
        try {
            battelBoard.start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println(server);
            if (!server) {
                System.out.println("här client");
                //client
                //Skapa connection mellan klient och server
                Socket socket = new Socket("localhost", 8080);
                System.out.println("Connected to server");
                //för att ta emot och hantera data vi får från servern
                InputStream inputStream = socket.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                //för att ta hand om data vi vill skicka vidare till Servern
                OutputStream outputStream = socket.getOutputStream();
                writer = new PrintWriter(outputStream, true);

                System.out.println("är vi här");


                while (gameIsRunning) {
                    System.out.println("test igen");

                    System.out.println("You are client but choose server");
                    //börja gissa alltså skicka iväg data

                    System.out.println("fösta spelare client");


                  /*while (hit){

                        //Vänta på/ta emot svar om miss/träff

                        //om träff skjut igen ha kvar hit som true
                        //skicka tillbaka svar



                        //om miss vänta på svar från servern och
                        sätt hit till false

                 }*/

                    gameIsRunning = false;
                }
            } else {
                System.out.println("här? server");
                //Server
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

                System.out.println("eller här");

                while (gameIsRunning
//                        && startingBoard.getStartGame()
                ) {
                    System.out.println("hur långt har vi kommit?");
                    if (startingBoard.getServer()) {
                        System.out.println("You are the server and will start the game");
                        gameIsRunning = false;
                    } else {
                        System.out.println("You are still in the server but choose client");
                        //Börja ta emot ginning från clienten
                        //hantera den


                        //skicka tillbaka meddelande om miss/träff


                        gameIsRunning = false;
                    }

                }

            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}



