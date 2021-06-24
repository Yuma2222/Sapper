package Sapper;

import Sapper.gameSession.GameSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class sceneController {

    @FXML private Stage stage;
    @FXML private Scene scene;
    @FXML private Parent root;

    public void switchToMenu(KeyEvent event) throws IOException {
        root = new FXMLLoader(getClass().getResource("menu/menu.fxml")).load();
        switchTo(event.getSource());
   }

    public void switchToDifficultySetter(ActionEvent event) throws IOException {
        root = new FXMLLoader(getClass().getResource("difficultySetter/difficultySetter.fxml")).load();
        switchTo(event.getSource());
    }

    public void switchToScoreboard(ActionEvent event) throws IOException {
        root = new FXMLLoader(getClass().getResource("scoreBoard/scoreBoard.fxml")).load();
        switchTo(event.getSource());
    }

    public void switchToGameSession(ActionEvent event, String level) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Sapper/gameSession/gameSession.fxml"));
        root = loader.load();
        GameSession gameSession = loader.getController();

        switch (level){
            case "easy":
                gameSession.setHowManyInRow(10);
                gameSession.setBombsAmount(10);
                break;
            case "medium":
                gameSession.setHowManyInRow(20);
                gameSession.setBombsAmount(25);
                break;
            case "hard":
                gameSession.setHowManyInRow(30);
                gameSession.setBombsAmount(40);
                break;
        }

        gameSession.setDifficultyLevel(level);
        gameSession.setBombImage(new Image("file:src/resources/level-"+ level +".png"));
        gameSession.setFlagImage(new Image("file:src/resources/flag.png"));
        gameSession.createButtons();
        switchTo(event.getSource());
    }

    public void switchTo(Object source) {
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage = (Stage) ((Node)source).getScene().getWindow();
        stage.setWidth(1500);
        stage.setHeight(1000);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}