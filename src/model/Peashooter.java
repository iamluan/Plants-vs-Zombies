package model;

import controller.GamePlay;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.sql.Time;

public class Peashooter extends Plant  {

    private Timeline shooting;

    private int lane;

    public Peashooter(int x, int y, int col, int row) {
        super(x, y, "resource/image/pea_shooter.gif", 75, 75, 100, col, row);
        this.lane = col;
    }

    public void act(Pane pane){
        Timeline shoot = new Timeline(new KeyFrame(Duration.seconds(2), actionEvent -> {
            PeaBullet peaBullet = new PeaBullet(x, y, getX(), lane);
            //peaBullet.shoot(pane);
        }));
        shoot.setCycleCount(Timeline.INDEFINITE);
        shoot.play();
        this.shooting = shoot;
        GamePlay.animationTimelines.add(shoot);
    }

    public void eaten(){
        this.shooting.stop();
        img.setVisible(false);
        img.setDisable(true);
    }
}
