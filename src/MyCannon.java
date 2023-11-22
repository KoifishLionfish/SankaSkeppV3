import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

//++ Fredrik L (Malin)
public class MyCannon {
    private final List<Integer> initialHitX = new ArrayList<>();
    private final List<Integer> initialHitY = new ArrayList<>();
    private final List<Integer> latestShotX = new ArrayList<>();
    private final List<Integer> latestShotY = new ArrayList<>();
    private final List<Integer> currentShipX = new ArrayList<>();
    private final List<Integer> currentShipY = new ArrayList<>();
    private String currentShip = "";
    private Direction previousDirection = null;
    private int shipLength = 0;
    private int numberOfSunkenShips = 0;
    private int nrOfHits = 0;
    private final int NUMBER_OF_SHIPS = 10;
    private boolean isShipHorizontal = false;
    private boolean isShipVertical = false;
    private int horizontalRectangles = 0;
    private int verticalRectangles = 0;
    private boolean previousHit = false;
    private String guess;
    int numberOfShipSunk = 0;
    private Boolean shipdirection = null;


    // random shot
    public String randomShot(RectangleCell[][] rectangles) {

        // Random number generator
        Random randomHit = new Random();
        int x = randomHit.nextInt(10);
        int y = randomHit.nextInt(10);

        // if no ship has been hit or sunk
        if (initialHitX.isEmpty() && initialHitY.isEmpty()) {
            // if rectangle isnt black and isnt hit ship (RED) & is active
            if (!isCellBlack(rectangles, x, y) && !isHit(rectangles, x, y) && isActive(rectangles, x, y)) { //
              guess = (x) + "" + (y);
            } else {
                randomShot(rectangles); // if cell is black or red(hit), shoot again
            }
        } else {


            if (previousHit && !latestShotX.isEmpty()) {
                x = latestShotX.get(0);
                y = latestShotY.get(0);

                if (previousDirection == Direction.RIGHT && x != 9) {
                    followUpShot(rectangles, x, y, Direction.RIGHT);
                } else if (previousDirection == Direction.LEFT && x != 0) {
                    followUpShot(rectangles, x, y, Direction.LEFT);
                } else if (previousDirection == Direction.UP && y != 0) {
                    followUpShot(rectangles, x, y, Direction.UP);
                } else if (previousDirection == Direction.DOWN && y != 9) {
                    followUpShot(rectangles, x, y, Direction.DOWN);
                }
            } else {
                x = initialHitX.get(0);
                y = initialHitY.get(0);


                if (previousDirection == null) {
                    aimRandomDirection(rectangles, x, y);
                } else { // else, if previousDirection has direction
                    if (previousDirection == Direction.UP) {
                        aimUp(rectangles, x, y);
                    } else if (previousDirection == Direction.DOWN) {
                        aimDown(rectangles, x, y);
                    } else if (previousDirection == Direction.LEFT) {
                        aimLeft(rectangles, x, y);
                    } else if (previousDirection == Direction.RIGHT) {
                        aimRight(rectangles, x, y);
                    }
                }
            }
        }
        return guess;
    }

    // follow up shots

