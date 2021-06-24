package Sapper.difficultySetter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class DifficultySetter{

    @FXML private void setDifficulty(ActionEvent event) throws IOException {
       String level = ((Button)event.getSource()).getId();
       Sapper.sceneController sceneController = new Sapper.sceneController();
       sceneController.switchToGameSession(event, level);
    }
}
