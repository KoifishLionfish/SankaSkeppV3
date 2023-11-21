

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import java.util.SplittableRandom;


public class RectangleCell {


    private boolean isShip=false;
    private boolean isActive=true;
    private String rectangleId;


    private Rectangle rectangelCell = new Rectangle(25, 25);


    public RectangleCell() {
        rectangelCell.setFill(Color.ROYALBLUE);
        rectangelCell.setStroke(Color.BLACK);
    }


    public void setIsShipStatus(boolean ship){
        this.isShip=ship;
    }

    public boolean getIsShip() {
        if (isShip){
            // rectangelCell.setFill(Color.ORANGE);
        }
        return isShip;
    }

    public void setIsShip(boolean ship) {
        isShip = ship;
        if (isShip){
            rectangelCell.setFill(Color.ORANGE);
        }
        else rectangelCell.setFill(Color.ROYALBLUE);
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
    }


    public String getRectangleId() {
        return rectangleId;
    }


    public void setRectangleId(String rectangleId) {
        this.rectangleId = rectangleId;
    }
}