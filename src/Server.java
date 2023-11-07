//import javafx.stage.Stage;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Random;
//import java.util.Scanner;
//
//public class Server extends Thread {
//
//    BufferedReader reader;
//    PrintWriter writer;
//    private boolean gameIsRunning=true;
//StartingBoard startingBoard= new StartingBoard();
//
//
//    @Override
//    public void run() {
//        startGame();
//    }
//
//
//
//    public void boardStart() throws Exception {
//        startingBoard=new StartingBoard();
//        startingBoard.start(new Stage());
//    }
//
//
//
//    public void startGame() {
//        try {
//            //skapa connection mellan client och server
//            ServerSocket serverSocket = new ServerSocket(8080);
//            System.out.println("Waiting for client");
//            Socket socket = serverSocket.accept();
//            System.out.println("Client conected");
//
//
//            //för att ta emot och hantera data vi får från servern
//            InputStream input = socket.getInputStream();
//            reader = new BufferedReader(new InputStreamReader(input));
//
//            //för att ta hand om data vi vill skicka vidare till Servern
//            OutputStream output = socket.getOutputStream();
//            writer = new PrintWriter(output, true);
//
//            System.out.println("eller här");
//
//            while (gameIsRunning
////                    &&startingBoard.getStartGame()
//            ){
//                System.out.println("hur långt har vi kommit?");
//                if (startingBoard.getServer()){
//                    System.out.println("You are the server and will start the game");
//                    gameIsRunning=false;
//                }
//
//                else{
//                    System.out.println("You are still in the server but choose client");
//                    //Börja ta emot ginning från clienten
//                    //hantera den
//
//
//                    //skicka tillbaka meddelande om miss/träff
//
//
//
//
//
//
//
//
//
//                    gameIsRunning=false;
//                }
//
//            }
//
//
//
//
//
//
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//}