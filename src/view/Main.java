package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    public static Stage primaryStage;
    public static MediaPlayer backgroundMusic;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent welcomeWindow = FXMLLoader.load(getClass().getResource("WelcomeWindow.fxml"));
        //addBackgroundMusic();
        primaryStage.setScene(new Scene(welcomeWindow,1024, 600));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Plant vs Zombies");
        Main.primaryStage = primaryStage;
        primaryStage.show();
    }


    public void addBackgroundMusic() {
        String waveFile = "src/resource/sound/background.wav";
        Media sound = new Media(new File(waveFile).toURI().toString());
        backgroundMusic = new MediaPlayer(sound);
        backgroundMusic.setAutoPlay(true);
        backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMusic.setStartTime(Duration.seconds(0));
        backgroundMusic.setStopTime(Duration.seconds(50));
        backgroundMusic.play();
    }
}
