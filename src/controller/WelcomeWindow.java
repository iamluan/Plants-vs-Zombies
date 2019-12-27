package controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import view.Main;

import java.io.IOException;

public class WelcomeWindow {

    public TextArea helpText;

    public void toggleHelpText() {
        helpText.setVisible(!helpText.isVisible());
    }

    public void startGame(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameWindow.fxml"));
        Parent root = loader.load();
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.centerOnScreen();
        Main.primaryStage.show();
        Main.primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
    }
}
