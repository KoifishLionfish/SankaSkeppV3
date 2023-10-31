import java.util.Random;

public class Ship {
  private String shipName;
  private int shipSize;

  static void placeRandomShips(RectangleCell[][] rectangles, int numberOfShips, int shipSize) {

    Random random = new Random();

    for (int nrOfShip = 0; nrOfShip < numberOfShips; nrOfShip++) {
      boolean directionRandom = random.nextBoolean();
      // directionRandom om true så ligger skeppen horisontellt

      int rowRandom;
      int colRandom;

      //avgränsar hur långt randomInt kan gå så att skeppen inte
      //riskeras att placeras utan för spelbrädet
      if (directionRandom) {
        rowRandom = random.nextInt(10);
        colRandom = random.nextInt(10 - shipSize);
      } else {
        rowRandom = random.nextInt(10 - shipSize);
        colRandom = random.nextInt(10);
      }

      //Ökar row/col för att placera ut nästa del av skeppet
      for (int i = 0; i < shipSize; i++) {
        if (directionRandom) {
          rowRandom = rowRandom;
          colRandom = colRandom + 1;
        } else {
          colRandom = colRandom;
          rowRandom = rowRandom + 1;
        }
        rectangles[rowRandom][colRandom].setisShip(true);
        System.out.println("Ship placed at: "+rectangles[rowRandom][colRandom].getRectangleId());
      }

    }
  }


  public String getShipName() {
    if (shipSize == 5) {
      shipName = "Hangarfartyg";
    } else if (shipSize == 4) {
      shipName = "Slagskepp";
    } else if (shipSize == 3) {
      shipName = "Kryssare";
    } else {
      shipName = "Ubåt";
    }
    return shipName;
  }

  public void setShipName(String shipName) {
    this.shipName = shipName;
  }

  public int getShipSize() {
    return shipSize;
  }

  public void setShipSize(int shipSize) {
    this.shipSize = shipSize;
  }
}


//Det här är koden för att placera ut skepp innan vi hade egen klass för rektanglar och innan jag
//la in att skepp hamnar åt random håll
//public static Rectangle[][] placeRandomShips(Rectangle[][] rectangles, int numberOfShips, int shipSize) {
//    Random random = new Random();
//    int gridSize = rectangles.length;
//
//    for (int ship = 0; ship < numberOfShips; ship++) {
//        int row = random.nextInt(gridSize);
//        int col = random.nextInt(gridSize);
//
//        boolean canPlaceShip = true;
//
//        for (int i = 0; i < shipSize; i++) {
//            if (col + i >= gridSize || rectangles[row][col + i].getFill() == Color.ORANGE) {
//                canPlaceShip = false;
//                break;
//            }
//        }
//
//        if (canPlaceShip) {
//            for (int i = 0; i < shipSize; i++) {
//                rectangles[row][col + i].setFill(Color.ORANGE);
//            }
//        } else {
//            ship--;
//        }
//    }
//    return rectangles;
//}
//}

