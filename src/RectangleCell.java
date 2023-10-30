import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleCell {

    private boolean isShip=false;
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
        if (isShip){
            rectangelCell.setFill(Color.ORANGE);
        }
        return isShip;
    }

    public void setisShip(boolean ship) {
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
}



