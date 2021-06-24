package Sapper.gameSession;

import Sapper.MediaPlayer;
import Sapper.sceneController;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class GameSession implements Initializable {

    @FXML
    private AnchorPane gameBoard;
    private int squareSize;
    private int howManyInRow;
    private int bombsAmount;
    private final int STAGE_SIZE = 1000;
    private String difficultyLevel;
    private Image bombImage;
    private Image flagImage;
    private Button actualButton;
    private String actualButtonID;
    private LocalDateTime start;
    private LocalDateTime finish;
    private final ArrayList<String> bombIDList = new ArrayList<>();
    private final ArrayList<String> flagIDList = new ArrayList<>();
    private final BufferedWriter writer = new BufferedWriter(new FileWriter("src/Sapper/scoreBoard/scoreBoard.txt", true));
    private final RotateTransition rotation = new RotateTransition();

    public void createButtons(){
        int x = 0;
        int y = 0;
        squareSize = STAGE_SIZE /howManyInRow;
        for(int i = 0; i < STAGE_SIZE; i+= squareSize){
            for(int j = 0; j < STAGE_SIZE; j += squareSize){
                Button button = new Button();
                rotation.setCycleCount(Animation.INDEFINITE);
                rotation.setCycleCount(1);
                rotation.setByAngle(90);
                rotation.setDuration(javafx.util.Duration.seconds(1));
                button.setId("btn:" + x + ":" + y);
                button.setUserData("btn:" + x + ":" + y);
                button.setPrefWidth(squareSize);
                button.setPrefHeight(squareSize);
                button.setLayoutX(i);
                button.setLayoutY(j);
                button.setOnMouseClicked(event -> {
                    actualButton = ((Button) event.getSource());
                    actualButtonID = actualButton.getId();
                    if (event.getButton() == MouseButton.PRIMARY) {

                        if (isThisPartOf(bombIDList, actualButtonID) == 0) {
                            if (isThisPartOf(flagIDList, actualButtonID) == 0){
                                setClue(actualButton);}
                        }else{
                            showBombs();
                        }

                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        setFlag();
                        try {
                            haveIWon();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                gameBoard.getChildren().add(button);
                y++;
            }
            y = 0;
            x++;
        }
        gameBoard.setStyle("-fx-background-color: transparent");
        setBombs();
        start = LocalDateTime.now();
    }

    private void setFlag(){
        ImageView flagImageView = new ImageView(flagImage);
        flagImageView.setFitWidth(squareSize - 15);
        flagImageView.setFitHeight(squareSize - 15);
        if(actualButton.getGraphic() == null){
            actualButton.setGraphic(flagImageView);
            flagIDList.add(actualButton.getId());
        }else{
            actualButton.setGraphic(null);
            flagIDList.remove(actualButton.getId());
        }
    }

    private void setClue(Node n){
        //for (Node n : gameBoard.getChildren()) {
           // if(buttonID.equals(n.getId())){
                int value = howManyBombsNear(n.getId());
                if(value != 0){
                    changeColour(value, n);
                    ((Button)n).setText(String.valueOf(value));
                }else{
                    n.setStyle(" -fx-opacity: 0.05 ");
                    rotation.setNode(n);
                    rotation.play();
                }
                n.setDisable(true);
            //}
       // }
        //String[] xy = actualButtonID.split(":");
        //int x =  Integer.parseInt(xy[1]);
        //int y =  Integer.parseInt(xy[2]);
        //setClue("btn:"+(x-1)+":"+(y-1));
        //setClue("btn:"+(x-1)+":"+y);
        //setClue("btn:"+(x-1)+":"+(y+1));
        //setClue("btn:"+x +":"+(y-1));
        //setClue("btn:"+x +":"+(y+1));
        //setClue("btn:"+(x+1)+":"+(y-1));
        //setClue("btn:"+(x+1)+":"+y);
        //setClue("btn:"+(x+1)+":"+(y+1));
    }

    private void changeColour(int value, Node n){
        switch (value){
            case 1:((Button)n).setTextFill(Paint.valueOf("blue")); break;
            case 2:((Button)n).setTextFill(Paint.valueOf("green")); break;
            case 3:((Button)n).setTextFill(Paint.valueOf("red")); break;
            case 4:((Button)n).setTextFill(Paint.valueOf("darkblue")); break;
            case 5:((Button)n).setTextFill(Paint.valueOf("darkred")); break;
            case 6:((Button)n).setTextFill(Paint.valueOf("teal")); break;
            case 7:((Button)n).setTextFill(Paint.valueOf("black")); break;
            case 8:((Button)n).setTextFill(Paint.valueOf("gray")); break;
        }
        n.setStyle("-fx-opacity:1.0; -fx-font-size:" + (squareSize - 75) +"; -fx-font-family: 'Helvetica', Arial, sans-serif;");
    }

    private void setBombs(){
        Random random = new Random();
        Set<String> bombIDSet = new TreeSet<>();
        int i = 0;
        while (i < bombsAmount){
            //eg.btn:25:25 <-- max in hard mode
            int newRandX = random.nextInt(howManyInRow);
            int newRandY = random.nextInt(howManyInRow);
            bombIDSet.add("btn:" + newRandX + ":" + newRandY);
            i++;
        }
        bombIDList.addAll(bombIDSet);
    }

    private int isThisPartOf(ArrayList<String> list, String buttonID){
        int i = 0;
        while (i < list.size()){
            if(buttonID.equals(list.get(i))){
                return 1;
            }
            i++;
        }
        return 0;
    }

    private int howManyBombsNear(String buttonID){
        String[] xy =  buttonID.split(":");
        int x =  Integer.parseInt(xy[1]);
        int y =  Integer.parseInt(xy[2]);
        return + isThisPartOf(bombIDList,"btn:"+(x-1)+":"+(y-1))
                + isThisPartOf(bombIDList,"btn:"+(x-1)+":"+y)
                + isThisPartOf(bombIDList,"btn:"+(x-1)+":"+(y+1))
                + isThisPartOf(bombIDList,"btn:"+x +":"+(y-1))
                + isThisPartOf(bombIDList,"btn:"+x +":"+(y+1))
                + isThisPartOf(bombIDList,"btn:"+(x+1)+":"+(y-1))
                + isThisPartOf(bombIDList,"btn:"+(x+1)+":"+y)
                + isThisPartOf(bombIDList,"btn:"+(x+1)+":"+(y+1));
    }

    private void showBombs(){
        int i = 0;
        while (i < bombIDList.size()){
            ImageView bombImageView = new ImageView(bombImage);
            bombImageView.setFitWidth(squareSize - 15);
            bombImageView.setFitHeight(squareSize - 15);
            for (Node n : gameBoard.getChildren()) {
                   n.setDisable(true);
                if (bombIDList.get(i).equals(n.getUserData())) {
                   ((Button) n).setGraphic(bombImageView);
                }
            }
            i++;
        }
        exitToMenu();
    }

    private void haveIWon() throws IOException {
        Collections.sort(bombIDList);
        Collections.sort(flagIDList);
        if(bombIDList.equals(flagIDList)){
            finish = LocalDateTime.now();
            promptUserNameInput();
        }
    }

    private void exitToMenu(){
        Button button = new Button();
        button.setPrefWidth(squareSize);
        button.setPrefHeight(squareSize);
        button.setText("Exit");
        button.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                Sapper.sceneController sceneController = new sceneController();
                try {
                    sceneController.switchToMenu(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        gameBoard.getChildren().add(button);
    }

    private void promptUserNameInput(){
        Duration duration = Duration.between(start, finish);
        long time = duration.toSeconds();
        System.out.println(time);
        TextField name = new TextField();
        name.setLayoutX(450);
        name.setLayoutY(450);
        name.setPrefWidth(squareSize);
        name.setPrefHeight(squareSize);
        name.setPromptText("Tell me your name: ");
        name.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                Sapper.sceneController sceneController = new sceneController();
                try {
                    saveToScoreboardFile(name.getText(), difficultyLevel, time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    sceneController.switchToMenu(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        gameBoard.getChildren().add(name);
    }

    private void saveToScoreboardFile(String name, String difficultyLevel, long timeInM) throws IOException {
        writer.write(name + ":" + difficultyLevel + ":" + timeInM);
        writer.newLine();
        writer.close();
    }

    //Setters and Constructor
    public GameSession() throws IOException {

    }

    public void setHowManyInRow(int howManyInRow) {
        this.howManyInRow = howManyInRow;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public void setBombImage(Image bombImage) {
        this.bombImage = bombImage;
    }

    public void setFlagImage(Image flagImage) {
        this.flagImage = flagImage;
    }

    public void setBombsAmount(int bombsAmount) {
        this.bombsAmount = bombsAmount;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.start();
    }
}