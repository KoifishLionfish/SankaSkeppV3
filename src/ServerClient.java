import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClient extends Thread {

    int räkmacka = 0;
    BufferedReader reader;
    PrintWriter writer;
    private boolean gameIsRunning = true;

    private String incomingmessage;

    private String outgoingMessage;
    private boolean server;

    // private Cannon cannon = new Cannon();

    private Board myBoard = new Board();
    private String titel;
    private boolean firstGuess = true;

    public ServerClient(boolean server) {
        this.server = server;
    }

    @Override
    public void run() {
        start();
    }

    public void start() {
        this.titel = titel;

        if (server) {
            titel = "Server";
        } else {
            titel = "Client";
        }


//startar upp ett bräde
        Board battelBoard = new Board();
        try {
            battelBoard.startBoard(new Stage(), titel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        //Ny kortare socket server-----------------------------------------


        try {
            Socket socket;
            if (!server) {
                //CLIENT**********************************************************************
                //Skapa connection mellan klient och server
                socket = new Socket("localhost", 8080);
                System.out.println("Connected to server");

            } else {
                //SERVER**********************************************************************
                //skapa connection mellan client och server
                ServerSocket serverSocket = new ServerSocket(8080);
                System.out.println("Waiting for client");
                socket = serverSocket.accept();
                System.out.println("Client connected");
            }

            //för att ta emot och hantera data vi får
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));

            //för att ta hand om data vi vill skicka vidare
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            //}catch här annars
            while (gameIsRunning) {

                //client
                //börja gissa alltså skicka iväg data
                if (firstGuess&&!server) {
                    outgoingMessage = "Min första gissning";
                    writer.println(outgoingMessage);
                    System.out.println("--"+outgoingMessage);
                    firstGuess = false;

                } else {

                    if (reader.ready()) {
//                        //Tar emot meddelande om gissning och hit/miss
                        incomingmessage = reader.readLine();
                        System.out.println(incomingmessage);

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            System.out.println("Could not pause due to:\n" + e.getMessage());
                        }
                        //skicka meddelande om miss/träff & gissning
                        outgoingMessage = "Du missade, jag gissar nu";
                        writer.println(outgoingMessage);
                        System.out.println("--"+outgoingMessage);
                    }
                }

                gameIsRunning=false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        //test här emellan -------------------------------------------


        //Startar upp server/client beroende på val
//        try {
//            if (!server) {
//                //CLIENT**********************************************************************
//                //Skapa connection mellan klient och server
//
//                Socket socket = new Socket("localhost", 8080);
//                System.out.println("Connected to server");
//
//                //för att ta emot och hantera data vi får från servern
//                InputStream inputStream = socket.getInputStream();
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                //för att ta hand om data vi vill skicka vidare till Servern
//                OutputStream outputStream = socket.getOutputStream();
//                writer = new PrintWriter(outputStream, true);
//
//
//                int countii = 0;
//                while (gameIsRunning && countii < 10) {
//
//
//                    //client
//                    //börja gissa alltså skicka iväg data
//                    if (firstGuess) {
//                        outgoingMessage = "Min första gissning";
//                        writer.println(outgoingMessage);
//                        System.out.println(outgoingMessage);
//                        firstGuess = false;
//
//                    } else {
//                        if (reader.ready()) {
////                        //Tar emot meddelande om hit/miss
//                            incomingmessage = reader.readLine();
//                            System.out.println(incomingmessage);
//
//
//                            //ta emot gissning
//                            incomingmessage = reader.readLine();
//                            System.out.println(incomingmessage);
//
//
//                            try {
//                                Thread.sleep(2000);
//                            } catch (InterruptedException e) {
//                                System.out.println("Could not pause due to:\n" + e.getMessage());
//                            }
//                            //skicka meddelande om miss/träff
//                            outgoingMessage = "miss/träff";
//                            writer.println(outgoingMessage);
//                            System.out.println(outgoingMessage);
//
//                            try {
//                                Thread.sleep(2000);
//                            } catch (InterruptedException e) {
//                                System.out.println("Could not pause due to:\n" + e.getMessage());
//                            }
//                            //skicka gissning
//                            outgoingMessage = räkmackeRäkning();
//                            räkmacka++;
//                            writer.println(outgoingMessage);
//                            System.out.println(outgoingMessage);
//
//                        }
//
//
//                    }
////gameIsRunning=false;
//                }
//
//
//            } else {
//                //SERVER**********************************************************************
//                //skapa connection mellan client och server
//                ServerSocket serverSocket = new ServerSocket(8080);
//                System.out.println("Waiting for client");
//                Socket socket = serverSocket.accept();
//                System.out.println("Client connected");
//
//
//                //för att ta emot och hantera data vi får
//                InputStream input = socket.getInputStream();
//                reader = new BufferedReader(new InputStreamReader(input));
//
//                //för att ta hand om data vi vill skicka vidare
//                OutputStream output = socket.getOutputStream();
//                writer = new PrintWriter(output, true);
//
//
//                int count = 0;
//                while (gameIsRunning) {
//                    //server
//                    //börjar med att vänta på gissning från clienten
//                    //hantera den
//                    if (reader.ready()) {
//
//                        //ta emot gissning
//                        incomingmessage = reader.readLine();
//                        System.out.println("--Clienten gissade: " + incomingmessage);
//
//                        //skicka tillbaka meddelande om miss/träff
//                        outgoingMessage = "Hit/Miss";
//                        writer.println(outgoingMessage);
//                        System.out.println("--du " + outgoingMessage);
//
//
//                        //Skicka egen gissning
//                        outgoingMessage = "Server gissar j9";
//                        writer.println(outgoingMessage);
//                        System.out.println("--" + outgoingMessage);
//
////ta emot meddelande om miss/träff
//                        if (reader.ready()) {
//                            incomingmessage = reader.readLine();
//                            System.out.println(incomingmessage);
//                        }
//                    }
//
//                    // gameIsRunning=false;
//                }
//
//            }
//
//
//        } catch (
//                Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

    }
    //tråd kan vara mitt i start
    public String räkmackeRäkning() {


        if (räkmacka > 10) {
            gameIsRunning = false;
            return "Gissat 10 gånger";
        } else
            return "Gissar igen";
    }
}



