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

    // private Board myBoard = new Board();
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

        Runnable warLoop = () -> {

            while (gameIsRunning) {

                //client
                //börja gissa alltså skicka iväg data
                if (firstGuess && !server) {
                    outgoingMessage = sendGuess();
                    writer.println(outgoingMessage);
                    System.out.println("my first message is " + outgoingMessage);
                    firstGuess = false;

                } else {
                    try {
                        if (reader.ready()) {

                            //tar emot svar hm och gissning
                            incomingmessage = reader.readLine();

                            // om miss
                            if (incomingmessage.startsWith("m")) {
                                //cannon.cannonBallHit(battelBoard.rectangleCellsEnemy, xFromoldGuess(), yFromOldGuess(), true);
                                Platform.runLater(cannon.cannonBallHit(battelBoard.rectangleCellsEnemy, xFromoldGuess(), yFromOldGuess(), false));
                                System.out.println("ska skjuta på miss");
                            } else if (incomingmessage.startsWith("h")) {
                                // cannon.cannonBallHit(battelBoard.rectangleCellsEnemy, xFromoldGuess(), yFromOldGuess(), false);
                                Platform.runLater(cannon.cannonBallHit(battelBoard.rectangleCellsEnemy, xFromoldGuess(), yFromOldGuess(), true));
                                System.out.println("ska skjuta på hit");
                            }
                            //om första gissningen
                            else {
                                System.out.println("first guess still");
                            }

                            //skjuta på xy och generera svar om miss/träff
                            //   String answerHitMiss = cannon.cannonBallAnswer(battelBoard.rectangleCells, xFromNewGuess(), yFromNewGuess());
                            String answerHitMiss = cannon.cannonBallAnswer(battelBoard.rectangleCells, xFromNewGuess(), yFromNewGuess());
                            Platform.runLater(cannon.cannonBallAnswerUpdateMap(battelBoard.rectangleCells, xFromNewGuess(), yFromNewGuess()));

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                System.out.println("Could not pause due to:\n" + e.getMessage());
                            }

                            //skicka tillbaka meddelande om hit/miss plus ny gissning
                            outgoingMessage = answerHitMiss + sendGuess();
                            writer.println(outgoingMessage);
                            System.out.println("jag säger till dig " + outgoingMessage);


                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                    //_______________
//                if (reader.ready()) {
//
//                    //tar emot svar hm och gissning
//                    incomingmessage = reader.readLine();
//
//                    String[] incomingMessageList;
//                    String hitMissAnswerAndOldGuess = "";
//                    String newGuess = "testar";
//                    //dela upp incomingmessage i två ("m x,y" & "a,b")
//                    if (incomingmessage.startsWith("m") || incomingmessage.startsWith("h")) {
//                        incomingMessageList = incomingmessage.split("new guess");
//                        hitMissAnswerAndOldGuess = incomingMessageList[0];
//                        newGuess = incomingMessageList[1];
//
//
//                        // om miss
//                        if (incomingmessage.contains("m")) {
//                            //ska skjuta skottet beroende på mh
//
//                            System.out.println("vart är vi");
//                        } else {
//                            //ska skjuta skottet beroende på mh
//                            System.out.println("Här?");
//                        }
//
//                        //Ta andra delen av message (alltså nya gissningen) och skjuta i sin egen karta
//                        // och se om hit/miss
//                        System.out.println(newGuess);
//
//
//                    } else {
//                        System.out.println("First guess");
//
//
//                        //ska bara ta meddelandet och testa skjuta och skicka tillbaka
//                        //h x,y new guess a,b
//
//                        //dela upp och få x,y från incomingmessage
//                        String[] stringXY = incomingmessage.split(",");
//                        int x = Integer.parseInt(stringXY[0]);
//                        int y = Integer.parseInt(stringXY[1]);
//
//                        //skjuta på xy och generera svar om miss/träff
//                        String answerHitMiss = cannon.cannonBallAnswer(battelBoard.rectangleCells, x, y);
//                        System.out.println(answerHitMiss);
//
//                        //skicka tillbaka meddelande om hit/miss plus ny gissning
//
//                        outgoingMessage = answerHitMiss + sendGuess();
//                        System.out.println(outgoingMessage);
//
//                    }
//-------------------------------------------------------

                    //Dela upp message


//                    //uppdatera karta beroende på hm
//                    if (incomingmessage.contains("h")){
//                        cannon.cannonBallHit(battelBoard.rectangleCellsEnemy, x,y, true);
//                    }
//                    else {
//                        cannon.cannonBallHit(battelBoard.rectangleCellsEnemy,x,y, false );
//                    }


                    //sätt in nya gissningen och få fram hm


                    //sätter in i kartan och får hm
                    //skickar tillbaka hm + ny gissning
                    //uppdaterar kartan utfifrån hm


                    //Tar emot meddelande om hit/miss och gissning
//                    incomingmessage = reader.readLine();
//                    if (!incomingmessage.contains("m") || !incomingmessage.contains("h")) {
//                        //om inte innehåller m/h är det första gissningen och då vill vi bara hantera koordinater
//
//                        //får ut x,y koordinater
//                        String[] stringXY;
//                        int x;
//                        int y;
//                        stringXY = incomingmessage.split(",");
//                        System.out.println("**" + incomingmessage);
//                        x = Integer.parseInt(stringXY[0]);
//                        y = Integer.parseInt(stringXY[1]);
//
//                        //skicka tillbaka answerMH+nygissning
//                        String answerMH = cannon.cannonBallAnswer(battelBoard.rectangleCells, x, y);
//                        System.out.println(answerMH);
//                        //Skjuta skottet från första gissningen
//
//                        outgoingMessage = answerMH + sendGuess();
//                        writer.println(outgoingMessage);
//                        System.out.println("--" + outgoingMessage);
//
//                    }
//
//
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        System.out.println("Could not pause due to:\n" + e.getMessage());
//                    }
//
//                    //skicka meddelande om miss/träff & gissning
//                    outgoingMessage = "SDFSFSDASEDF";
//                    writer.println(outgoingMessage);
//                    System.out.println("--" + outgoingMessage);
//
//                    //Har skjutit skottet från motståndarens gissning på sin karta och
//                    //ska skicka tillbaka svar om H/M oku ny gissning
//                    //incommingmessage består av H/M id
//

                }

//                räkmacka++;
//                if (räkmacka == 5) {
//                    gameIsRunning = false;
//                }
//                else gameIsRunning=true;
//                int count = 1;
//                count = count + 1;
//                if (count == 10) {
//                    gameIsRunning = false;
//                }

            }

        };
        Thread updateMap = new Thread(warLoop);
        updateMap.start();

    }


