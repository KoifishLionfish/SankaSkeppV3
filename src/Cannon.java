import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Cannon {

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
        makeCellRed(rectangles, x, y);

        System.out.println("Shot hit at: " + cell.getRectangleId());
        aimRandomDirection(rectangles, x, y); // choose random direction
      }
      // if shot hits a previously hit rectangle, retry
      else if (isCellRed(rectangles, x, y) || isCellBlack(rectangles, x, y)) {
        shoot(rectangles);
      }
    }
  }

  // follow-up shots if the first hit is successful
  public void followUpShot(RectangleCell[][] rectangles, int x, int y, Direction previousDirection) {
    RectangleCell cell = rectangles[x][y];

    // if it hits, go in the same direction again
    if (isCellBlue(rectangles, x, y)) {
      makeCellBlack(rectangles, x, y);
      System.out.println("Shot missed at: " + cell.getRectangleId());
    } else if (isCellOrange(rectangles, x, y)) {
      makeCellRed(rectangles, x, y);
      System.out.println("Shot hit at: " + cell.getRectangleId());
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
    if (y - 1 > -1 && !isCellRed(rectangles, x, y - 1)) {
      y--;
      followUpShot(rectangles, x, y, Direction.UP);
    } else {
      aimRandomDirection(rectangles, x, y);
    }
  }

  public void aimRight(RectangleCell[][] rectangles, int x, int y) {
    if (x + 1 < 10 && !isCellRed(rectangles, x + 1, y)) {
      x++;
      followUpShot(rectangles, x, y, Direction.RIGHT);
    } else {
      aimRandomDirection(rectangles, x, y);
    }
  }

  public void aimDown(RectangleCell[][] rectangles, int x, int y) {
    if (y + 1 < 10 && !isCellRed(rectangles, x, y + 1)) {
      y++;
      followUpShot(rectangles, x, y, Direction.DOWN);
    } else {
      aimRandomDirection(rectangles, x, y);
    }
  }

  public void aimLeft(RectangleCell[][] rectangles, int x, int y) {
    if (x - 1 > -1 && !isCellRed(rectangles, x - 1, y)) {
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
    Rectangle cell = rectangles[x][y].getRectangelCell();
    return cell.getFill() == Color.ORANGE;
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
