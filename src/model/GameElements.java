package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class GameElements{
    protected int x;
    protected int y;
    protected String path;
    transient protected ImageView img;
    protected int width;
    protected int height;

    public GameElements(int x, int y, String path, int width, int height){
        this.x=x;
        this.y=y;
        this.path=path;
        this.width=width;
        this.height=height;
    }

    public void drawImage(Pane pane){
        img = new ImageView();
        Image im=new Image(path,(double) width,(double) height,false,false);
        img.setImage(im);
        img.setX(x);
        img.setY(y);
        pane.getChildren().add(img);
    }

    public int getX(){
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
        img.setX(x);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        img.setY(y);
    }

}
