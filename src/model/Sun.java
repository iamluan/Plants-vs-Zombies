package model;

import controller.GamePlay;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Sun extends GameElements{
    private int x;
    private int y;
    private final int timeouttime;

    public Sun(int x, int y, boolean fallingSun)
    {
        super(x, y, "resource/image/sun.gif", 50, 50);

        if(fallingSun) timeouttime=14000;
        else timeouttime=5000;
        disappearAfterTime();
    }
    public void disappearAfterTime(){
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(timeouttime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            img.setVisible(false);
            img.setDisable(true);
        });
        t.start();
    }

    @Override
    public void drawImage(Pane p) {
        super.drawImage(p);
        this.img.setOnMouseClicked(e->{
            this.img.setVisible(false);
            this.img.setDisable(true);
            GamePlay.updateSunScore(25);
        });

    }

    public void moveSun() {
        if(getY()<=550) {
            setY(getY()+1);
        }
    }

    public void dropSun() {
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(12),e -> moveSun()));
        animation.setCycleCount(550);
        animation.play();
        GamePlay.animationTimelines.add(animation);
    }


}
