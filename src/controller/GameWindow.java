package controller;

import javafx.application.Platform;
import javafx.scene.control.Button;
import model.Game;

import java.util.Timer;
import java.util.TimerTask;

public class GameWindow {

    private Game game;
    private final byte FPS = 3;

    public Button myButton;

    public void initialize() {
        game = new Game();
        Timer timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(() -> updateAnimation());
            }
        };
        timer.schedule(timerTask, 0, 1000/FPS);
    }

    private void updateAnimation() {

    }
}
