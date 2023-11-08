import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//++ Fredrik L
public class Cannon {
  private final List<Integer> initialHitX = new ArrayList<>();
  private final List<Integer> initialHitY = new ArrayList<>();
  private String currentShip = "";
  private int shipLength = 0;
  private int numberOfSunkenShips = 0;
  private int nrOfHits = 0;
  private final int NUMBER_OF_SHIPS = 6;


  // random shot
  public void randomShot(RectangleCell[][] rectangles) {
    // Random number generator
    Random randomHit = new Random();
    int x = randomHit.nextInt(10);
    int y = randomHit.nextInt(10);

    // if no ship has been hit or sunk
    if (initialHitX.isEmpty() && initialHitY.isEmpty()) {
      // if rectangle isnt black and isnt hit ship (RED)
      if (!isCellBlack(rectangles, x, y) && !isHit(rectangles, x, y)) { //
        // shoots cannonball
        cannonBall(rectangles, x, y); // if miss turn black if hits turn red.

        if (isCellBlack(rectangles, x, y)) {
          randomShot(rectangles); // if first hit is miss - shoot again
        } else if (isHit(rectangles, x, y)) {
          nrOfHits++; // register hit

          // save coords
          initialHitX.add(x);
          initialHitY.add(y);


          shipLength++; // add rectangle to length of ship


          scanShipLength(rectangles, x, y); // scan ship for knowing when its broken later
          typeOfShip(); // writes what type of ship it is to check for errors

          aimRandomDirection(rectangles, x, y); // aim random direction -> direction -> followUpShot
        }
      } else {
        randomShot(rectangles); // if cell is black or red(hit), shoot again
      }
    } else { // if battleship isnt sunk
      // if list is not empty go back to initial shot:
      x = initialHitX.get(0);
      y = initialHitY.get(0);

      aimRandomDirection(rectangles, x, y); // aim random direction -> direction -> followUpShot
    }
  }

  // follow up shots
  public void followUpShot(RectangleCell[][] rectangles, int x, int y, Direction direction) {

    for (int tries = 0; tries < 1; tries++) { // direction up

      if ((shipLength - nrOfHits) == 0) {
        System.out.println("You sunk my: " + currentShip);
        numberOfSunkenShips++;

        System.out.println("You have sunken a total of: " + numberOfSunkenShips + "/" + NUMBER_OF_SHIPS + " Ships");

        if (numberOfSunkenShips == NUMBER_OF_SHIPS) {
          gameOver(); // if game is won run method and after
          break; // break
        }

        // resetting
        initialHitX.remove(0);
        initialHitY.remove(0);
        shipLength = 0;
        nrOfHits = 0;

        randomShot(rectangles); // shoot again
      } else {

        if (direction == Direction.UP) {
          if (y > 0 && y <= 9) { // if y is 1++
            y--; // go up once
            if (isCellBlack(rectangles, x, y)) { // check if rectrangle is black.
              randomShot(rectangles); // if its black, shoot again.
            } else { // if its not black
              cannonBall(rectangles, x, y); // shoot cannonball
              if (isHit(rectangles, x, y)) { // if it hits a ship
                nrOfHits++; // add hit
                tries--; // get another try
              } else { // if it misses
                randomShot(rectangles); // start over
              }
            }

          } else if (y == 0) { // if y = 0 (upper border)
            if (isCellBlack(rectangles, x, y)) { // check if rectangle is black
              randomShot(rectangles); // if its black, shoot again
            } else { // if its not black
              cannonBall(rectangles, x, y); // shoot cannonball
              if (isHit(rectangles, x, y)) { // if it hits a ship
                nrOfHits++; // register hit
                randomShot(rectangles); // go back to another shot since its at the top
              } else { // if it doesnt hit
                randomShot(rectangles); // change direction from initial hit
              }
            }
          }

        } else if (direction == Direction.DOWN) { // direction down
          if (y >= 0 && y < 9) { // if y 8--
            y++; // go down once
            if (isCellBlack(rectangles, x, y)) { // check if rectangle is black
              randomShot(rectangles); // if its black, shoot again
            } else { // if its not black
              cannonBall(rectangles, x, y); // shoot cannonball
              if (isHit(rectangles, x, y)) { // if it hits a ship
                nrOfHits++; // register hit
                tries--; // get another try
              } else { // if it misses
                randomShot(rectangles); // start over
              }
            }

          } else if (y == 9) { // if y == 9 (lower border)
            if (isCellBlack(rectangles, x, y)) { // check if rectangle is black
              randomShot(rectangles); // if its black, shoot again
            } else { // if its not black
              cannonBall(rectangles, x, y); // shoot cannonball
              if (isHit(rectangles, x, y)) { // if it hits a ship
                nrOfHits++; // register hit
                randomShot(rectangles); // go back to another shot since its at the top
              } else { // if it doesnt hit
                randomShot(rectangles); // change direction from initial hit
              }
            }
          }

        } else if (direction == Direction.LEFT) { // direction LEFT
          if (x > 0 && x <= 9) { // if x is 1++
            x--; // go left once
            if (isCellBlack(rectangles, x, y)) { // check if rectangle is black
              randomShot(rectangles); // if its black, shoot again.
            } else { // if its not black
              cannonBall(rectangles, x, y); // shoot cannonball
              if (isHit(rectangles, x, y)) { // if it hits a ship
                nrOfHits++; // register hit
                tries--; // get another try
              } else { // if it misses
                randomShot(rectangles); // start over
              }
            }

          } else if (x == 0) { // if x = 0 (left border)
            if (isCellBlack(rectangles, x, y)) { // check if rectangle is black
              randomShot(rectangles); // if its black, shoot again
            } else { // if its not black
              cannonBall(rectangles, x, y); // shoot cannonball
              if (isHit(rectangles, x, y)) { // if it hits a ship
                nrOfHits++; // register hit
                randomShot(rectangles); // go back to another shot since its at the left border
              } else { // if it doesnt hit
                randomShot(rectangles); // change direction from initial hit
              }
            }
          }


        } else if (direction == Direction.RIGHT) { // direction RIGHT
          if (x >= 0 && x < 9) { // if x 8--
            x++;
            if (isCellBlack(rectangles, x, y)) { // check if rectangle is black
              randomShot(rectangles); // if its black, shoot again
            } else { // if its not black
              cannonBall(rectangles, x, y); // shoot cannonball
              if (isHit(rectangles, x, y)) { // if it hits a ship
                nrOfHits++; // register hit
                tries--; // get another try
              } else { // if it misses
                randomShot(rectangles); // start over
              }
            }

          } else if (x == 9) { // if y == 9 (right border)
            if (isCellBlack(rectangles, x, y)) { // check if rectangle is black
              randomShot(rectangles); // if its black, shoot again
            } else { // if its not black
              cannonBall(rectangles, x, y); // shoot cannonball
              if (isHit(rectangles, x, y)) { // if it hits a ship
                nrOfHits++; // register hit
                randomShot(rectangles); // go back to another shot since its at the right border
              } else { // if it doesnt hit
                randomShot(rectangles); // change direction from initial hit
              }
            }
          }
        }
      }
    }
  }

