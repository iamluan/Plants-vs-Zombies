package model;

import controller.GamePlay;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public abstract class Zombie extends GameElements {
    public int health;
    public int damage;
    public int lane;
    public int x, y;
    public int deltaX;
    public transient Timeline zombieAnimation;
    public transient ImageView image;
    public boolean isMoving = true;
    public static int numberOfZombie = 0;
    public String imagePath = null;




    public Zombie(int x, int y, String imagePath,  int health, int damage, int lane) {
        super(x, y, imagePath, 68, 118);
        this.health = health;
        this.damage = damage;
        this.lane = lane;

    }


    public void drawImage(Pane pane){
        ImageView img = new ImageView();
        Image im=new Image(imagePath,68,118,false,false);
        img.setImage(im);
        img.setX(x);
        img.setY(y);
        pane.getChildren().add(img);
    }


    public void moveZombie() {
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(70), e -> zombieWalk()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
        this.zombieAnimation = animation;
        GamePlay.animationTimelines.add(animation);
    }

    public void zombieWalk() {
        if(getX()>220 && this.health>0) // If the zombie did't reach the house and is still alive
        {
            //Test deltaX
            deltaX = -1;
            //Update the location of the zombie
            setX(getX() + deltaX);
        }
    }

    public void checkReachedHouse() {
        if (image.getX() <= 220) { //reach the house
            String eatingBrainFile = "src/resource/sound/eatingbrain.wav";
            Media eatingBrain = new Media(new File(eatingBrainFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(eatingBrain);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();
        }
    }

    public int getHealth() {
        return health;
    }

    public int getLane() {
        return lane;
    }

    public int setHealth() {
        return health;
    }
}
