import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClient implements Runnable {

    int räkmacka = 0;
    BufferedReader reader;
    PrintWriter writer;
    private boolean gameIsRunning = true;

    private String incomingmessage;

    private String outgoingMessage;
    private boolean server;

    private Cannon cannon = new Cannon();

    private Board myBoard = new Board();
    private String titel;
    private boolean firstGuess = true;
    Board battelBoard = new Board();

    public ServerClient(boolean server) {
        this.server = server;
    }


    @Override
    public void run() {
        try {
            startGame();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void startGame() throws Exception {
        this.titel = titel;

        if (server) {
            titel = "Server";
        } else {
            titel = "Client";
        }


//startar upp ett bräde
        battelBoard.startBoard(new Stage(), titel);


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


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        //Kortare inte lika rörig variant av det som är under
        while (gameIsRunning) {

            //client
            //börja gissa alltså skicka iväg data
            if (firstGuess && !server) {
       // Skapar meddelande och skickar iväg
                outgoingMessage = "Meddelande";
                writer.println(outgoingMessage);
//                System.out.println("--" + outgoingMessage);
                firstGuess = false;

            } else {

                if (reader.ready()) {
                    //Tar emot meddelande om hit/miss och gissning
                    incomingmessage = reader.readLine();
                        System.out.println("--" + incomingmessage);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Could not pause due to:\n" + e.getMessage());
                    }

                    //skicka meddelande om miss/träff & gissning
                    outgoingMessage = "Du missade, jag gissar nu";
                    writer.println(outgoingMessage);
                    battelBoard.setTextLable("Hej då ");
                    System.out.println("--" + outgoingMessage);
                }
            }
        }
//Kan nog strunta i det som är under här till en början om man ska försöka förstå server/client

       //----------------------------------------------------------------------------
















        while (gameIsRunning) {

//            räkmacka++;
//            if (räkmacka == 5) {
//                gameIsRunning = false;
//            }

            //client
            //börja gissa alltså skicka iväg data
            if (firstGuess && !server) {
                outgoingMessage = sendGuess();
                writer.println(outgoingMessage);
//                System.out.println("--" + outgoingMessage);
                firstGuess = false;

            } else {

                if (reader.ready()) {
                    //Tar emot meddelande om hit/miss och gissning
                    incomingmessage = reader.readLine();

                    //Om inte innehåller det så är det första gissningen
                    if ((!incomingmessage.contains("H")) || (!incomingmessage.contains("M"))) {
//om första gissning har vi bara x,y som meddelande
//ska dela upp den till två int och sätta in i
                      //  takeGuessUpdateMapAndGenerateReply();
                    } else


//                    takeGuessUpdateMapAndSendReply(incomingmessage);


                        System.out.println("--" + incomingmessage);
                    //   taEmotGissningOchOmvandlaTillSkott(5,1);
                    // battelBoard.setTextLable("Hej Igen");

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Could not pause due to:\n" + e.getMessage());
                    }
                    //skicka meddelande om miss/träff & gissning
                    outgoingMessage = "Du missade, jag gissar nu";
                    writer.println(outgoingMessage);
                    battelBoard.setTextLable("Hej då ");
                    System.out.println("--" + outgoingMessage);
                }
            }
        }

        Runnable updateInstructions = () -> {
            for (int i = 0; i < 10; i++) {
                int x = i;

                Platform.runLater(() -> cannon.cannonBallRectangle(battelBoard.rectangleCellsEnemy, x, x));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        };

        Thread updateMap = new Thread(updateInstructions);
        updateMap.start();


        //  gameIsRunning = false;

    }


    public String sendGuess() {
        //om Träff innan kör follwupShot
        if (10 == 9) {
            return "followUp";
        }
        //om miss kör randomshot
        else return "randomShot";
        //fast ska ha så de inte gör något på kartan, bara skickar iväg en gissning
    }

    public String takeGuessUpdateMapAndGenerateReply(String incomingGuess) {

        //Uppdaterar sitt bräde där sina skepp är placerade &
        //returnerar Hit/Miss+cellId


        //Med gissningen kommer först meddelande om miss/träff på förra gissningen
        //men den struntar vi här och måste därför "Klippa" bort den.
        //incomingGuess
        //"M A1 Guess x,y"- så här ska meddelandet se ut för att det ska splittas på rätt sätt M/H
        String[] stringXY;
        int x;
        int y;


        if ((!incomingmessage.contains("H")) || (!incomingmessage.contains("M"))) {
//om första gissning har vi bara x,y som meddelande
//ska dela upp den till två int och sätta in i

            stringXY = incomingmessage.split(",");
            x = Integer.parseInt(stringXY[0]);
            y = Integer.parseInt(stringXY[1]);
        } else {
            //split så vi bara har "x,y" kvar
            String guess = incomingGuess.substring(11);

            //Split guess till två int x,y
            stringXY = guess.split(",");
            x = Integer.parseInt(stringXY[0]);
            y = Integer.parseInt(stringXY[1]);
        }
        return cannon.cannonBallRectangle(myBoard.rectangleCells, x, y);
    }


    public void takeAnswerAndUpdateMapDependingOnAnswer(String incomingAnswer) {
        //ex incomingAnswer= "H/M B5 Guess: x, y"; (plus att dens gissning för skott är med här)

//om träff
        if (incomingAnswer.startsWith("H")) {
            //skjut skott som en träff på föregående gissning
            System.out.println("Hit");
        }
//om miss
        else {
            //skjut skott som miss på föregående gissning
            System.out.println("Miss");
        }
    }


    public boolean räkmackeRäkning() {
        if (räkmacka > 10) {
            gameIsRunning = false;
        }
        return gameIsRunning;
    }

    public void taEmotGissningOchOmvandlaTillSkott(int x, int y) {

        cannon.cannonBallRectangle(battelBoard.rectangleCells, x, y);

    }
}


//threads
//När man uppdaterar UI måste det uppdateras på main tråden
//Det gör man mha Platform.runLater funktionen. Vill göra så mycket kod som möjligt
//innan så att det bara är det man faktiskt vill göra i main som hamnar i runLater
////test för threads
//                //kan skriva
//                //_____________________________________________
//                Runnable namn = new Runnable() {
//                    @Override
//                    public void run() {
//                        //vad vi vill göra här
//                    }
//                };
////-------------------------------------------------
//
//                //Kan skrivas om med lambda till
//                Runnable namne = () -> {
//                    //vad vi vill göra här
//                };
//
//                // sen skapar man en ny tråd och anger runnable i new Thread()
//                //Oavsett hur man skriver det innan
//
//                Thread threadUpdate = new Thread(namn);
//                threadUpdate.start();
//
////Slut på test threads-------------------------------------------------------------------








