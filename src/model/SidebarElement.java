package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class SidebarElement extends GameElements {

    private int cost;
    private int coolDownTime;
    private boolean isDisabled = false;
    private static ImageView border;
    private static int cardSelected = -1;
    private static HashMap<Integer, SidebarElement> allElements;

    public SidebarElement(int x, int y, String path, int width, int height, int cost) {
        super(x, y, path, width, height);
        this.cost = cost;
    }

    public int getCost(){
        return cost;
    }

    public static void getSidebarElements(Pane pane){

        allElements=new HashMap<Integer, SidebarElement>();

        SidebarElement sunflowerCard=new SidebarElement(24, 79,
                "resource/image/sunflowerCard.png",
                97, 58,50);
        sunflowerCard.drawImage(pane);
        sunflowerCard.coolDownTime=5000;
        allElements.put(1,sunflowerCard);
        sunflowerCard.img.setOnMouseClicked(e->{
            if (!sunflowerCard.isDisabled){
                    setCardSelected(1);
                }
            });

        SidebarElement peashooterCard = new SidebarElement(24, 147,
                "resource/image/peashooterCard.png",97,58,100);
        peashooterCard.drawImage(pane);
        peashooterCard.coolDownTime = 5000;
        allElements.put(2, peashooterCard);
        peashooterCard.img.setOnMouseClicked(e->{
            if (!peashooterCard.isDisabled){
                setCardSelected(2);
            }
        });

        border =new ImageView(new Image("resource/image/selectedCardBorder.png",
                110.0,72.0,false,false));
        pane.getChildren().add(border);
        border.setVisible(false);
        border.setDisable(true);
    }

    public static void setCardSelected(int i){
        cardSelected = i;
        border.setVisible(true);
        border.setX(allElements.get(cardSelected).getX()-5);
        border.setY(allElements.get(cardSelected).getY()-5);
    }

    public static int getCardSelected(){
        return cardSelected;
    }

    public static SidebarElement getElement(int x){
        if(allElements.containsKey(x)) return allElements.get(x);
        return null;
    }

    public void setDisabledOn(Pane pane){
        this.isDisabled=true;
        ImageView im =new ImageView(new Image(
                "resource/image/lock.png",50.0,50.0,
                false,false));
        im.setX(this.getX()+20);
        im.setY(this.getY());
        pane.getChildren().add(im);
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(this.coolDownTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.isDisabled=false;
            im.setVisible(false);
            im.setDisable(true);
        });
        t.start();
    }

    public static void setCardSelectedToNull(){
        cardSelected = -1;
        border.setVisible(false);
    }
}
