import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT,

//
//    //************************************************************************
//    public String randomShotId(RectangleCell[][] rectangles) {
//        // Förenklat randomShot för att bara få random koordinater
//        Random randomHit = new Random();
//        int x = randomHit.nextInt(10);
//        int y = randomHit.nextInt(10);
//
//        if (isCellBlack(rectangles, x, y)) {
//            randomShotId(rectangles);
//        }
//        return (x) + "" + (y);
//    }
//
//
//
//
//    //  Är i princip tre metoder ifrån cannonBall. En som kollar på "myBoard" och ser om gissningen var miss/träff och returnerar det.
//    //    En som uppdaterar "myBoard" med gissningen och en som uppdaterar enemyBoard utrifån svaret som ges ifrån första metoden
//    public Runnable cannonBallHit(RectangleCell[][] rectangles, int x, int y, boolean hit) {
//        //uppdaterar kartan beroende på svar om h/m på enemyBoard
//
//        Runnable runnableToReturn = new Runnable() {
//            @Override
//            public void run() {
//                Rectangle cell = rectangles[x][y].getRectangelCell();
//                String cellId = rectangles[x][y].getRectangleId();
//                if (hit) {
//                    //  board.setTextLabel("Hej");
//
//                    // previousHit=true;
//                    cell.setFill(Color.RED);
//                } else {
//                    // board.setTextLabel("Miss");
//                    //previousHit=false;
//                    cell.setFill(Color.BLACK);
//                }
//            }
//        };
//        return runnableToReturn;
//    }
//
//    public String cannonBallAnswer(RectangleCell[][] rectangles, int x, int y) {
//        //Ger bara svar om hit/miss men uppdaterar inte kartan på my board
//        Rectangle cell = rectangles[x][y].getRectangelCell();
//        String cellId = rectangles[x][y].getRectangleId();
//        String answer;
//        if (isAShip(rectangles, x, y)) {
//            // cell.setFill(Color.RED);
//            // answer = "h " + (x) + "," + (y);
//            answer = "h ";
//
//
//        } else {
//            //  cell.setFill(Color.BLACK);
//
//            // answer = "m " + (x) + "," + (y);
//            answer = "m ";
//        }
//        return answer;
//    }
//
//    public Runnable cannonBallAnswerUpdateMap(RectangleCell[][] rectangles, int x, int y) {
//        //Uppdaterar myBoard
//        Runnable runnableToReturn = new Runnable() {
//            @Override
//            public void run() {
//                Rectangle cell = rectangles[x][y].getRectangelCell();
////        String cellId = rectangles[x][y].getRectangleId();
////        String answer;
//                if (isAShip(rectangles, x, y)) {
//                    cell.setFill(Color.RED);
//
//                    //   answerHMWithCoordinates="h "+(x)+","+(y);
//                    // answerHMWithCoordinates="h ";
//                    //+(x)+(y);
//                } else {
//                    cell.setFill(Color.BLACK);
//                    //System.out.println("Shot missed at: " + cellId);
//                    //  answerHMWithCoordinates ="m "+(x)+","+(y);
//                    // answerHMWithCoordinates ="m ";
//                    //+(x)+","+(y);
//                }
//
//            }
//        };
//
//        return runnableToReturn;
//    }





    }

