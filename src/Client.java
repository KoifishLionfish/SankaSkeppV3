//import javafx.stage.Stage;
//
//import java.io.*;
//import java.net.Socket;
//
//public class Client extends Thread  {
//
//
//
//
//    BufferedReader reader;
//    PrintWriter writer;
//    private boolean gameIsRunning=true;
//    private boolean firstPlayer;
//    StartingBoard startingBoard;
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
//   startingBoard=new StartingBoard();
//   startingBoard.start(new Stage());
//
//    }
//    public void startGame() {
//        try {
//            //Skapa connection mellan klient och server
//            Socket socket = new Socket("localhost", 8080);
//            System.out.println("Connected to server");
//            //för att ta emot och hantera data vi får från servern
//            InputStream inputStream = socket.getInputStream();
//            reader = new BufferedReader(new InputStreamReader(inputStream));
//
//            //för att ta hand om data vi vill skicka vidare till Servern
//            OutputStream outputStream = socket.getOutputStream();
//            writer = new PrintWriter(outputStream, true);
//
//            System.out.println("är vi här");
//            while (gameIsRunning
////                    &&startingBoard.getStartGame()
//            ) {
//                System.out.println("test igen");
//                if (startingBoard.getServer()) {
//
//                    System.out.println("You are client but choose server");
//                    //börja gissa alltså skicka iväg data
//                    String outputSHoot = shot();
//                    System.out.println("fösta spelare client");
//
//
//                  /*while (hit){
//
//                        //Vänta på/ta emot svar om miss/träff
//
//                        //om träff skjut igen ha kvar hit som true
//                        //skicka tillbaka svar
//
//
//
//                        //om miss vänta på svar från servern och
//                        sätt hit till false
//
//                 }*/
//                    gameIsRunning=false;
//
//                } else {
//                    System.out.println("you are client and choose client");
//gameIsRunning=false;
//                }
//
//            }
//
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//    }
//
//    public String shot() {
//
//        return "A2";
//    }
//
//}
