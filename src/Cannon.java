import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//++ Fredrik L
public class Cannon {
  private final List<Integer> initialHitX = new ArrayList<>();
  private final List<Integer> initialHitY = new ArrayList<>();
  private final List<Integer> currentShipX = new ArrayList<>();
  private final List<Integer> currentShipY = new ArrayList<>();
  private String currentShip = "";
  private int shipLength = 0;
  private int numberOfSunkenShips = 0;
  private int nrOfHits = 0;
  private final int NUMBER_OF_SHIPS = 10;
  private boolean isShipHorizontal = false;
  private boolean isShipVertical = false;
  private int horizontalRectangles = 0;
  private int verticalRectangles = 0;


  // random shot
  public void randomShot(RectangleCell[][] rectangles) {
    // Random number generator
    Random randomHit = new Random();
    int x = randomHit.nextInt(10);
    int y = randomHit.nextInt(10);

    // if no ship has been hit or sunk
    if (initialHitX.isEmpty() && initialHitY.isEmpty()) {
      // if rectangle isnt black and isnt hit ship (RED)
      if (!isCellBlack(rectangles, x, y) && !isHit(rectangles, x, y) && isActive(rectangles, x, y)) { //
        // shoots cannonball
        cannonBall(rectangles, x, y); // if miss turn black if hits turn red.

        if (isCellBlack(rectangles, x, y) || !isActive(rectangles, x, y)) {
          randomShot(rectangles); // if first hit is miss - shoot again
        } else if (isHit(rectangles, x, y)) {
          nrOfHits++; // register hit

          // save coords
          initialHitX.add(x);
          initialHitY.add(y);
          currentShipX.add(x);
          currentShipY.add(y);


          shipLength++; // add rectangle to length of ship


          scanShipLength(rectangles, x, y); // scan ship for knowing when its broken later
          System.out.println("Horizontal: " + isShipHorizontal);
          System.out.println("Vertical: " + isShipVertical);
          typeOfShip(); // writes what type of ship it is to check for errors

          aimRandomDirection(rectangles, x, y); // aim random direction -> direction -> followUpShot
        }
      } else {
        randomShot(rectangles); // if cell is black or red(hit), shoot again
      }
    } else {

      // if battleship isnt sunk
      // if list is not empty go back to initial shot:
      x = initialHitX.get(0);
      y = initialHitY.get(0);

//      deactivateBlock(rectangles, x, y); // deactivate cells on the sides if they arent black.

      aimRandomDirection(rectangles, x, y); // aim random direction -> direction -> followUpShot
    }
  }

