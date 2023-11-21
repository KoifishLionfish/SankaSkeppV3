import javafx.application.Platform;
import javafx.stage.Stage;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;


public class ServerClient implements Runnable {
    private boolean testFörstaGissning = true;


    private BufferedReader reader;
    private PrintWriter writer;
    private boolean gameIsRunning = true;


    private String incomingmessage;


    private String outgoingMessage;
    private boolean server;


    private Cannon cannon = new Cannon();
    private int randomTime;
    private String titel;
    private boolean firstGuess = true;
    private Board battelBoard = new Board();


    private String oldGuess;
    private String[] oldGuessList;
    private int oldX;
    private int oldY;
    int count = 0;


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


       /*
       -1    Skapar server/Klient/Socket
       -2a)  Skapar en runnable (används för att skapa ny tråd)
       -3    Skickar första gissningen
       -4    Tar emot svar om förra gissning h/m och ny gissning (när det är första gissningen tar man inte emot något svar om förra gissning utan bara en ny gissning)
              och dela upp gamla gissningen i x y
       -5a)  Kollar svar om h/m och uppdaterar fienden kartan (den tomma)
       -5b)  Tar nya gissningen och kollar om det är h/m
       -5c)  Uppdaterar myBoard (den med mina skepp utplacerade) utifrån nya gissningen
       -6    Generar nytt svar utifrån 5b) och skickar med en ny gissning
       -2b)    Avslutar runnable och skapar en ny tråd ifrån den
        */




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
                    outgoingMessage = "i " + sendGuess();  //säger vad som ska finnas i outgoingMessage
                    writer.println(outgoingMessage);        //sätter in outgoinmessage i vår writer och skickar iväg meddelandet
                    System.out.println("my first message is " + outgoingMessage);
                    oldGuess = outgoingMessage; //anger old guess för att spara koordinaterna från gissningen
                    firstGuess = false;


                } else {
                    try {
                        if (reader.ready()) {
                            System.out.println("**************************************************************************************************");
//-4
                            incomingmessage = reader.readLine(); //använder vår reader för att ta emot outgoingmessage som motståndaren skickade iväg med sin writer


                            //om det är första gissningen och man är server har man ingen gammal gissning man kommer ihåg
                            //annars så tar man oldguess och delar upp till x & y som är ens förra gissnings koordinater som man i den här rundan får svar på om h/m
                            if (testFörstaGissning && server) {
                                System.out.println("Finns ingen gammal gissning det här är första");
                                testFörstaGissning = false;


                            } else {
                                oldGuessList = oldGuess.split("");
                                oldX = Integer.parseInt(oldGuessList[8]);
                                oldY = Integer.parseInt(oldGuessList[9]);
                                System.out.println("hej hej, min förra gissning var " + oldGuess);
                            }


                            try {
                                Thread.sleep(randomTime);
                            } catch (InterruptedException e) {
                                System.out.println("Could not pause due to:\n" + e.getMessage());
                            }




                            //Skjuter på vår förra gissning beroende på svaret vi fick om h/m
                            // använder cannonBallHit
//-5a)
                            if (incomingmessage.startsWith("m")) {
                                Platform.runLater(cannon.cannonBallHit(battelBoard.rectangleCellsEnemy, oldX, oldY, false));
                                System.out.println("ska skjuta på miss på " + oldX + oldY);


                            } else if (incomingmessage.startsWith("h")) {
                                Platform.runLater(cannon.cannonBallHit(battelBoard.rectangleCellsEnemy, oldX, oldY, true));
                                System.out.println("ska skjuta på hit");
                            } else {//om första gissningen har vi inga gamla koordinater att skjuta på än
                                System.out.println("first guess still");
                            }


                            System.out.println("du sa och gissade: " + incomingmessage);
                            System.out.println("nya xy är: " + xNewGuess() + "" + yNewGuess());




                            //ska få fram svar om hm på nya gissningen och uppdatera kartan för det
//-5b)
                            String answerHitMiss = cannon.cannonBallAnswer(battelBoard.rectangleCells, xNewGuess(), yNewGuess()); //använder cannonballAnswer för att returnera om h/m
//-5c)
                            Platform.runLater(cannon.cannonBallAnswerUpdateMap(battelBoard.rectangleCells, xNewGuess(), yNewGuess())); //använder cannonballUpdateMap för att uppdatera vårt spelbräde
                            System.out.println("Svar om hm: " + answerHitMiss);


//-6
                            //skicka tillbaka meddelande om hit/miss plus ny gissning
                            outgoingMessage = answerHitMiss + sendGuess();  //ger nytt värde på outgoing message ex "h shoot 11", alltså du träffade på din gissning och min nya gissning är 11
                            writer.println(outgoingMessage);                //använder writer för att skicka iväg outgoingmessage till motståndaren som får det som använder reader för att få fram meddelandet
                            System.out.println("jag säger till dig " + outgoingMessage);
                            oldGuess = outgoingMessage; //ger nytt värde till oldGuess så vi kommer ihåg koordinaterna vi gissade på nu i nästa "varv"


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
        //en kort räknare så spelet tar slut någon gång när man testar
        //Nu är det bara randomShoot men här vi behöver jobba
        String guess;
        count++;
        if (count > 5) {
            gameIsRunning = false;
        }
        guess = "shoot " + cannon.randomShotId(battelBoard.rectangleCellsEnemy); //använder randomShotId som jag förenklat en del för att bara få ut random koordinater
        return guess;
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
        int newY = Integer.parseInt(incomingMessageList[9]);
        return newY;
    }
}


