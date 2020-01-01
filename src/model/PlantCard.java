package model;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javafx.scene.input.MouseEvent;
import java.util.HashMap;

public class PlantCard extends GameElements {

    private int cost;
    private String type;
    private static ImageView selectedBorder;
    private int isSelected = 0; //
    private boolean isDisabled = false;
    private int coolDownTime;





    public PlantCard(int x, int y, String path, int width, int height, int cost, String type) {
        super(x, y, path, width, height);
        this.cost = cost;
        this.type = type;
        if(type.equals("sunflower"))
            coolDownTime = 5000;
        else if(type.equals("peashooter"))
            coolDownTime = 6000;
    }



    public static void displayPlantCard(Pane pane){
        PlantCard sunflowerCard = new PlantCard(24, 79, "resource/image/sunflowerCard.png",97,58,50, "sunflower");
        sunflowerCard.drawImage(pane);
        sunflowerCard.img.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setSelectedBorderOn("sunflower");
                event.consume();
            }
        });
        PlantCard peashooterCard = new PlantCard(24, 147,"resource/image/peashooterCard.png",97,58,100, "peashooter");
        peashooterCard.drawImage(pane);
        peashooterCard.img.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setSelectedBorderOn("sunflower");
                event.consume();
            }
        });

        selectedBorder = new ImageView(new Image("resource/image/selectedCardBorder.png",110.0,72.0,false,false));
        pane.getChildren().add(selectedBorder);
        selectedBorder.setVisible(false);
        selectedBorder.setDisable(true);
    }


    public static void setSelectedBorderOn(String type) {
        if (type.equals("sunflower")){
            selectedBorder.setX(24 - 5);
            selectedBorder.setY(79 - 5);
        } else if (type.equals("peashooter")) {
            selectedBorder.setX(24 - 5);
            selectedBorder.setY(147 - 5);
        }
        selectedBorder.setVisible(true);
    }

    public static void setSelectedBorderOff() {
        selectedBorder.setVisible(false);
    }


}