  // follow up shots
  public void followUpShot(RectangleCell[][] rectangles, int x, int y, Direction direction) {

    for (int tries = 0; tries < 1; tries++) { // direction up

      if ((shipLength - nrOfHits) == 0) {
        System.out.println("You sunk my: " + currentShip);
        numberOfSunkenShips++;

        System.out.println("ship had length: " + shipLength);

        System.out.println("amount of ship cells (should be same as shiplength): " + currentShipY.size());

        deactivateCellsAroundShip(rectangles);

        System.out.println("You have sunken a total of: " + numberOfSunkenShips + "/" + NUMBER_OF_SHIPS + " Ships");

        // if whole ship is sunk, deactivate cells next to initialHit before resetting
        x = initialHitX.get(0);
        y = initialHitY.get(0);

        if (numberOfSunkenShips == NUMBER_OF_SHIPS) { // if all ships are sunk
          gameOver(); // if game is won run method and after
          break; // break
        } else { // if there are ships left

          // resetting
          initialHitX.remove(0);
          initialHitY.remove(0);
          shipLength = 0;
          nrOfHits = 0;
          isShipVertical = false;
          isShipHorizontal = false;
          verticalRectangles = 0;
          horizontalRectangles = 0;
          currentShipY.clear();
          currentShipX.clear();


          randomShot(rectangles); // shoot again
        }

      } else {

        if (direction == Direction.UP) {
          System.out.println("followup - going UP");
          if (y > 0 && y <= 9) { // if y is 1++
            y--; // go up once
            if (isCellBlack(rectangles, x, y)) { // check if rectrangle is black.
              randomShot(rectangles); // if its black, shoot again.
            } else { // if its not black
              cannonBall(rectangles, x, y); // shoot cannonball
              if (isHit(rectangles, x, y)) { // if it hits a ship
                nrOfHits++; // add hit
                tries--; // keep going same direction
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
          System.out.println("followup - going DOWN");
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
          System.out.println("followup - going LEFT");
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
          System.out.println("followup - going RIGHT");
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

    if (horizontalRectangles > verticalRectangles) {
      isShipHorizontal = true;
    } else {
      isShipVertical = true;
    }

  }

  public void scanLeft(RectangleCell[][] rectangles, int x, int y) {

    // going left until it hits water
    for (int scan = 0; scan < 1; scan++) {
      if (x - 1 > -1) {
        x--;
        if (isAShip(rectangles, x, y)) {
          shipLength++;
          horizontalRectangles++;
          // save coordinate of ship rectangle in list
          currentShipX.add(x);
          currentShipY.add(y);
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
          horizontalRectangles++;
          // save coordinate of ship rectangle in list
          currentShipX.add(x);
          currentShipY.add(y);
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
          verticalRectangles++;
          // save coordinate of ship rectangle in list
          currentShipX.add(x);
          currentShipY.add(y);
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
          verticalRectangles++;
          // save coordinate of ship rectangle in list
          currentShipX.add(x);
          currentShipY.add(y);
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
    if (!isHit(rectangles, x, y - 1) && isActive(rectangles, x, y - 1)) { // if rectangle above is not red(hit) & active
      followUpShot(rectangles, x, y, Direction.UP); // aim up
    } else { // else if rectangle above is red
      aimRandomDirection(rectangles, x, y); // choose new direction
    }
  }

  public void aimRight(RectangleCell[][] rectangles, int x, int y) {
    if (!isHit(rectangles, x + 1, y) && isActive(rectangles, x + 1, y)) { // if rectangle to the right is not red(hit) & active
      followUpShot(rectangles, x, y, Direction.RIGHT); // aim right
    } else { // else if rectangle to the right is red
      aimRandomDirection(rectangles, x, y); // choose new direction
    }
  }

  public void aimDown(RectangleCell[][] rectangles, int x, int y) {
    if (!isHit(rectangles, x, y + 1) && isActive(rectangles, x, y + 1)) { // if rectangle under is not red(hit) & active
      followUpShot(rectangles, x, y, Direction.DOWN); // aim down
    } else { // else if rectangle under is red
      aimRandomDirection(rectangles, x, y); // choose new direction
    }
  }

  public void aimLeft(RectangleCell[][] rectangles, int x, int y) {
    if (!isHit(rectangles, x - 1, y) && isActive(rectangles, x - 1, y)) { // if rectangle to the left is not red(hit) & active
      followUpShot(rectangles, x, y, Direction.LEFT); // aim left
    } else { // else if rectangle to the left is red
      aimRandomDirection(rectangles, x, y); // choose new direction
    }
  }

  public void cannonBall(RectangleCell[][] rectangles, int x, int y) {
    Rectangle cell = rectangles[x][y].getRectangelCell();
    String cellId = rectangles[x][y].getRectangleId();
    if (isAShip(rectangles, x, y)) {
      cell.setFill(Color.RED);
      System.out.println("Shot hit at: " + cellId);
//      deactivateBlock(rectangles, x, y);
    } else {
      cell.setFill(Color.BLACK);
      System.out.println("Shot missed at: " + cellId);
//      rectangles[x][y].setIsActive(false); // deactivate cell
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

  public boolean isActive(RectangleCell[][] rectangles, int x, int y) {
    return rectangles[x][y].getIsActive();
  }


  // method wich deactivates surrounding cells
  public void deactivateCellsAroundShip(RectangleCell[][] rectangles) {
    for (int i = 0; i < shipLength; i++) {
      int x = currentShipX.get(i);
      int y = currentShipY.get(i);


      // upper border
      if (x == 0 && y == 0) { // upper left corner
        if (isActive(rectangles, x, y + 1) && !isCellBlack(rectangles, x, y + 1) && !isAShip(rectangles, x, y + 1) && !isHit(rectangles, x, y+1)) { // if cell under is not active and is not black and not a ship
          rectangles[x][y + 1].setIsActive(false); // deactive bottom cell
        }
      } else if (x > 0 && x < 9 && y == 0) { // upper middle
        if (isActive(rectangles, x - 1, y) && !isCellBlack(rectangles, x - 1, y) && !isAShip(rectangles, x - 1, y) && !isHit(rectangles, x-1, y)) { // left of ship
          rectangles[x - 1][y].setIsActive(false); // deactive left cell

        }
        if (isActive(rectangles, x - 1, y + 1) && !isCellBlack(rectangles, x - 1, y + 1) && !isAShip(rectangles, x - 1, y + 1) && !isHit(rectangles, x-1, y+1)) {
          rectangles[x - 1][y + 1].setIsActive(false); // deactivate bottom left
        }
        if (isActive(rectangles, x, y + 1) && !isCellBlack(rectangles, x, y + 1) && !isAShip(rectangles, x, y + 1) && !isHit(rectangles, x, y+1)) {
          rectangles[x][y + 1].setIsActive(false); // deactivate cell under
        }
        if (isActive(rectangles, x + 1, y + 1) && !isCellBlack(rectangles, x + 1, y + 1) && !isAShip(rectangles, x + 1, y + 1) && !isHit(rectangles, x+1, y+1)) {
          rectangles[x + 1][y + 1].setIsActive(false); // deactivate bottom right cell
        }
        if (isActive(rectangles, x + 1, y) && !isCellBlack(rectangles, x + 1, y) && !isAShip(rectangles, x + 1, y) && !isHit(rectangles, x+1, y)) {
          rectangles[x + 1][y].setIsActive(false); // deactivate right cell
        }
      } else if (x == 9 && y == 0) { // if upper right corner - only need to check bottom
        if (isActive(rectangles, x, y + 1) && !isCellBlack(rectangles, x, y + 1) && !isAShip(rectangles, x, y + 1) && !isHit(rectangles, x, y+1)) {
          rectangles[x][y + 1].setIsActive(false); // deactivate bottom cell
        }
      }
      // check right border
      else if (x == 9 && y > 0 && y < 9) { // if middle of right border, check upper, upperleft, left, bottom left and down.
        if (isActive(rectangles, x, y - 1) && !isCellBlack(rectangles, x, y - 1) && !isAShip(rectangles, x, y - 1) && !isHit(rectangles, x, y-1)) {
          rectangles[x][y - 1].setIsActive(false); // deactivate cell above
        }
        if (isActive(rectangles, x - 1, y - 1) && !isCellBlack(rectangles, x - 1, y - 1) && !isAShip(rectangles, x - 1, y - 1) && !isHit(rectangles, x-1, y-1)) {
          rectangles[x - 1][y - 1].setIsActive(false); // deactivate cell upper left
        }
        if (isActive(rectangles, x - 1, y) && !isCellBlack(rectangles, x - 1, y) && !isAShip(rectangles, x - 1, y) && !isHit(rectangles, x-1, y)) {
          rectangles[x - 1][y].setIsActive(false); // deactivate left cell
        }
        if (isActive(rectangles, x - 1, y + 1) && !isCellBlack(rectangles, x - 1, y + 1) && !isAShip(rectangles, x - 1, y + 1) && !isHit(rectangles, x-1, y+1)) {
          rectangles[x - 1][y + 1].setIsActive(false); // deactivate bottom left cell
        }
        if (isActive(rectangles, x, y + 1) && !isCellBlack(rectangles, x, y + 1) && !isAShip(rectangles, x, y + 1) && !isHit(rectangles, x, y+1)) {
          rectangles[x][y + 1].setIsActive(false); // deactivate bottom cell
        }
      } else if (x == 9 && y == 9) {
        // bottom right corner, only need to ceck up
        if (isActive(rectangles, x, y - 1) && !isCellBlack(rectangles, x, y - 1) && !isAShip(rectangles, x, y - 1) && !isHit(rectangles, x, y-1)) {
          rectangles[x][y - 1].setIsActive(false); // deactivate upper cell
        }
      } else if (x > 0 && x < 9 && y == 9) {
        // bottom middle, check left, left up, up, right up, right
        if (isActive(rectangles, x - 1, y) && !isCellBlack(rectangles, x - 1, y) && !isAShip(rectangles, x - 1, y) && !isHit(rectangles, x-1, y)) {
          rectangles[x - 1][y].setIsActive(false); // deactivate left cell
        }
        if (isActive(rectangles, x - 1, y - 1) && !isCellBlack(rectangles, x - 1, y - 1) && !isAShip(rectangles, x - 1, y - 1) && !isHit(rectangles, x-1, y-1)) {
          rectangles[x - 1][y - 1].setIsActive(false); // deactivate upper left cell
        }
        if (isActive(rectangles, x, y - 1) && !isCellBlack(rectangles, x, y - 1) && !isAShip(rectangles, x, y - 1) && !isHit(rectangles, x, y-1)) {
          rectangles[x][y - 1].setIsActive(false); // deactivate upper cell
        }
        if (isActive(rectangles, x + 1, y - 1) && !isCellBlack(rectangles, x + 1, y - 1) && !isAShip(rectangles, x + 1, y - 1) && !isHit(rectangles, x+1, y-1)) {
          rectangles[x + 1][y - 1].setIsActive(false); // deactivate upper right cell
        }
        if (isActive(rectangles, x + 1, y) && !isCellBlack(rectangles, x + 1, y) && !isAShip(rectangles, x + 1, y) && !isHit(rectangles, x+1, y)) {
          rectangles[x + 1][y].setIsActive(false); // deactivate right cell
        }
      } else if (x == 0 && y == 9) {
        if (isActive(rectangles, x, y - 1) && !isCellBlack(rectangles, x, y - 1) && !isAShip(rectangles, x, y - 1) && !isHit(rectangles, x, y-1)) {
          rectangles[x][y - 1].setIsActive(false); // deactivate upper cell
        }
      } else if (x == 0 && y > 0 && y < 9) {
        // left border, check up, up right, right, down right and down
        if (isActive(rectangles, x, y - 1) && !isCellBlack(rectangles, x, y - 1) && !isAShip(rectangles, x, y - 1) && !isHit(rectangles, x, y-1)) {
          rectangles[x][y - 1].setIsActive(false); // deactivate upper cell
        }
        if (isActive(rectangles, x + 1, y - 1) && !isCellBlack(rectangles, x + 1, y - 1) && !isAShip(rectangles, x + 1, y - 1) && !isHit(rectangles, x+1, y-1)) {
          rectangles[x + 1][y - 1].setIsActive(false); // deactivate upper right cell
        }
        if (isActive(rectangles, x + 1, y) && !isCellBlack(rectangles, x + 1, y) && !isAShip(rectangles, x + 1, y) && !isHit(rectangles, x+1, y)) {
          rectangles[x + 1][y].setIsActive(false); // deactivate right cell
        }
        if (isActive(rectangles, x + 1, y + 1) && !isCellBlack(rectangles, x + 1, y + 1) && !isAShip(rectangles, x + 1, y + 1) && !isHit(rectangles, x+1, y+1)) {
          rectangles[x + 1][y + 1].setIsActive(false); // deactivate right bottom cell
        }
        if (isActive(rectangles, x, y + 1) && !isCellBlack(rectangles, x, y + 1) && !isAShip(rectangles, x, y + 1) && !isHit(rectangles, x, y+1)) {
          rectangles[x][y + 1].setIsActive(false); // deactivate bottom cell
        }
      } else if (x > 0 && x < 9 && y > 0 && y < 9) {
        // middle of board, not in borders. check up, up right, right, down right, down, down left, left, up left
        if (isActive(rectangles, x, y-1) && !isCellBlack(rectangles, x, y-1) && !isHit(rectangles, x, y-1)) {
          rectangles[x][y-1].setIsActive(false); // deactivate upper cell
        }
        if (isActive(rectangles, x+1, y-1) && !isCellBlack(rectangles, x+1, y-1) && !isAShip(rectangles, x+1, y-1) && !isHit(rectangles, x+1, y-1)) {
          rectangles[x+1][y-1].setIsActive(false); // deactivate upper right cell
        }
        if (isActive(rectangles, x+1, y) && !isCellBlack(rectangles, x+1, y) && !isAShip(rectangles, x+1, y) && !isHit(rectangles, x+1, y)) {
          rectangles[x+1][y].setIsActive(false); // deactivate right cell
        }
        if (isActive(rectangles, x+1, y+1) && !isCellBlack(rectangles, x+1, y+1) && !isAShip(rectangles, x+1, y+1) && !isHit(rectangles, x+1, y+1)) {
          rectangles[x+1][y].setIsActive(false); // deactivate bottom right cell
        }
        if (isActive(rectangles, x, y+1) && !isCellBlack(rectangles, x, y+1) && !isAShip(rectangles, x, y+1) && !isHit(rectangles, x, y+1)) {
          rectangles[x][y+1].setIsActive(false); // deactivate bottom cell
        }
        if (isActive(rectangles, x-1, y+1) && !isCellBlack(rectangles, x-1, y+1) && !isAShip(rectangles, x-1, y+1) && !isHit(rectangles, x-1, y+1)) {
          rectangles[x-1][y+1].setIsActive(false); // deactivate bottom left cell
        }
        if (isActive(rectangles, x-1, y) && !isCellBlack(rectangles, x-1, y) && !isAShip(rectangles, x-1, y) && !isHit(rectangles, x-1, y)) {
          rectangles[x-1][y].setIsActive(false); // deactivate left cell
        }
        if (isActive(rectangles, x-1, y-1) && !isCellBlack(rectangles, x-1, y-1) && !isAShip(rectangles, x-1, y-1) && !isHit(rectangles, x-1, y-1)) {
          rectangles[x-1][y-1].setIsActive(false); // deactivate upper right cell
        }
      }
    }
  }

  //-- Fredrik L
}