    public void followUpShot(RectangleCell[][] rectangles, int x, int y, Direction direction) {

//omsänktskepp

        if (direction == Direction.UP) {
            if (y > 0 && y <= 9) { // if y is 1+
                y--; // go up once
                if (y > 0) {
                    if (isCellBlack(rectangles, x, y) || isHit(rectangles, x, y) || !isActive(rectangles, x, y)) { // if rectrangle is black or inactive.
                        resetLatestShotListAndPreviousDirection(); // reset latest shotList, go back to initial shot
                        randomShot(rectangles); // if its black or inactive, shoot again. IT SHOULD NOT SHOOT AGAIN
                    } else { // if its not black and not inactive

                      guess = (x) + "" + (y);
                        previousDirection = Direction.UP;
                        //handleFollowUpResult();
                    }
                } else {
                    // if y = 0 (upper border)
                    if (isCellBlack(rectangles, x, y) || isHit(rectangles, x, y) || !isActive(rectangles, x, y)) { // if rectangle is black
                        randomShot(rectangles); // if its black, shoot again;
                    } else { // if its not black or not inactive
                        if (isHit(rectangles, x, y)) { // if rectangle is already hit
                            resetLatestShotListAndPreviousDirection(); // reset shotlist
                            randomShot(rectangles); // shoot somehwere else
                        }
                         guess = (x) + "" + (y);
                        //handleFollowUpResultAgain();
                        previousDirection = Direction.UP;
                    }
                }
            }
        } else if (direction == Direction.RIGHT) { // direction RIGHT
            if (x >= 0 && x < 9) { // if x 8--
                x++;
                if (x < 9) {
                    if (isCellBlack(rectangles, x, y) || isHit(rectangles, x, y) || !isActive(rectangles, x, y)) { // check if rectangle is black
                        resetLatestShotListAndPreviousDirection(); // reset latest shotList, go back to initial shot
                        randomShot(rectangles); // if its black, shoot again
                    } else { // if its not black

                         guess = (x) + "" + (y);
                        previousDirection = Direction.RIGHT;
                        //handleFollowUpResult();
                    }
                } else { // if y == 9 (right border)
                    if (isCellBlack(rectangles, x, y) || isHit(rectangles, x, y) || !isActive(rectangles, x, y)) { // check if rectangle is black
                        randomShot(rectangles); // if its black, shoot again
                    } else { // if its not black
                        if (isHit(rectangles, x, y)) { // doesnt get stuck in border
                            resetLatestShotListAndPreviousDirection();
                            randomShot(rectangles);
                        }

                         guess = (x) + "" + (y);
                        //handleFollowUpResultAgain();
                        previousDirection = Direction.RIGHT;
                    }
                }
            }
        } else if (direction == Direction.DOWN) { // direction down
            if (y >= 0 && y < 9) { // if y 8--
                y++; // go down once
                if (y < 9) {
                    if (isCellBlack(rectangles, x, y) || isHit(rectangles, x, y) || !isActive(rectangles, x, y)) { // check if rectangle is black
                        resetLatestShotListAndPreviousDirection(); // reset latest shotList, go back to initial shot
                        randomShot(rectangles); // if its black, shoot again
                    } else { // if its not black

                         guess = (x) + "" + (y);
                        previousDirection = Direction.DOWN;
                        //handleFollowUpResult();
                    }
                } else { // if y == 9 (lower border)
                    if (isCellBlack(rectangles, x, y) || isHit(rectangles, x, y) || !isActive(rectangles, x, y)) { // check if rectangle is black
                        randomShot(rectangles); // if its black, shoot again
                    } else { // if its not black
                        if (isHit(rectangles, x, y)) { // dont get stuck in border
                            resetLatestShotListAndPreviousDirection();
                            randomShot(rectangles);
                        }

                         guess = (x) + "" + (y);
                        previousDirection = Direction.DOWN;
                        //handleFollowUpResultAgain();
                    }
                }
            }
        } else if (direction == Direction.LEFT) { // direction LEFT
            if (x > 0 && x <= 9) { // if x is 1++
                x--; // go left once
                if (x > 0) {
                    if (isCellBlack(rectangles, x, y) || isHit(rectangles, x, y) || !isActive(rectangles, x, y)) { // check if rectangle is black
                        resetLatestShotListAndPreviousDirection(); // reset latest shotList, go back to initial shot
                        randomShot(rectangles); // if its black, shoot again
                    } else { // if its not black

                         guess = (x) + "" + (y);
                        previousDirection = Direction.LEFT;
                        //handleFollowUpResult();
                    }
                } else { // if x = 0 (left border)
                    if (isCellBlack(rectangles, x, y) || isHit(rectangles, x, y) || !isActive(rectangles, x, y)) { // check if rectangle is black
                        randomShot(rectangles); // if its black, shoot again
                    } else { // if its not black
                        if (isHit(rectangles, x, y)) { // dont get stuck in border
                            resetLatestShotListAndPreviousDirection();
                            randomShot(rectangles);
                        }
                         guess = (x) + "" + (y);
                        //handleFollowUpResultAgain();
                        previousDirection = Direction.LEFT;
                    }
                }
            }
        }
        //  }
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
        if (y > 0) {
            if (!isHit(rectangles, x, y - 1) && isActive(rectangles, x, y - 1) && !isCellBlack(rectangles, x, y - 1)) { // if rectangle above is not red(hit) & active
                followUpShot(rectangles, x, y, Direction.UP); // aim up
            } else { // else if rectangle above is red
                aimRandomDirection(rectangles, x, y); // choose new direction
            }
        } else {
            aimRandomDirection(rectangles, x, y);
        }

    }

