package model;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javafx.scene.input.MouseEvent;
import java.util.HashMap;

public class PlantCard extends GameElements {

    private int cost;
    private static ImageView selectedBorder;
    private static int type = -1; //
    private boolean isSelected = false;
    private int coolDownTime;
    private Pane pane;

    public PlantCard(int x, int y, String path, int width, int height, int cost, int type, Pane pane) {
        super(x, y, path, width, height);
        this.cost = cost;
        this.type = type;
        this.pane = pane;

        if(type == 1)
            coolDownTime = 5000;
        else if(type == 2)
            coolDownTime = 6000;

        super.drawImage(pane);

        img.setOnMouseClicked(event -> {
            isSelected = true;
            setSelectedBorderOn(type, pane);
            event.consume();
        });
    }

    public boolean getStatus(){
        return isSelected;
    }

    public int getCost(){
        return this.cost;
    }

    public int getType(){
        return type;
    }

    public void setSelectedBorderOn(int type, Pane pane) {
        selectedBorder = new ImageView(new Image("resource/image/selectedCardBorder.png",
                110.0,72.0,false,false));
        pane.getChildren().add(selectedBorder);
        selectedBorder.setVisible(false);
        selectedBorder.setDisable(true);
        if (type == 1){
            selectedBorder.setX(24 - 5);
            selectedBorder.setY(79 - 5);
        } else if (type == 2) {
            selectedBorder.setX(24 - 5);
            selectedBorder.setY(147 - 5);
        }
        selectedBorder.setVisible(true);
    }

    public void setSelectedBorderOff() {
        selectedBorder.setVisible(false);
    }


}
