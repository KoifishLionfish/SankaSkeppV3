import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class vBox {
    static VBox getvBox() {
        VBox vbox = new VBox();
        vbox.setSpacing(0);
        for (int i = 0; i < 10; i++) {
            char ascii = (char) (65 + i);
            Label l = new Label();
            l.setAlignment(Pos.CENTER_LEFT);
            l.setPrefSize(50, 50);
            l.setText(String.valueOf(ascii));
            l.setAlignment(Pos.BASELINE_CENTER);
            l.setTextFill(Color.BROWN);
            //  Rectangle r = new Rectangle(50, 50);
            //r.setFill(Color.CADETBLUE);
            vbox.getChildren().add(l);
        }
        return vbox;
    }
}