    public void aimRight(RectangleCell[][] rectangles, int x, int y) {
        if (x < 9) {
            if (!isHit(rectangles, x + 1, y) && isActive(rectangles, x + 1, y) && !isCellBlack(rectangles, x + 1, y)) { // if rectangle to the right is not red(hit) & active
                followUpShot(rectangles, x, y, Direction.RIGHT); // aim right
            } else { // else if rectangle to the right is red
                aimRandomDirection(rectangles, x, y); // choose new direction
            }
        } else {
            aimRandomDirection(rectangles, x, y);
        }

    }

    public void aimDown(RectangleCell[][] rectangles, int x, int y) {
        if (y < 9) {
            if (!isHit(rectangles, x, y + 1) && isActive(rectangles, x, y + 1) && !isCellBlack(rectangles, x, y + 1)) { // if rectangle under is not red(hit) & active
                followUpShot(rectangles, x, y, Direction.DOWN); // aim down
            } else { // else if rectangle under is red
                aimRandomDirection(rectangles, x, y); // choose new direction
            }
        } else {
            aimRandomDirection(rectangles, x, y);
        }
    }


    public void aimLeft(RectangleCell[][] rectangles, int x, int y) {

        if (x > 0) {
            if (!isHit(rectangles, x - 1, y) && isActive(rectangles, x - 1, y)  && !isCellBlack(rectangles, x - 1, y)) { // if rectangle to the left is not red(hit) & active
                followUpShot(rectangles, x, y, Direction.LEFT); // aim left
            } else { // else if rectangle to the left is red
                aimRandomDirection(rectangles, x, y); // choose new direction
            }
        } else {
            aimRandomDirection(rectangles, x, y);
        }
    }

    public Runnable cannonBallHit(RectangleCell[][] rectangles, int x, int y, boolean hit, boolean sink) {
        //uppdaterar kartan beroende på svar om h/m/s på enemyBoard
        Runnable runnableToReturn = new Runnable() {
            @Override
            public void run() {
                Rectangle cell = rectangles[x][y].getRectangelCell();

                if (hit) {
                    cell.setFill(Color.RED);
                } else {
                    cell.setFill(Color.BLACK);
                }
            }
        };
        return runnableToReturn;
    }


    public void cannonBallHitStatusUpdate(RectangleCell[][] rectangles, int x, int y, boolean hit, boolean sink) {
//sparar data om skott efter
        if (hit) {
            rectangles[x][y].setIsShipStatus(true);
            saveDataHit(rectangles, x, y);
            if (sink) {
                omSänktskepp(rectangles);
            }
        } else {
            rectangles[x][y].setIsShipStatus(false);
        }
        rectangles[x][y].setIsActive(false);
    }

    public String cannonBallAnswer(Board board, RectangleCell[][] rectangles, int x, int y) {
        //Ger bara svar om hit/miss men uppdaterar inte kartan på my board

        if (isAShip(rectangles, x, y)) {

            if (isShipSunk(rectangles, x, y)) {
                numberOfShipSunk++;
                System.out.println("numberOfShipSunk: "+numberOfShipSunk);
                if (numberOfShipSunk == 10) {
                    return "game over";
                } else {
                    return "s ";
                }
            } else {
                return "h ";
            }
        } else {
            return "m ";
        }
    }


    public Runnable cannonBallUpdateMap(RectangleCell[][] rectangles, int x, int y) {
        //Uppdaterar kartan på "myBoard"
        Runnable runnableToReturn = new Runnable() {
            @Override
            public void run() {
                Rectangle cell = rectangles[x][y].getRectangelCell();
                if (isAShip(rectangles, x, y)) {
                    cell.setFill(Color.RED);
                } else {
                    cell.setFill(Color.BLACK);
                }
            }
        };
        return runnableToReturn;
    }

    // Check color of rectangle

