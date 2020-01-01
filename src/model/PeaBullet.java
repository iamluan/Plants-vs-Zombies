package model;

import controller.GamePlay;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.Iterator;

public class PeaBullet extends GameElements {

    private int lane;
    private int plantPos;
    private Timeline peaAnimation;
    private boolean flag;

    public PeaBullet(int x, int y, int plantPos, int lane ) {
        super(x, y, "resource/image/Pea.png", 20, 20);
        this.lane = lane;
        this.plantPos = plantPos;
        flag = false;
    }

    public void shoot(Pane pane){
        Timeline shooting = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            drawImage(pane);
            if(x <= 1050) setX(getX() + 1);
            collideZombie();
        }));
        shooting.setCycleCount(Timeline.INDEFINITE);
        shooting.play();
        peaAnimation = shooting;
        GamePlay.animationTimelines.add(shooting);
    }

    public void collideZombie(){
        Iterator<Zombie> zombies = GamePlay.allZombies.iterator();
        while (zombies.hasNext()){
            Zombie zombie = zombies.next();
            if(lane == zombie.getLane() && !flag){
                zombie.setHealth(zombie.getHealth() - 1);
                img.setVisible(false);
                img.setDisable(true);
                peaAnimation.stop();

                String splatFile = "resource/sound/shoot.wav";
                Media splat = new Media(new File(splatFile).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(splat);
                mediaPlayer.setAutoPlay(true);
                mediaPlayer.play();
            }
        }

    }
}
