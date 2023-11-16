import java.util.Random;

public class Ship {

  private String shipName;
  private int shipSize;

  static boolean placeRandomShips(RectangleCell[][] rectangles, int[] shipsPerSize) {
    Random random = new Random();
    int[] shipSizes = {5, 4, 4, 3, 3, 3, 2, 2, 2, 2};
    int maxAttempts = 100;

    for (int index = 0; index < shipSizes.length; index++) {
      int shipSize = shipSizes[index];
      int numberOfShips = (index < shipsPerSize.length) ? shipsPerSize[index] : 0;

      for (int numOfShips = 0; numOfShips < numberOfShips; numOfShips++) {
        boolean placed = false;
        int attempts = 0;

        while (!placed && attempts < maxAttempts) {
          boolean directionRandom = random.nextBoolean();
          int rowRandom = 0;
          int colRandom = 0;
          boolean isValidPlacement = false;

          while (!isValidPlacement) {
            if (directionRandom) {
              rowRandom = random.nextInt(10);
              colRandom = random.nextInt(10 - shipSize);
            } else {
              rowRandom = random.nextInt(10 - shipSize);
              colRandom = random.nextInt(10);
            }
            isValidPlacement = true;

            boolean hasAdjacent = false;

            for (int i = rowRandom - 1; i < rowRandom + shipSize + 1; i++) {
              for (int j = colRandom - 1; j < colRandom + shipSize + 1; j++) {
                if (i >= 0 && i < 10 && j >= 0 && j < 10) {
                  if (rectangles[i][j].getIsShip()) {
                    hasAdjacent = true;
                    break;
                  }
                }
              }
              if (hasAdjacent) {
                isValidPlacement = false;
                break;
              }
            }

            if (isValidPlacement) {
              for (int i = 0; i < shipSize; i++) {
                int row = directionRandom ? rowRandom : rowRandom + i;
                int col = directionRandom ? colRandom + i : colRandom;
                rectangles[row][col].setisShip(true);
              }
              placed = true;
            }
          }

          attempts++;
          if (attempts >= maxAttempts) {
            System.out.println("Failed to place ship of size " + shipSize + " after " + attempts + " attempts.");
          }
        }

        if (!placed) {
          resetBoard(rectangles);
          System.out.println("Failed to place ship " + (numOfShips + 1) + " of size " + shipSize);
          return false;
        }
      }

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
    return true;
  }

  private static void resetBoard(RectangleCell[][] rectangles) {
    for (int i = 0; i < rectangles.length; i++) {
      for (int j = 0; j < rectangles[i].length; j++) {
        rectangles[i][j].setisShip(false);
      }
    }
  }
}


// Jacob D


   /* public String getShipName() {
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
    }*/


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
