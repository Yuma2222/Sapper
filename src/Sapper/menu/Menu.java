package Sapper.menu;

import Sapper.sceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Menu implements Initializable {
    @FXML private AnchorPane border;
    @FXML private Button startGameBtn;
    @FXML private Button showScoresBtn;
    @FXML private final sceneController sceneController = new sceneController();

    @FXML private void startGame(ActionEvent event) throws IOException {
        sceneController.switchToDifficultySetter(event);
    }

    @FXML private void showScores(ActionEvent event) throws IOException {
        sceneController.switchToScoreboard(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        border.setStyle("-fx-background-color: transparent; ");
        startGameBtn.setStyle("-fx-background-color: transparent");
        showScoresBtn.setStyle("-fx-background-color: transparent");
    }
}
