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
import java.util.Iterator;

public abstract class Zombie extends GameElements {
    public int health;
    public int damage;
    public int lane;
    public int x, y;
    public int deltaX = -1;
    public transient Timeline zombieAnimation;
    public transient Timeline chomping;
    public transient ImageView image;
    public boolean isCollidedPlant = true;
    public boolean isEating = false;
    public static int numberOfZombie = 0;
    public String imagePath = null;


    public Zombie(int x, int y, String imagePath, int health, int damage, int lane, int width, int height) {
        super(x, y, imagePath, width, height);
        this.health = health;
        this.damage = damage;
        this.lane = lane;

    }


    public void moveZombie() {
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(70), e -> zombieWalk()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
        this.zombieAnimation = animation;
        GamePlay.animationTimelines.add(animation);
    }

    public void zombieWalk() {
        if (getX() > 220 && this.health > 0) {
            //Update the location of the zombie
            setX(getX() + deltaX);
            try{
            eatPlant();}
            catch (Exception e) {
                System.out.println(e);
            }
            checkReachedHouse();
            // System.out.println("The zombie has moved");
        }
    }

    public void checkReachedHouse() {
        //System.out.println(getX());
        if (getX() <= 220) { //reach the house
            String eatingBrainFile = "src/resource/sound/eatingbrain.wav";
            Media eatingBrain = new Media(new File(eatingBrainFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(eatingBrain);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();
        }
    }

    public void chompingPlantSound() {
        String chompFile = "src/resource/sound/chomp.wav";
        Media chomp = new Media(new File(chompFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(chomp);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setStartTime(Duration.seconds(0));
        mediaPlayer.setStopTime(Duration.seconds(1));
        mediaPlayer.setCycleCount(1);
        mediaPlayer.play();
    }

    public void eatPlant() {
        int foundPlant = 0;
        synchronized (GamePlay.allPlants) {
            Iterator<Plant> i = GamePlay.allPlants.iterator();
            while (i.hasNext()) {
                Plant p = i.next();
                if (p.row == getLane()) {
                    if (Math.abs(p.getX() - img.getX()) <= 50) {
                        foundPlant = 1;
                        if (isCollidedPlant == false) {
                            isCollidedPlant = true;
                            isEating = true;
                        }
                        if (isEating) {
                            Timeline chomp = new Timeline(new KeyFrame(Duration.millis(1000), e -> chompingPlantSound()));
                            chomp.setCycleCount(1000);
                            chomp.play();
                            this.deltaX = 0;
                            this.chomping = chomp;
                            GamePlay.animationTimelines.add(chomp);
                            isEating = false;
                        }
                        if (foundPlant == 1) {
                            this.deltaX = 0;
                            p.setHp(p.getHp() - this.damage);
                            if (p.getHp() <= 0) {
                                p.setHp(0);
                                GamePlay.allPlants.remove(p);
                                p.img.setVisible(false);
                                p.img.setDisable(true);
                                this.deltaX = -1;
                                this.isCollidedPlant = false;
                                this.chomping.stop();
                            }
                        }
                    } else {
                        this.deltaX = -1;
                        this.isCollidedPlant = false;
                        if (this.chomping != null) {
                            this.chomping.stop();
                        }
                    }
                } else {
                    this.deltaX = -1;
                }
            }
        }
        if (foundPlant == 0) {
            this.deltaX = -1;
            if (this.chomping != null) {
                this.chomping.stop();
            }
            this.isCollidedPlant = false;
        }
    }


    public Timeline getZombieAnimation() {
        return zombieAnimation;
    }

    public int getHealth() {
        return health;
    }

    public int getLane() {
        return lane;
    }

    public void setHealth(int health) {
        this.health = health;
        if(health <= 0){
            this.img.setVisible(false);
            this.img.setDisable(true);
            this.zombieAnimation.stop();
            if(this.chomping!=null)
            {
                this.chomping.stop();
            }
            for(int i = 0; i<GamePlay.allZombies.size(); i++)
            {
                if(this == GamePlay.allZombies.get(i)) {
                    GamePlay.allZombies.remove(i);
                }
            }
        }
        if (health <= 7) {
            img.setImage(new Image("resource/image/normalzombie.gif", (double) 68,(double) 118,false,false));
            this.width=68;
            this.height=118;
        }
    }
}