  // Game over
  public void gameOver() {
    System.out.println("Congratulations, you have won!");
  }

  // type of ship
  public void typeOfShip() {
    if (shipLength == 5) {
      currentShip = "Carrier";
    } else if (shipLength == 4) {
      currentShip = "Battleship";
    } else if (shipLength == 3) {
      currentShip = "Cruiser";
    } else if (shipLength == 2) {
      currentShip = "Destroyer";
    }
  }

  // the scanner
  public void scanShipLength(RectangleCell[][] rectangles, int x, int y) {

    // scanning all directions
    scanLeft(rectangles, x, y);
    scanRight(rectangles, x, y);
    scanDown(rectangles, x, y);
    scanUp(rectangles, x, y);
  }

  public void scanLeft(RectangleCell[][] rectangles, int x, int y) {

    // going left until it hits water
    for (int scan = 0; scan < 1; scan++) {
      if (x - 1 > -1) {
        x--;
        if (isAShip(rectangles, x, y)) {
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
    // going right until it hits water or black
    for (int scan = 0; scan < 1; scan++) {
      if (x + 1 < 10) {
        x++;
        if (isAShip(rectangles, x, y)) {
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
    // going up until it hits water, black or side
    for (int scan = 0; scan < 1; scan++) {
      if (y - 1 > -1) {
        y--;
        if (isAShip(rectangles, x, y)) {
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
    // going down until it hits water, black or side
    for (int scan = 0; scan < 1; scan++) {
      if (y + 1 < 10) {
        y++;
        if (isAShip(rectangles, x, y)) {
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

  //Choose a random direction
  public void aimRandomDirection(RectangleCell[][] rectangles, int x, int y) {

    Random random = new Random();
    int randomNr;

    // upper left corner
    if (x == 0 && y == 0) {
      randomNr = random.nextInt(2) + 1;

      if (randomNr == 1) {
        aimRight(rectangles, x, y);
      } else {
        aimDown(rectangles, x, y);
      }
    }
    // upper right corner
    else if (x == 9 && y == 0) {
      randomNr = random.nextInt(2) + 1;

      if (randomNr == 1) {
        aimLeft(rectangles, x, y);
      } else {
        aimDown(rectangles, x, y);
      }
    }
    // lower right corner
    else if (x == 9 && y == 9) {
      randomNr = random.nextInt(2) + 1;

      if (randomNr == 1) {
        aimUp(rectangles, x, y);
      } else {
        aimLeft(rectangles, x, y);
      }
    }
    // lower left corner
    else if (x == 0 && y == 9) {
      randomNr = random.nextInt(2) + 1;

      if (randomNr == 1) {
        aimUp(rectangles, x, y);
      } else {
        aimRight(rectangles, x, y);
      }
    }
    // upper middle border
    else if (x > 0 && x < 9 && y == 0) {
      randomNr = random.nextInt(3) + 1;

      if (randomNr == 1) {
        aimLeft(rectangles, x, y);
      } else if (randomNr == 2) {
        aimDown(rectangles, x, y);
      } else {
        aimRight(rectangles, x, y);
      }
    }
    // right middle border
    else if (x == 9 && y > 0 && y < 9) {
      randomNr = random.nextInt(3) + 1;

      if (randomNr == 1) {
        aimUp(rectangles, x, y);
      } else if (randomNr == 2) {
        aimLeft(rectangles, x, y);
      } else {
        aimDown(rectangles, x, y);
      }
    }
    // bottom middle border
    else if (x > 0 && x < 9 && y == 9) {
      randomNr = random.nextInt(3) + 1;

      if (randomNr == 1) {
        aimLeft(rectangles, x, y);
      } else if (randomNr == 2) {
        aimUp(rectangles, x, y);
      } else {
        aimRight(rectangles, x, y);
      }
    }
    // left middle border
    else if (x == 0 && y > 0 && y < 9) {
      randomNr = random.nextInt(3) + 1;

      if (randomNr == 1) {
        aimUp(rectangles, x, y);
      } else if (randomNr == 2) {
        aimRight(rectangles, x, y);
      } else {
        aimDown(rectangles, x, y);
      }
    }
    // middle of board
    else if (x > 0 && x < 9 && y > 0 && y < 9) {
      randomNr = random.nextInt(4) + 1;

      if (randomNr == 1) {
        aimRight(rectangles, x, y);
      } else if (randomNr == 2) {
        aimDown(rectangles, x, y);
      } else if (randomNr == 3) {
        aimLeft(rectangles, x, y);
      } else {
        aimUp(rectangles, x, y);
      }
    }
  }

  //Methods to choose a direction
  public void aimUp(RectangleCell[][] rectangles, int x, int y) {
    if (!isHit(rectangles, x, y-1)) { // if rectangle above is not red(hit)
      followUpShot(rectangles, x, y, Direction.UP); // aim up
    } else { // else if rectangle above is red
      aimRandomDirection(rectangles, x, y); // choose new direction
    }
  }

  public void aimRight(RectangleCell[][] rectangles, int x, int y) {
    if (!isHit(rectangles, x+1, y)) { // if rectangle to the right is not red(hit)
      followUpShot(rectangles, x, y, Direction.RIGHT); // aim right
    } else { // else if rectangle to the right is red
      aimRandomDirection(rectangles, x, y); // choose new direction
    }
  }

  public void aimDown(RectangleCell[][] rectangles, int x, int y) {
    if (!isHit(rectangles, x, y+1)) { // if rectangle under is not red(hit)
      followUpShot(rectangles, x, y, Direction.DOWN); // aim down
    } else { // else if rectangle under is red
      aimRandomDirection(rectangles, x, y); // choose new direction
    }
  }

  public void aimLeft(RectangleCell[][] rectangles, int x, int y) {
    if (!isHit(rectangles, x-1, y)) { // if rectangle to the left is not red(hit)
      followUpShot(rectangles, x, y, Direction.LEFT); // aim left
    } else { // else if rectangle to the left is red
      aimRandomDirection(rectangles, x, y); // choose new direction
    }
  }

  public void cannonBall(RectangleCell[][] rectangles, int x, int y) {
    Rectangle cell = rectangles[x][y].getRectangelCell();
    if (isAShip(rectangles, x, y)) {
      cell.setFill(Color.RED);
      System.out.println("Shot hit at coords x" + x + ", y" + y);
    } else {
      cell.setFill(Color.BLACK);
      System.out.println("Shot missed at coords x" + x + ", y" + y);
    }
  }

  // Check color of rectangle

  public boolean isHit(RectangleCell[][] rectangles, int x, int y) {
    Rectangle cell = rectangles[x][y].getRectangelCell();
    return cell.getFill() == Color.RED;
  }

  public boolean isCellBlack(RectangleCell[][] rectangles, int x, int y) {
    Rectangle cell = rectangles[x][y].getRectangelCell();
    return cell.getFill() == Color.BLACK;
  }

  public boolean isAShip(RectangleCell[][] rectangles, int x, int y) {
    return rectangles[x][y].getIsShip();
  }

  //-- Fredrik L
}