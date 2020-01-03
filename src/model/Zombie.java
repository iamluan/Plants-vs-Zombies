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
import java.sql.Time;
import java.util.Iterator;

public abstract class Zombie extends GameElements {
    public int hp;
    public int damage;
    public int lane;
    public int x, y;
    public int deltaX = -1;
    public Timeline move;
    public Timeline chomping;
    public ImageView image;
    public Timeline eat;

    public Zombie(int x, int y, String imagePath, int health, int damage, int lane, int width, int height) {
        super(x, y, imagePath, width, height);
        this.hp = health;
        this.damage = damage;
        this.lane = lane;

    }


    public void forward() {
        move = new Timeline(new KeyFrame(Duration.millis(70), e -> {
            checkHp();
            zombieWalk();
            checkReachedHouse();
        }));
        move.setCycleCount(Timeline.INDEFINITE);
        move.play();

        GamePlay.animationTimelines.add(move);
    }

    public void zombieWalk() {
        if (getX() > 220) {
            setX(getX() + deltaX);
            eatPlant(detectPlant());
        }
    }

    public Plant detectPlant(){
        synchronized (GamePlay.allPlants) {
            Iterator<Plant> plants = GamePlay.allPlants.iterator();
            while (plants.hasNext()) {
                Plant plant = plants.next();
                if (plant.getRow() == lane && (getX() - plant.getX()) <= 1 ){
                    return plant;
                }
            }
        }
        return null;
    }

    public void eatPlant(Plant plant){
        if(plant != null) {
            stop();
            if (plant.getHp() > 0 && getHp() > 0) {
                eat = new Timeline(new KeyFrame(Duration.millis(100), actionEvent -> {
                    if(!GamePlay.allPlants.contains(plant)){
                        eat.stop();
                        move();
                    }
                    plant.setHp(plant.getHp() - 1);
                }));
                eat.setCycleCount(Timeline.INDEFINITE);
                eat.play();

                GamePlay.animationTimelines.add(eat);
            }
        }
    }

    public void stop(){
        deltaX = 0;
    }

    public void move(){
        deltaX = -1;
    }

    public void checkReachedHouse() {
        if (getX() < 220) {

            GamePlay.endGame();

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




    public int getHp() {
        return hp;
    }

    public int getLane() {
        return lane;
    }

    public void setHp(int hp) {
        this.hp = hp;
        checkHp();
    }

    public void checkHp(){
        if(hp <= 0){
            this.img.setVisible(false);
            this.img.setDisable(true);
            move.stop();
            if(this.chomping!=null)
            {
                this.chomping.stop();
            }
            GamePlay.allZombies.remove(this);
        }
    }
}