    public boolean isHit(RectangleCell[][] rectangles, int x, int y) {
        return rectangles[x][y].getIsShip();
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
                if (isActive(rectangles, x, y + 1) && !isCellBlack(rectangles, x, y + 1) && !isAShip(rectangles, x, y + 1) && !isHit(rectangles, x, y + 1)) { // if cell under is not active and is not black and not a ship
                    rectangles[x][y + 1].setIsActive(false); // deactive bottom cell
                }
            } else if (x > 0 && x < 9 && y == 0) { // upper middle
                if (isActive(rectangles, x - 1, y) && !isCellBlack(rectangles, x - 1, y) && !isAShip(rectangles, x - 1, y) && !isHit(rectangles, x - 1, y)) { // left of ship
                    rectangles[x - 1][y].setIsActive(false); // deactive left cell

                }
                if (isActive(rectangles, x - 1, y + 1) && !isCellBlack(rectangles, x - 1, y + 1) && !isAShip(rectangles, x - 1, y + 1) && !isHit(rectangles, x - 1, y + 1)) {
                    rectangles[x - 1][y + 1].setIsActive(false); // deactivate bottom left
                }
                if (isActive(rectangles, x, y + 1) && !isCellBlack(rectangles, x, y + 1) && !isAShip(rectangles, x, y + 1) && !isHit(rectangles, x, y + 1)) {
                    rectangles[x][y + 1].setIsActive(false); // deactivate cell under
                }
                if (isActive(rectangles, x + 1, y + 1) && !isCellBlack(rectangles, x + 1, y + 1) && !isAShip(rectangles, x + 1, y + 1) && !isHit(rectangles, x + 1, y + 1)) {
                    rectangles[x + 1][y + 1].setIsActive(false); // deactivate bottom right cell
                }
                if (isActive(rectangles, x + 1, y) && !isCellBlack(rectangles, x + 1, y) && !isAShip(rectangles, x + 1, y) && !isHit(rectangles, x + 1, y)) {
                    rectangles[x + 1][y].setIsActive(false); // deactivate right cell
                }
            } else if (x == 9 && y == 0) { // if upper right corner - only need to check bottom
                if (isActive(rectangles, x, y + 1) && !isCellBlack(rectangles, x, y + 1) && !isAShip(rectangles, x, y + 1) && !isHit(rectangles, x, y + 1)) {
                    rectangles[x][y + 1].setIsActive(false); // deactivate bottom cell
                }
            }
            // check right border
            else if (x == 9 && y > 0 && y < 9) { // if middle of right border, check upper, upperleft, left, bottom left and down.
                if (isActive(rectangles, x, y - 1) && !isCellBlack(rectangles, x, y - 1) && !isAShip(rectangles, x, y - 1) && !isHit(rectangles, x, y - 1)) {
                    rectangles[x][y - 1].setIsActive(false); // deactivate cell above
                }
                if (isActive(rectangles, x - 1, y - 1) && !isCellBlack(rectangles, x - 1, y - 1) && !isAShip(rectangles, x - 1, y - 1) && !isHit(rectangles, x - 1, y - 1)) {
                    rectangles[x - 1][y - 1].setIsActive(false); // deactivate cell upper left
                }
                if (isActive(rectangles, x - 1, y) && !isCellBlack(rectangles, x - 1, y) && !isAShip(rectangles, x - 1, y) && !isHit(rectangles, x - 1, y)) {
                    rectangles[x - 1][y].setIsActive(false); // deactivate left cell
                }
                if (isActive(rectangles, x - 1, y + 1) && !isCellBlack(rectangles, x - 1, y + 1) && !isAShip(rectangles, x - 1, y + 1) && !isHit(rectangles, x - 1, y + 1)) {
                    rectangles[x - 1][y + 1].setIsActive(false); // deactivate bottom left cell
                }
                if (isActive(rectangles, x, y + 1) && !isCellBlack(rectangles, x, y + 1) && !isAShip(rectangles, x, y + 1) && !isHit(rectangles, x, y + 1)) {
                    rectangles[x][y + 1].setIsActive(false); // deactivate bottom cell
                }
            } else if (x == 9 && y == 9) {
                // bottom right corner, only need to ceck up
                if (isActive(rectangles, x, y - 1) && !isCellBlack(rectangles, x, y - 1) && !isAShip(rectangles, x, y - 1) && !isHit(rectangles, x, y - 1)) {
                    rectangles[x][y - 1].setIsActive(false); // deactivate upper cell
                }
            } else if (x > 0 && x < 9 && y == 9) {
                // bottom middle, check left, left up, up, right up, right
                if (isActive(rectangles, x - 1, y) && !isCellBlack(rectangles, x - 1, y) && !isAShip(rectangles, x - 1, y) && !isHit(rectangles, x - 1, y)) {
                    rectangles[x - 1][y].setIsActive(false); // deactivate left cell
                }
                if (isActive(rectangles, x - 1, y - 1) && !isCellBlack(rectangles, x - 1, y - 1) && !isAShip(rectangles, x - 1, y - 1) && !isHit(rectangles, x - 1, y - 1)) {
                    rectangles[x - 1][y - 1].setIsActive(false); // deactivate upper left cell
                }
                if (isActive(rectangles, x, y - 1) && !isCellBlack(rectangles, x, y - 1) && !isAShip(rectangles, x, y - 1) && !isHit(rectangles, x, y - 1)) {
                    rectangles[x][y - 1].setIsActive(false); // deactivate upper cell
                }
                if (isActive(rectangles, x + 1, y - 1) && !isCellBlack(rectangles, x + 1, y - 1) && !isAShip(rectangles, x + 1, y - 1) && !isHit(rectangles, x + 1, y - 1)) {
                    rectangles[x + 1][y - 1].setIsActive(false); // deactivate upper right cell
                }
                if (isActive(rectangles, x + 1, y) && !isCellBlack(rectangles, x + 1, y) && !isAShip(rectangles, x + 1, y) && !isHit(rectangles, x + 1, y)) {
                    rectangles[x + 1][y].setIsActive(false); // deactivate right cell
                }
            } else if (x == 0 && y == 9) {
                if (isActive(rectangles, x, y - 1) && !isCellBlack(rectangles, x, y - 1) && !isAShip(rectangles, x, y - 1) && !isHit(rectangles, x, y - 1)) {
                    rectangles[x][y - 1].setIsActive(false); // deactivate upper cell
                }
            } else if (x == 0 && y > 0 && y < 9) {
                // left border, check up, up right, right, down right and down
                if (isActive(rectangles, x, y - 1) && !isCellBlack(rectangles, x, y - 1) && !isAShip(rectangles, x, y - 1) && !isHit(rectangles, x, y - 1)) {
                    rectangles[x][y - 1].setIsActive(false); // deactivate upper cell
                }
                if (isActive(rectangles, x + 1, y - 1) && !isCellBlack(rectangles, x + 1, y - 1) && !isAShip(rectangles, x + 1, y - 1) && !isHit(rectangles, x + 1, y - 1)) {
                    rectangles[x + 1][y - 1].setIsActive(false); // deactivate upper right cell
                }
                if (isActive(rectangles, x + 1, y) && !isCellBlack(rectangles, x + 1, y) && !isAShip(rectangles, x + 1, y) && !isHit(rectangles, x + 1, y)) {
                    rectangles[x + 1][y].setIsActive(false); // deactivate right cell
                }
                if (isActive(rectangles, x + 1, y + 1) && !isCellBlack(rectangles, x + 1, y + 1) && !isAShip(rectangles, x + 1, y + 1) && !isHit(rectangles, x + 1, y + 1)) {
                    rectangles[x + 1][y + 1].setIsActive(false); // deactivate right bottom cell
                }
                if (isActive(rectangles, x, y + 1) && !isCellBlack(rectangles, x, y + 1) && !isAShip(rectangles, x, y + 1) && !isHit(rectangles, x, y + 1)) {
                    rectangles[x][y + 1].setIsActive(false); // deactivate bottom cell
                }
            } else if (x > 0 && x < 9 && y > 0 && y < 9) {
                // middle of board, not in borders. check up, up right, right, down right, down, down left, left, up left
                if (isActive(rectangles, x, y - 1) && !isCellBlack(rectangles, x, y - 1) && !isHit(rectangles, x, y - 1)) {
                    rectangles[x][y - 1].setIsActive(false); // deactivate upper cell
                }
                if (isActive(rectangles, x + 1, y - 1) && !isCellBlack(rectangles, x + 1, y - 1) && !isAShip(rectangles, x + 1, y - 1) && !isHit(rectangles, x + 1, y - 1)) {
                    rectangles[x + 1][y - 1].setIsActive(false); // deactivate upper right cell
                }
                if (isActive(rectangles, x + 1, y) && !isCellBlack(rectangles, x + 1, y) && !isAShip(rectangles, x + 1, y) && !isHit(rectangles, x + 1, y)) {
                    rectangles[x + 1][y].setIsActive(false); // deactivate right cell
                }
                if (isActive(rectangles, x + 1, y + 1) && !isCellBlack(rectangles, x + 1, y + 1) && !isAShip(rectangles, x + 1, y + 1) && !isHit(rectangles, x + 1, y + 1)) {
                    rectangles[x + 1][y + 1].setIsActive(false); // deactivate bottom right cell
                }
                if (isActive(rectangles, x, y + 1) && !isCellBlack(rectangles, x, y + 1) && !isAShip(rectangles, x, y + 1) && !isHit(rectangles, x, y + 1)) {
                    rectangles[x][y + 1].setIsActive(false); // deactivate bottom cell
                }
                if (isActive(rectangles, x - 1, y + 1) && !isCellBlack(rectangles, x - 1, y + 1) && !isAShip(rectangles, x - 1, y + 1) && !isHit(rectangles, x - 1, y + 1)) {
                    rectangles[x - 1][y + 1].setIsActive(false); // deactivate bottom left cell
                }
                if (isActive(rectangles, x - 1, y) && !isCellBlack(rectangles, x - 1, y) && !isAShip(rectangles, x - 1, y) && !isHit(rectangles, x - 1, y)) {
                    rectangles[x - 1][y].setIsActive(false); // deactivate left cell
                }
                if (isActive(rectangles, x - 1, y - 1) && !isCellBlack(rectangles, x - 1, y - 1) && !isAShip(rectangles, x - 1, y - 1) && !isHit(rectangles, x - 1, y - 1)) {
                    rectangles[x - 1][y - 1].setIsActive(false); // deactivate upper right cell
                }
            }
        }
    }

    public void resetLatestShotListAndPreviousDirection() {
        latestShotX.clear();
        latestShotY.clear();
        previousDirection = null;
        previousHit = false;
    }

    public void addLatestXandY(int x, int y) {
        latestShotX.clear();
        latestShotY.clear();
        latestShotX.add(x);
        latestShotY.add(y);
    }


    public void saveDataHit(RectangleCell[][] rectangles, int x, int y) {
        nrOfHits++; // register hit
        // save coords
        initialHitX.add(x);
        initialHitY.add(y);
        currentShipX.add(x);
        currentShipY.add(y);


        shipLength++; // add rectangle to length of ship


        scanShipLength(rectangles, x, y); // scan ship for knowing when its broken later
        typeOfShip(); // writes what type of ship it is to check for errors

        rectangles[x][y].setIsShipStatus(true);
    }


    public void handleFollowUpResult(RectangleCell[][] rectangleCells, int x, int y) {
        if (previousDirection == Direction.UP) {
            if (y == 0 || !isActive(rectangleCells, x, y - 1)) {
                handleFollowUpResultIfEdgeOfShipReached(rectangleCells, x, y, Direction.UP);
            } else {
                handleFollowUpResultIfEdgeOfShipNotReached(rectangleCells, x, y, Direction.UP);
            }
        } else if (previousDirection == Direction.RIGHT) {
            if (x == 9 || !isActive(rectangleCells, x + 1, y)) {
                handleFollowUpResultIfEdgeOfShipReached(rectangleCells, x, y, Direction.RIGHT);
            } else {
                handleFollowUpResultIfEdgeOfShipNotReached(rectangleCells, x, y, Direction.RIGHT);
            }
        } else if (previousDirection == Direction.DOWN) {
            if (y == 9 || !isActive(rectangleCells, x, y + 1)) {
                handleFollowUpResultIfEdgeOfShipReached(rectangleCells, x, y, Direction.DOWN);
            } else {
                handleFollowUpResultIfEdgeOfShipNotReached(rectangleCells, x, y, Direction.DOWN);
            }
        } else if (previousDirection == Direction.LEFT) {
            if (x == 0 || !isActive(rectangleCells, x - 1, y)) {
                handleFollowUpResultIfEdgeOfShipReached(rectangleCells, x, y, Direction.LEFT);
            } else {
                handleFollowUpResultIfEdgeOfShipNotReached(rectangleCells, x, y, Direction.LEFT);
            }
        }
    }



    public void handleFollowUpResultIfEdgeOfShipNotReached(RectangleCell[][] rectangles, int x, int y, Direction direction) {
        if (isHit(rectangles, x, y)) { // if it hits a ship
            nrOfHits++; // add hit

            addLatestXandY(x, y); // save latest coordinates
            previousHit = true;

        } else { // if cannonball misses
            if (direction == Direction.UP || direction == Direction.DOWN) {

                shipdirection = Boolean.FALSE.equals(isShipHorizontal());

            } else {
                shipdirection = isShipHorizontal();
            }
            resetLatestShotListAndPreviousDirection();

            if (previousDirection == direction && Boolean.TRUE.equals(shipdirection)) {
                //  resetLatestShotListAndPreviousDirection(); // Reset LatestShot
                previousDirection = direction.opposite();
            }
        }
    }

    public void handleFollowUpResultIfEdgeOfShipReached(RectangleCell[][] rectangles, int x, int y, Direction direction) {
        if (isHit(rectangles, x, y)) { // if it hits a ship
            nrOfHits++; // register hit
            previousHit = true;

            resetLatestShotListAndPreviousDirection();
            previousDirection = direction.opposite();

        } else { // if it misses
            if (direction == Direction.UP || direction == Direction.DOWN) {
                shipdirection = Boolean.FALSE.equals(isShipHorizontal());
            } else {
                shipdirection = isShipHorizontal();
            }

            if (previousDirection == direction && Boolean.TRUE.equals(shipdirection)) {// && nrOfHits > 1) {
                resetLatestShotListAndPreviousDirection();
                previousDirection = direction.opposite();
            } else {
                resetLatestShotListAndPreviousDirection(); // reset latest shotlist, go back to initial shot
            }
        }
    }

    private Boolean isShipHorizontal() {
        if (initialHitX.size() > 1) {
            return Objects.equals(initialHitX.get(0), initialHitX.get(1));
        } else return null;
    }


    public void omSänktskepp(RectangleCell[][] rectangles) {

        System.out.println("You sunk my: " + currentShip);
        numberOfSunkenShips++;

        deactivateCellsAroundShip(rectangles);

        System.out.println("You have sunken a total of: " + numberOfSunkenShips + "/" + NUMBER_OF_SHIPS + " Ships");

        // resetting
        initialHitX.clear();
        initialHitY.clear();
        shipLength = 0;
        nrOfHits = 0;
        isShipVertical = false;
        isShipHorizontal = false;
        verticalRectangles = 0;
        horizontalRectangles = 0;
        currentShipY.clear();
        currentShipX.clear();
        previousHit = false;
        previousDirection = null;
        resetLatestShotListAndPreviousDirection();
    }


    public boolean isShipSunk(RectangleCell[][] rectangleCells, int x, int y) {

        //om det inte finns fler aktiva skeppsdelar åt något håll returnera true
        //aka om det inte finns fler gula celler
        return !finnsFlerSkepp(rectangleCells, x, y, Direction.UP) && !finnsFlerSkepp(rectangleCells, x, y, Direction.RIGHT) && !finnsFlerSkepp(rectangleCells, x, y, Direction.DOWN) && !finnsFlerSkepp(rectangleCells, x, y, Direction.LEFT);
    }

    public boolean finnsFlerSkepp(RectangleCell[][] rectangleCells, int x, int y, Direction direction) {
        int newX = x;
        int newY = y;


        switch (direction) {
            case UP:
                newY = y - 1;
                break;
            case RIGHT:
                newX = x + 1;
                break;
            case DOWN:
                newY = y + 1;
                break;
            case LEFT:
                newX = x - 1;
                break;
        }
        if (newX > 9 || newX < 0 || newY > 9 || newY < 0) {
            return false;
        }

        Color cellColor = (Color) rectangleCells[newX][newY].getRectangelCell().getFill();

        if (cellColor.equals(Color.RED)) {
            return finnsFlerSkepp(rectangleCells, newX, newY, direction);

        } else if (cellColor.equals(Color.ORANGE)) {
            return true;
        } else {
            return false;
        }
    }
//-- Fredrik L
}