package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import view.Main;

import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;


public class WelcomeWindow {

    public TextArea helpText;


    @FXML
    private AnchorPane mainRoot;

    @FXML
    private ImageView playButton;


    public void toggleHelpText() {
        helpText.setVisible(!helpText.isVisible());
    }

    @FXML
    public void playButton(MouseEvent mouseEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GamePlay.fxml"));
        AnchorPane root = loader.load();
        GamePlay controller = loader.<GamePlay>getController();
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.centerOnScreen();
        Main.primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        mainRoot.getChildren().setAll(root);
    }


}
