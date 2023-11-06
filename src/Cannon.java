import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cannon {
  private Direction previousDirection;
  private List<Integer> initialHitX = new ArrayList<>();
  private List<Integer> initialHitY = new ArrayList<>();
  private int shipLengthLeft = 0;
  private int shipLengthRight = 0;
  private int shipLengthUp = 0;
  private int shipLengthDown = 0;
  private int shipLength = 0;
  private int numberOfSunkenShips = 0;
  private int nrOfHits = 0;
  private String currentBattleShip = "";

  //++ Fredrik L
  public void shoot(RectangleCell[][] rectangles) {
    Random random = new Random();
    int x = random.nextInt(10);
    int y = random.nextInt(10);
    RectangleCell cell = rectangles[x][y];

    // if shot misses
    if (isCellBlue(rectangles, x, y)) {
      makeCellBlack(rectangles, x, y);
      System.out.println("Shot missed at: " + cell.getRectangleId());
    }
    // if shot hits
    else if (isCellOrange(rectangles, x, y)) {
      makeCellRed(rectangles, x, y);
      System.out.println("Shot hit at: " + cell.getRectangleId());
      aimRandomDirection(rectangles, x, y); // choose random direction
    }
    // if shot hits a previously hit rectangle, retry
    else if (isCellRed(rectangles, x, y) || isCellBlack(rectangles, x, y)) {
      shoot(rectangles);
    }
  }

  // THIS METHOD IS FOR TESTING ONLY
  public void forceHit(RectangleCell[][] rectangles) {


    // if no trackrecord exist
    if (initialHitX.isEmpty() && initialHitY.isEmpty()) {

      Random random = new Random();
      int x = random.nextInt(10);
      int y = random.nextInt(10);
      RectangleCell cell = rectangles[x][y];

      // FORCE HIT - THIS IS FOR TESTING ONLY
      if (!isCellOrange(rectangles, x, y)) {
        forceHit(rectangles);
      } else {

        // if shot misses
        if (isCellBlue(rectangles, x, y)) {
          makeCellBlack(rectangles, x, y);
          System.out.println("Shot missed at: " + cell.getRectangleId());
        }
        // if shot hits
        else if (isCellOrange(rectangles, x, y)) {

          // scan shiplength && vertical or horizontal
          scanShipLength(rectangles, x, y);
          shipLength++;

          // Identifier
          identifyShip();

          makeCellRed(rectangles, x, y);
          nrOfHits++;

          // add shot to list
          initialHitX.add(x);
          initialHitY.add(y);

          System.out.println("Shot hit at: " + cell.getRectangleId());
          aimRandomDirection(rectangles, x, y); // choose random direction
        }
        // if shot hits a previously hit rectangle, retry
        else if (isCellRed(rectangles, x, y) || isCellBlack(rectangles, x, y)) {
          shoot(rectangles);
        }
      }
    } else {
      System.out.println("secondary");
      int x = initialHitX.get(0);
      int y = initialHitY.get(0);

      if (isCellRed(rectangles, x, y)) {
        //scanShipLength(rectangles, x, y);
        //shipLength++;

        //identifyShip();

        System.out.println("its red");
        System.out.println(previousDirection);

      }




    }
  }

  // identifier
  public void identifyShip() {
    if (shipLength == 5) {
      System.out.println("Type of Ship: Hangarskepp, 5 long");
      currentBattleShip = "Hangarskepp";
    } else if (shipLength == 4) {
      System.out.println("Type of Ship: Slagskepp, 4 long");
      currentBattleShip = "Slagskepp";
    } else if (shipLength == 3) {
      System.out.println("Type of Ship: Kryssare, 3 long");
      currentBattleShip = "Kryssare";
    } else if (shipLength == 2) {
      System.out.println("Type of Ship: Ubåt, 2 long");
      currentBattleShip = "Ubåt";
    }
  }

  // the scanner
  public void scanShipLength(RectangleCell[][] rectangles, int x, int y) {

    scanLeft(rectangles, x, y);
    scanRight(rectangles, x, y);
    scanDown(rectangles, x, y);
    scanUp(rectangles, x, y);
  }

  public void scanLeft(RectangleCell[][] rectangles, int x, int y) {
    RectangleCell cell = rectangles[x][y];

    // going left until it hits water
    for (int scan = 0; scan < 1; scan++) {
      if (x - 1 > -1) {
        x--;
        if (isCellOrange(rectangles, x, y)) {
          shipLength++;
          scan--;
        } else {
          break;
        }
      } else {
        break;
      }
    }
  }

  public void scanRight(RectangleCell[][] rectangles, int x, int y) {
    RectangleCell cell = rectangles[x][y];

    // going right until it hits water or black
    for (int scan = 0; scan < 1; scan++) {
      if (x + 1 < 10) {
        x++;
        if (isCellOrange(rectangles, x, y)) {
          shipLength++;
          scan--;
        } else {
          break;
        }
      } else {
        break;
      }
    }
  }

  public void scanUp(RectangleCell[][] rectangles, int x, int y) {
    RectangleCell cell = rectangles[x][y];

    // going up until it hits water, black or side
    for (int scan = 0; scan < 1; scan++) {
      if (y - 1 > -1) {
        y--;
        if (isCellOrange(rectangles, x, y)) {
          shipLength++;
          scan--;
        } else {
          break;
        }
      } else {
        break;
      }
    }
  }

  public void scanDown(RectangleCell[][] rectangles, int x, int y) {
    RectangleCell cell = rectangles[x][y];

    // going down until it hits water, black or side
    for (int scan = 0; scan < 1; scan++) {
      if (y + 1 < 10) {
        y++;
        if (isCellOrange(rectangles, x, y)) {
          shipLength++;
          scan--;
        } else {
          break;
        }
      } else {
        break;
      }
    }
  }

   //follow-up shots if the first hit is successful
  public void followUpShot(RectangleCell[][] rectangles, int x, int y, Direction previousDirection) {
    RectangleCell cell = rectangles[x][y];

    // if it hits, go in the same direction again
    if (isCellBlue(rectangles, x, y)) {
      makeCellBlack(rectangles, x, y);
      System.out.println("Shot missed at: " + cell.getRectangleId());

      if (nrOfHits == shipLength) {
        initialHitY.remove(0);
        initialHitX.remove(0);
        System.out.println("You sunk my Battleship: " + currentBattleShip);
        System.out.println("numberOfHits: " + nrOfHits);
        nrOfHits = 0;
        forceHit(rectangles);
      } else {
        System.out.println(nrOfHits);
        forceHit(rectangles);
      }

      if (previousDirection == Direction.RIGHT && nrOfHits > 1 && shipLength > 0) {
        previousDirection = Direction.LEFT;
        System.out.println(previousDirection);
      } else if (previousDirection == Direction.LEFT && nrOfHits > 1 && shipLength > 0) {
        previousDirection = Direction.RIGHT;
        System.out.println(previousDirection);
      } else if (previousDirection == Direction.DOWN && nrOfHits > 1 && shipLength > 0) {
        previousDirection = Direction.UP;
        System.out.println(previousDirection);
      } else if (previousDirection == Direction.UP && nrOfHits > 1 && shipLength > 0) {
        previousDirection = Direction.DOWN;
        System.out.println(previousDirection);
      }

    } else if (isCellOrange(rectangles, x, y)) {
      makeCellRed(rectangles, x, y);
      System.out.println("Shot hit at: " + cell.getRectangleId());
      nrOfHits++;

      if (nrOfHits == shipLength) {
        forceHit(rectangles);
      }

      if (previousDirection == Direction.UP) {
        aimUp(rectangles, x, y);

      } else if (previousDirection == Direction.RIGHT) {
        aimRight(rectangles, x, y);

      } else if (previousDirection == Direction.DOWN) {
        aimDown(rectangles, x, y);

      } else {
        aimLeft(rectangles, x, y);
      }
    }
  }

  // Choose a random direction
  public void aimRandomDirection(RectangleCell[][] rectangles, int x, int y) {
    Random random = new Random();
    int randomNr = random.nextInt(4) + 1;

    if (randomNr == 1) {
      aimUp(rectangles, x, y);
    } else if (randomNr == 2) {
      aimRight(rectangles, x, y);
    } else if (randomNr == 3) {
      aimDown(rectangles, x, y);
    } else {
      aimLeft(rectangles, x, y);
    }
  }

  // Methods to choose a direction
  public void aimUp(RectangleCell[][] rectangles, int x, int y) {
    if (y - 1 > -1 && !isCellRed(rectangles, x, y - 1) || y - 1 > -1 && !isCellBlack(rectangles, x, y - 1)) {
      y--;
      followUpShot(rectangles, x, y, Direction.UP);
    } else {
      aimRandomDirection(rectangles, x, y);
    }
  }

  public void aimRight(RectangleCell[][] rectangles, int x, int y) {
    if (x + 1 < 10 && !isCellRed(rectangles, x + 1, y) || x + 1 < 10 && !isCellBlack(rectangles, x + 1, y)) {
      x++;
      followUpShot(rectangles, x, y, Direction.RIGHT);
    } else {
      aimRandomDirection(rectangles, x, y);
    }
  }

  public void aimDown(RectangleCell[][] rectangles, int x, int y) {
    if (y + 1 < 10 && !isCellRed(rectangles, x, y + 1) || y + 1 < 10 && !isCellBlack(rectangles, x, y)) {
      y++;
      followUpShot(rectangles, x, y, Direction.DOWN);
    } else {
      aimRandomDirection(rectangles, x, y);
    }
  }

  public void aimLeft(RectangleCell[][] rectangles, int x, int y) {
    if (x - 1 > -1 && !isCellRed(rectangles, x - 1, y) || x - 1 > -1 && !isCellBlack(rectangles, x, y)) {
      x--;
      followUpShot(rectangles, x, y, Direction.LEFT);
    } else {
      aimRandomDirection(rectangles, x, y);
    }
  }

  // Methods to easier make cell black and red
  public void makeCellBlack(RectangleCell[][] rectangles, int x, int y) {
    RectangleCell cell = rectangles[x][y];
    cell.getRectangelCell().setFill(Color.BLACK);
  }

  public void makeCellRed(RectangleCell[][] rectangles, int x, int y) {
    RectangleCell cell = rectangles[x][y];
    cell.getRectangelCell().setFill(Color.RED);
  }

  // Check color of rectangle
  public boolean isCellBlue(RectangleCell[][] rectangles, int x, int y) {
    Rectangle cell = rectangles[x][y].getRectangelCell();
    return cell.getFill() == Color.ROYALBLUE;
  }

  public boolean isCellOrange(RectangleCell[][] rectangles, int x, int y) {
    RectangleCell cell = rectangles[x][y];
    return cell.getIsShip();
  }

  public boolean isCellRed(RectangleCell[][] rectangles, int x, int y) {
    Rectangle cell = rectangles[x][y].getRectangelCell();
    return cell.getFill() == Color.RED;
  }

  public boolean isCellBlack(RectangleCell[][] rectangles, int x, int y) {
    Rectangle cell = rectangles[x][y].getRectangelCell();
    return cell.getFill() == Color.BLACK;
  }

  //-- Fredrik L
}
