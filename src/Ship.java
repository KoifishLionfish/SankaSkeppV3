import java.util.Random;


//Jacob
public class Ship {

  // Metod för att slumpmässigt placera skepp på spelplanen
  static boolean placeRandomShips(RectangleCell[][] rectangles, int[] shipsPerSize) {
    Random random = new Random();
    int[] shipSizes = {5, 4, 4, 3, 3, 3, 2, 2, 2, 2};
    int maxAttempts = 500;



    // För varje skeppsstorlek i skeppstorlekslistan
    for (int index = 0; index < shipSizes.length; index++) {
      int shipSize = shipSizes[index];
      int numberOfShips = (index < shipsPerSize.length) ? shipsPerSize[index] : 0;


      // Placera det angivna antalet skepp av varje storlek
      for (int numOfShips = 0; numOfShips < numberOfShips; numOfShips++) {
        // Placera skepp av givet antal och storlek
        if (!placeShips(rectangles, shipSize, random, maxAttempts)) {
          resetBoard(rectangles);
          System.out.println("Misslyckades med att placera skepp " + (numOfShips + 1) + " av storlek " + shipSize);
          return false;
        }
      }
    }
    return true;
  }


  // Placera skepp på spelplanen med en given storlek och antal försök
  static boolean placeShips(RectangleCell[][] rectangles, int shipSize, Random random, int maxAttempts) {
    int attempts = 0;
    boolean placed = false;


    // Försök placera skeppet tills det är placerat eller maxAntal försök uppnås
    while (!placed && attempts < maxAttempts) {
      boolean directionRandom = random.nextBoolean();
      int rowRandom = random.nextInt(10);
      int colRandom = random.nextInt(10);


      boolean isValidPlacement = false;


      // Placera skeppet i den slumpmässiga riktningen
      if (directionRandom) {
        if (colRandom + shipSize <= 10) {
          isValidPlacement = true;
          for (int i = 0; i < shipSize; i++) {
            if (rectangles[rowRandom][colRandom + i].getIsShip()) {
              isValidPlacement = false;
              break;
            }
          }
          for (int i = Math.max(0, rowRandom - 1); i < Math.min(10, rowRandom + 2); i++) {
            for (int j = Math.max(0, colRandom - 1); j < Math.min(10, colRandom + shipSize + 1); j++) {
              if (rectangles[i][j].getIsShip()) {
                isValidPlacement = false;
                break;
              }
            }
          }
        }
      } else {
        if (rowRandom + shipSize <= 10) {
          isValidPlacement = true;
          for (int i = 0; i < shipSize; i++) {
            if (rectangles[rowRandom + i][colRandom].getIsShip()) {
              isValidPlacement = false;
              break;
            }
          }
          for (int i = Math.max(0, rowRandom - 1); i < Math.min(10, rowRandom + shipSize + 1); i++) {
            for (int j = Math.max(0, colRandom - 1); j < Math.min(10, colRandom + 2); j++) {
              if (rectangles[i][j].getIsShip()) {
                isValidPlacement = false;
                break;
              }
            }
          }
        }
      }


      // Om placeringen är giltig, placera skeppet på spelplanen
      if (isValidPlacement) {
        if (directionRandom) {
          for (int i = 0; i < shipSize; i++) {
            rectangles[rowRandom][colRandom + i].setIsShip(true);
          }
        } else {
          for (int i = 0; i < shipSize; i++) {
            rectangles[rowRandom + i][colRandom].setIsShip(true);
          }
        }
        placed = true;
      }


      attempts++;
      if (attempts >= maxAttempts) {
        System.out.println("Misslyckades med att placera skepp av storlek " + shipSize + " efter " + attempts + " försök.");
      }
    }
    return placed;
  }


  // Återställer spelplanen till dess ursprungliga tillstånd
  private static void resetBoard(RectangleCell[][] rectangles) {
    for (int i = 0; i < rectangles.length; i++) {
      for (int j = 0; j < rectangles[i].length; j++) {
        rectangles[i][j].setIsShip(false);
      }
    }
  }
} // Jacob