//    Runnable updateInstructions = () -> {
//        for (int i = 0; i < 10; i++) {
//            int x = i;
//
//            Platform.runLater(() -> cannon.cannonBallRectangle(battelBoard.rectangleCellsEnemy, x, x));
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//    };
//
//    Thread updateMap = new Thread(updateInstructions);
//    //   updateMap.start();


    //  gameIsRunning = false;


    public String sendGuess() {
        String guess;
        //om Träff innan kör follwupShot
        if (10 == 9) {
            guess = "1,5";
        }
        //om miss kör randomshot
        else {
            guess = "new guess " + cannon.randomShotId(battelBoard.rectangleCells);
            // guess = "5,1";
        }
        return guess;
        //fast ska ha så de inte gör något på kartan, bara skickar iväg en gissning
    }


    public int xFromoldGuess() {
        //  String incomingmessage = "h 7,3new guess 0,9";
        String[] incomingMessageList;
        String hitMissAnswerAndOldGuess = "";
        //  String theNewGuess;
        int x = 0;
        //dela upp incomingmessage i två ("m x,y" & "a,b")
        incomingMessageList = incomingmessage.split("new guess ");
        hitMissAnswerAndOldGuess = incomingMessageList[0];
//        theNewGuess = incomingMessageList[1];
//        // System.out.println("motspelaren skriver " + incomingmessage);


        try {  //dela upp gamla gissningens kordinater till x,y
            String[] coordinates = hitMissAnswerAndOldGuess.split(" ");
            String[] kordinater = coordinates[1].split(",");
            x = Integer.parseInt(kordinater[0]);
        } catch (Exception e) {
            System.out.println("Första gissningen så inget att hantera ");
        }
        return x;
    }


    public int yFromOldGuess() {
        //   String incomingmessage = "h 7,3new guess 0,9";
        String[] incomingMessageList;
        String hitMissAnswerAndOldGuess = "";
        String theNewGuess;
        int y = 0;
        //dela upp incomingmessage i två ("m x,y" & "a,b")
        incomingMessageList = incomingmessage.split("new guess ");
        hitMissAnswerAndOldGuess = incomingMessageList[0];
        theNewGuess = incomingMessageList[1];
        // System.out.println("motspelaren skriver " + incomingmessage);


        try {  //dela upp gamla gissningens kordinater till x,y
            String[] coordinates = hitMissAnswerAndOldGuess.split(" ");
            String[] kordinater = coordinates[1].split(",");
            y = Integer.parseInt(kordinater[1]);

        } catch (Exception e) {
            System.out.println("Första gissningen så inget att hantera ");
        }
        return y;
    }

    public int xFromNewGuess() {
//        String incomingmessage = "h 7,3new guess 0,9";
        //dela upp incomingmessage i två ("m x,y" & "a,b")
        String[] incomingMessageList = incomingmessage.split("new guess ");
        String theNewGuess = incomingMessageList[1];

        //dela upp och få x,y från incoming message
        String[] stringXY = theNewGuess.split(",");
        int x = Integer.parseInt(stringXY[0]);

        return x;
    }

    public int yFromNewGuess() {
        // String incomingmessage = "h 7,3new guess 0,9";
        //dela upp incomingmessage i två ("m x,y" & "x,y")
        String[] incomingMessageList = incomingmessage.split("new guess ");
        String theNewGuess = incomingMessageList[1];

        //dela upp och få x,y från new guess
        String[] stringXY = theNewGuess.split(",");
        int y = Integer.parseInt(stringXY[1]);

        return y;
    }


    public String takeMessageAndSplittOldGuess(String incomingmessage) {
        // String incomingMessage = "m 1,1 new guess 2, 2";
        String[] incomingMessageList = incomingmessage.split("new guess");
        String hitMissAswerAndOldGuess = incomingMessageList[0];
        String newGuess = incomingMessageList[1];
        return incomingMessageList[0];
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

        //Innehåller texten inte H/M är det första gissningen består bara av x,y
        //då delar vi i två och sätter in i int x/y
        if ((!incomingGuess.contains("H")) || (!incomingGuess.contains("M"))) {

            stringXY = incomingGuess.split(",");
            System.out.println("**" + incomingGuess);
            x = Integer.parseInt(stringXY[0]);
            y = Integer.parseInt(stringXY[1]);

        } else {
            //Är det inte första gissningen tar vi emot texten ex "H A1 guess x,y
            //splittar bort första delen så vi bara har "x,y" kvar och delar
            //x/y till int
            String guess = incomingGuess.substring(11);

            //Split guess till två int x,y
            stringXY = guess.split(",");
            x = Integer.parseInt(stringXY[0]);
            y = Integer.parseInt(stringXY[1]);
        }

        //Returnerar cannonBallRectangle(myBoard.rectangleCell, x,y);
        //se till att det är rätt bräde och lista. Ska skjuta på sitt eget bräde där den placerat ut alla skepp
        //Funktionen skjuter skottet och reurnerar string H/M cellId
        return cannon.cannonBallRectangle(battelBoard.rectangleCells, x, y);
        // return "H";


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








