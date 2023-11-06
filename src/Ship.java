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
                        // Generera slumpmässig rad och kolumn för skeppsplacering
                        if (directionRandom) {
                            rowRandom = random.nextInt(10);
                            colRandom = random.nextInt(10 - shipSize);
                        } else {
                            rowRandom = random.nextInt(10 - shipSize);
                            colRandom = random.nextInt(10);
                        }
                        isValidPlacement = true;

                        boolean hasAdjacent = false;

                        // Kontrollera för intilliggande skeppsplaceringar
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

                        // Placera skeppet på spelplanen om platsen är giltig
                        if (isValidPlacement) {
                            for (int i = 0; i < shipSize; i++) {
                                int row = directionRandom ? rowRandom : rowRandom + i;
                                int col = directionRandom ? colRandom + i : colRandom;
                                rectangles[row][col].setIsShip(true);
                            }
                            placed = true;
                        }
                    }

                    attempts++;
                    if (attempts >= maxAttempts) {
                        System.out.println("Misslyckades med att placera skepp av storlek " + shipSize + " efter " + attempts + " försök.");
                    }
                }

                // Återställ spelplanen om ett skepp inte kan placeras
                if (!placed) {
                    resetBoard(rectangles);
                    System.out.println("Misslyckades med att placera skepp " + (numOfShips + 1) + " av storlek " + shipSize);
                    return false;
                }
            }
        }
        return true;
    }

    // Återställ spelplanen till sitt ursprungliga tillstånd
    private static void resetBoard(RectangleCell[][] rectangles) {
        for (int i = 0; i < rectangles.length; i++) {
            for (int j = 0; j < rectangles[i].length; j++) {
                rectangles[i][j].setIsShip(false);
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
