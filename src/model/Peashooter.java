package model;

import controller.GamePlay;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.sql.Time;
import java.util.Iterator;

public class Peashooter extends Plant  {

    private Zombie target = null;
    private Timeline fire;

    public Peashooter(int x, int y, int col, int row) {
        super(x, y, "resource/image/pea_shooter.gif", 75, 75, 100, col, row);
    }

    public void act(Pane pane){
        fire = new Timeline(new KeyFrame(Duration.seconds(2), actionEvent -> {
            if(target == null) {
                target = defineTarget();
            }
            if ((target.getHealth() > 0 && target.getX() > x)) {
                checkHp();
                PeaBullet peaBullet = new PeaBullet(x + 80, y + 20, getX(), target);
                peaBullet.drawImage(pane);
                peaBullet.shoot();
            }else {
                target = null;
            }
        }));
        fire.setCycleCount(Timeline.INDEFINITE);
        fire.play();

        GamePlay.animationTimelines.add(fire);
    }

    public Zombie defineTarget(){
        synchronized (GamePlay.allZombies){
            Iterator<Zombie> zombies = GamePlay.allZombies.iterator();
            while(zombies.hasNext()){
                Zombie zombie=zombies.next();
                if(zombie.getLane() == row){
                    return zombie;
                }
            }
        }
        return null;
    }

    public void checkHp(){
        if(hp <= 0){
            fire.stop();
            img.setVisible(false);
            img.setDisable(true);
        }
    }
}
