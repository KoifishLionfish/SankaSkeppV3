import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class ServerClient implements Runnable {

    private BufferedReader reader;
    private PrintWriter writer;
    private boolean gameIsRunning = true;
    private String incomingmessage;
    private String outgoingMessage;
    private boolean server;
    private MyCannon cannon = new MyCannon();
    private int randomTime;
    private String titel;
    private boolean firstGuess = true;
    private Board battelBoard = new Board();
    private String oldGuess;
    private String[] oldGuessList;
    private int oldX;
    private int oldY;
    boolean skjuterPåAktivtSkepp = false;
    String answerHitMiss;
    String[] guessIndexSplit;

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
        if (server) {
            titel = "Server";
        } else {
            titel = "Client";
        }


//startar upp ett bräde
        battelBoard.startBoard(new Stage(), titel);

//-1
        try {
       /*Här börjar server/client/socket. Först startar vi upp server/klient och sen får
       de gemensamma input/outputstreams mm
       * */
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


//-2a)
        Runnable warLoop = () -> {
            //skapar en ny runnable som man använder för att skapa en ny tråd
            //När man använder Platform.runLater () i runnable så skickar man den kodsträngen för att köras i "huvudtråden"
            //Man använder runLater när man vill uppdatera kartan/gui


            while (gameIsRunning) {
                Random random = new Random();
                randomTime = random.nextInt(6001);

                //client
                //börja gissa alltså skicka iväg data
//-3
                if (firstGuess && !server) {
                    outgoingMessage = "i " + "shoot " + sendFirstGuess();  //säger vad som ska finnas i outgoingMessage
                    writer.println(outgoingMessage);        //sätter in outgoinmessage i vår writer och skickar iväg meddelandet
                    System.out.println("my first message is " + outgoingMessage);
                    oldGuess = outgoingMessage; //anger old guess för att spara koordinaterna från gissningen
                    battelBoard.appendToConsole("I guess " + sendFirstGuess());
                    firstGuess = false;
                } else {
                    try {
                        if (reader.ready()) {
                            System.out.println("**************************************************************************************************");
//-4

                            incomingmessage = reader.readLine(); //använder vår reader för att ta emot outgoingmessage som motståndaren skickade iväg med sin writer
                            if (incomingmessage.equals("game over")) {
                                System.out.println("Yaaay, jag vann");
                                battelBoard.appendToConsole("You have won! Congratulations!");
                                break;
                            }

//"h shoot 98"
                            //om det är första gissningen och man är server har man ingen gammal gissning man kommer ihåg
                            //annars så tar man oldguess och delar upp till x & y som är ens förra gissnings koordinater som man i den här rundan får svar på om h/m

                            if (incomingmessage.startsWith("i")) {

                            } else {
                                oldGuessList = oldGuess.split("");
                                oldX = Integer.parseInt(oldGuessList[8]);
                                oldY = Letters.valueOf((oldGuessList[9])).ordinal();
                            }


                            if (incomingmessage.startsWith("i")) {

                            } else if (incomingmessage.startsWith("h")) {
                                battelBoard.appendToConsole("And I hit ");
                            } else if (incomingmessage.startsWith("m")) {
                                battelBoard.appendToConsole("And I missed ");
                            } else if (incomingmessage.startsWith("s")) {
                                battelBoard.appendToConsole("And I sunk the ship ");
                            } else {
                                battelBoard.appendToConsole("And I sunk your last ship and won");

                            }


                            //Skjuter på vår förra gissning beroende på svaret vi fick om h/m/s
                            // använder cannonBallHit & uppdaterar spelbräde
                            //cannonBallHitStatusUpdate för att uppdatera och spara infon från skotten
//-5a)
                            if (incomingmessage.startsWith("m")) {
                                Platform.runLater(cannon.cannonBallHit(battelBoard.rectangleCellsEnemy, oldX, oldY, false, false));
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    System.out.println("Could not pause due to:\n" + e.getMessage());
                                }
                                cannon.cannonBallHitStatusUpdate(battelBoard.rectangleCellsEnemy, oldX, oldY, false, false);

                            } else if (incomingmessage.startsWith("h")) {
                                skjuterPåAktivtSkepp = true;
                                Platform.runLater(cannon.cannonBallHit(battelBoard.rectangleCellsEnemy, oldX, oldY, true, false));
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    System.out.println("Could not pause due to:\n" + e.getMessage());
                                }
                                cannon.cannonBallHitStatusUpdate(battelBoard.rectangleCellsEnemy, oldX, oldY, true, false);

                            } else if (incomingmessage.startsWith("s")) {//
                                Platform.runLater(cannon.cannonBallHit(battelBoard.rectangleCellsEnemy, oldX, oldY, true, true));
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    System.out.println("Could not pause due to:\n" + e.getMessage());
                                }
                                cannon.cannonBallHitStatusUpdate(battelBoard.rectangleCellsEnemy, oldX, oldY, true, true);
                                skjuterPåAktivtSkepp = false;

                            } else {//om första gissningen har vi inga gamla koordinater att skjuta på än
                                System.out.println("first guess still");
                            }

                            if (skjuterPåAktivtSkepp) {
                                cannon.handleFollowUpResult(battelBoard.rectangleCellsEnemy, oldX, oldY);
                            }

                            System.out.println("du sa och gissade: " + incomingmessage);

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                System.out.println("Could not pause due to:\n" + e.getMessage());
                            }


                            //ska få fram svar om hm på nya gissningen och uppdatera kartan för det
//-5b)
                            answerHitMiss = cannon.cannonBallAnswer(battelBoard, battelBoard.rectangleCells, xNewGuess(), yNewGuess()); //använder cannonballAnswer för att returnera om h/m
//-5c)
                            Platform.runLater(cannon.cannonBallUpdateMap(battelBoard.rectangleCells, xNewGuess(), yNewGuess())); //använder cannonballUpdateMap för att uppdatera vårt spelbräde


//-6
                            //skicka tillbaka meddelande om hit/miss plus ny gissning
                            outgoingMessage = sendGuess();  //ger nytt värde på outgoing message ex "h shoot 11", alltså du träffade på din gissning och min nya gissning är 11
                            writer.println(outgoingMessage);                //använder writer för att skicka iväg outgoingmessage till motståndaren som får det som använder reader för att få fram meddelandet
                            System.out.println("jag säger till dig " + outgoingMessage);
                            oldGuess = outgoingMessage; //ger nytt värde till oldGuess så vi kommer ihåg koordinaterna vi gissade på nu i nästa "varv"
                            if (!outgoingMessage.equals("game over")) {
                                battelBoard.appendToConsole("I guess " + guessIndexSplit[0] + guessIndexSplit[1]);
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }


        };
//2b)
        //slut på vår Runnable warLoop
        //skapar en ny tråd med warloop som "metod" och startar igång den nya tråden
        Thread updateMap = new Thread(warLoop);
        updateMap.start();
    }


    public String sendGuess() {
        String guess;

        if (answerHitMiss.equals("game over")) {
            guess = answerHitMiss;
            System.out.println("jag förlorade");
            battelBoard.appendToConsole("Oh no, I lost");
            gameIsRunning = false;
        } else {
            // String []guessIndexSplit=cannon.randomShot(battelBoard.rectangleCellsEnemy).split(""); //delar upp xy
            guessIndexSplit = cannon.randomShot(battelBoard.rectangleCellsEnemy).split(""); //delar upp xy
            String indexYAsLetter = Letters.intToLetter(Integer.parseInt(guessIndexSplit[1]));//omvandlar y till bokstav
            guess = answerHitMiss + "shoot " + guessIndexSplit[0] + indexYAsLetter;
        }
        return guess;
    }

    public String sendFirstGuess() {
        String firstGuess;
        String[] guessIndexSplit = cannon.randomShot(battelBoard.rectangleCellsEnemy).split(""); //delar upp xy
        String indexYAsLetter = Letters.intToLetter(Integer.parseInt(guessIndexSplit[1]));//omvandlar y till bokstav
        firstGuess = guessIndexSplit[0] + indexYAsLetter;

        return firstGuess;
    }


    public int xNewGuess() {
        //ex String incomingmessage = "h shoot 09";
        //Splittar meddelandet och tar index 8 för att få x
        String[] incomingMessageList = incomingmessage.split("");
        int newX = Integer.parseInt(incomingMessageList[8]);
        return newX;
    }


    public int yNewGuess() {
        // ex String incomingmessage = "h shoot 09";
        //Splittar meddelandet och tar index 9 för att få y
        String[] incomingMessageList = incomingmessage.split("");
        int newY = Letters.valueOf((incomingMessageList[9])).ordinal();

        return newY;
    }
}

