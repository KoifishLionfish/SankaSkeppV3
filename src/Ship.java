import java.util.Random;

public class Ship {
    private String shipName;
    private int shipSize;

    static void placeRandomShips(RectangleCell[][] rectangles, int numberOfShips, int shipSize) {

    static void placeRandomShips(RectangleCell[][] rectangles, int[] shipsPerSize) {
        int[] shipSizes = {5, 4, 4, 3, 3, 3, 2, 2, 2, 2, 1, 1, 1, 1, 1};
        Random random = new Random();

        for (int index = 0; index < shipSizes.length; index++) {
            int shipSize = shipSizes[index];
            int numberOfShips = (index < shipsPerSize.length) ? shipsPerSize[index] : 0;

            // Loopar igenom skeppen som ska placeras
            for (int numOfShips = 0; numOfShips < numberOfShips; numOfShips++) {
                // randomly väljer horizonalt och vertikalt
                boolean directionRandom = random.nextBoolean();
                // Initializerar variabler för att lagra de valda positionerna för skeppen
                int rowRandom = 0;
                int colRandom = 0;
                //  kollar om skeppets position är tillåtet
                boolean isValidPlacement = false;

                // fortsätter leta efter en tillåten position tills de hittar en
                while (!isValidPlacement) {

                    // genererar random positoner inom spel brädan för skeppen beroende på deras riktning

                    if (directionRandom) {
                        rowRandom = random.nextInt(10);
                        colRandom = random.nextInt(10 - shipSize);
                    } else {
                        rowRandom = random.nextInt(10 - shipSize);
                        colRandom = random.nextInt(10);
                    }

                    // antar att positionen är tillåten tills den bli motbevisad (alltså om en hamnar på en annan tex)
                    isValidPlacement = true;

                    // kollar omgivning för andras skepp postitoner för att se om placeringen är tillåten
                    for (int i = rowRandom - 1; i < rowRandom + shipSize + 1; i++) {
                        for (int j = colRandom - 1; j < colRandom + shipSize + 1; j++) {
                            // kollar så att cellerna är inom spel brädans gränser
                            if (i >= 0 && i < 10 && j >= 0 && j < 10) {
                                // ifall en grann cell redan har ett skepp, så blir placeringen inte tillåten
                                if (rectangles[i][j].getIsShip()) {
                                    isValidPlacement = false;
                                    break;
                                }
                            }
                        }
                        if (!isValidPlacement) {
                            break;
                        }
                    }
                }


                // placerar skepp i positioner som är tillåtna
                for (int i = 0; i < shipSize; i++) {
                    int row = directionRandom ? rowRandom : rowRandom + i;
                    int col = directionRandom ? colRandom + i : colRandom;

                    rectangles[row][col].setisShip(true);
                }
            }
        }
    }
}



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
