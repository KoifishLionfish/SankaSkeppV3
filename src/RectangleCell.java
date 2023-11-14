import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.SplittableRandom;

public class RectangleCell {


  private boolean isShip = false;
  private boolean isActive = true;
  private String rectangleId;

  private Rectangle rectangelCell = new Rectangle(50, 50);

  //Konstruktor

  //Här är basen för hur en rektangel ser ut och vill man ändra ex
  //färg för att det är ett skepp så ändrar man i get/set för boolean
  // för ex isSHip så när man sätter isShip=true, ändras färgen samtidigt
  public RectangleCell() {
    rectangelCell.setFill(Color.ROYALBLUE);
    rectangelCell.setStroke(Color.BLACK);
  }


  //Getters och Setters
  public boolean getIsShip() {
    if (isShip) {
      return rectangelCell.getFill() == Color.ORANGE;
    }
    return false;
  }

  public void setisShip(boolean ship) {
    isShip = ship;
    if (isShip) {
      rectangelCell.setFill(Color.ORANGE);
    } else rectangelCell.setFill(Color.ROYALBLUE);

  }

  public Rectangle getRectangelCell() {
    return rectangelCell;
  }

  public void setRectangelCell(Rectangle rectangelCell) {
    this.rectangelCell = rectangelCell;
  }

  public boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(boolean active) {
    isActive = active;

    if (!isActive) {
      this.rectangelCell.setFill(Color.GREENYELLOW);
    }
  }

  public String getRectangleId() {
    return rectangleId;
  }

  public void setRectangleId(String rectangleId) {
    this.rectangleId = rectangleId;
  }
}