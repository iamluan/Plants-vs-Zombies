package model;

import controller.GamePlay;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Plant extends GameElements {

    protected int hp;
    protected int col;
    protected int row;

    public Plant(int x, int y, String path, int width, int height, int hp, int col, int row) {
        super(x, y, path, width, height);
        this.hp = hp;
        this.col = col;
        this.row = row;
    }

    public void drawImage(GridPane gridPane) {
        img = new ImageView(new Image(path, width, height, false,false));
        gridPane.add(img, col, row, 1, 1);
    }

    public void act(Pane pane){}

    public int getHp(){return hp;}

    public void setHp(int hp){
        this.hp = hp;
        if(this.hp <= 0){
            GamePlay.allPlants.remove(this);
            img.setVisible(false);
            img.setDisable(true);
        }
    }

    public void Eaten(){}
}
