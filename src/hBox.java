import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class hBox {
    static HBox gethBox() {
        HBox hbox = new HBox();
        hbox.setSpacing(0);
        Label emptyLabel = new Label();
        emptyLabel.setPrefSize(50, 50);
        hbox.getChildren().add(emptyLabel);
        for (int i = 0; i < 10; i++) {
            String place = "";

            Label l = new Label();
            l.setAlignment(Pos.CENTER_LEFT);
            l.setPrefSize(50, 50);
            l.setText(place + i);
            l.setAlignment(Pos.BASELINE_CENTER);
            l.setTextFill(Color.BROWN);
            hbox.getChildren().addAll(l);
        }
        return hbox;
    }
}
