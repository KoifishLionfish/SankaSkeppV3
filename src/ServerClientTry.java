//import javafx.stage.Stage;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//
//public class ServerClientTry extends Thread {
//
//    BufferedReader reader;
//    PrintWriter writer;
//    private boolean gameIsRunning = true;
//    private int startConnection;
//    StartingBoard startingBoard;
//
//    private String incomingmessage;
//    private String outgoingMessage;
//    private boolean server;
//
//    private Cannon cannon = new Cannon();
//    private Board myBoard = new Board();
//
//    private String titel;
//    private boolean firstGuess = true;
//
//    public ServerClientTry(boolean server) {
//        this.server = server;
//    }
//
//    @Override
//    public void run() {
//        start();
//    }
//
//    public void start() {
//        this.titel = titel;
//        if (server) {
//            titel = "Server";
//        } else {
//            titel = "Client";
//        }
//
//
////startar upp ett bräde
//// (ev lägga till så man ändrar viss text på brädet om server/klient)
//        Board battelBoard = new Board();
//        try {
//            battelBoard.startBoard(new Stage(), titel);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//
//        //Startar upp server/client beroende på val
//        try {
//            if (!server) {
//                //CLIENT**********************************************************************
//                //Skapa connection mellan klient och server
//
//                Socket socket = new Socket("localhost", 8080);
//                System.out.println("Connected to server");
//                //för att ta emot och hantera data vi får från servern
//                InputStream inputStream = socket.getInputStream();
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                //för att ta hand om data vi vill skicka vidare till Servern
//                OutputStream outputStream = socket.getOutputStream();
//                writer = new PrintWriter(outputStream, true);
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
//            }
//        } catch (Exception e) {
//            e.getMessage();
//        }
//
//
//        while (gameIsRunning) {
//
//            if (firstGuess) {
//                //client
//                //börja gissa alltså skicka iväg data
//                outgoingMessage = cannon.shootID(battelBoard.rectangleCells);
//                writer.println(outgoingMessage);
//                //  System.out.println("Jag gissar " + outgoingMessage);
//                firstGuess = false;
//            } else {
//
//                    if (reader.ready()) {
//
//                        //Vänta på/ta emot svar om miss/träff
//                        incomingmessage = reader.readLine();
//                        System.out.println(incomingmessage);
//                        //om träff skjut igen ha kvar hit som true
//                        //skicka tillbaka svar
//
//                        //om miss vänta på svar från servern och
//                        // sätt hit till false
//
//                    }
//
//                }
//            }
//
//        }
//
//
//
//        while (gameIsRunning) {
//            //server
//            //börjar med att vänta på gissning från clienten
//            //hantera den
//            if (reader.ready()) {
//                incomingmessage = reader.readLine();
//                System.out.println("Clienten gissade: " + incomingmessage);
//
//                //skicka tillbaka meddelande om miss/träff
//                outgoingMessage = checkResultAndCreateReply(incomingmessage);
//                writer.println(outgoingMessage);
//
//
//                if (outgoingMessage.contains("Miss")) {
//                    //Om miss såsgissar vi
//
//                    outgoingMessage = cannon.shootID(battelBoard.rectangleCells);
//                    writer.println(outgoingMessage);
//                    //System.out.println("Jag gissar: " + outgoingMessage);
//                    //funkar inte att skriva ut saker????
//                    // System.out.println("jag gissar");
//                } else {
//                    incomingmessage = reader.readLine();
//                    System.out.println("Klienten gissar " + incomingmessage);
//
//
//            }
//        }
//    }
//
//
//
//
//public String checkResultAndCreateReply(String incomingmessage){
//        if(incomingmessage.contains("Miss")){
//        return"Miss";
//        }else
//        return"Hit";
//        }
//        }