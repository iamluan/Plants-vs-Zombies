package model;

import controller.GamePlay;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Sunflower extends Plant {

    protected Timeline sunProducer;

    public Sunflower(int x, int y, int col, int row) {
        super(x, y, "resource/image/sun_flower.gif", 75, 75, 100, col, row);
    }

    public void act(Pane pane){
        Timeline sunProducer = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            eaten();
            Sun s = new Sun(getX(), getY(), false);
            s.drawImage(pane);
        }));
        sunProducer.setCycleCount(Timeline.INDEFINITE);
        sunProducer.play();
        this.sunProducer = sunProducer;
        GamePlay.animationTimelines.add(sunProducer);
    }

    public void eaten(){
        if(getHp() <= 0) {
            sunProducer.stop();
        }
    }

}
