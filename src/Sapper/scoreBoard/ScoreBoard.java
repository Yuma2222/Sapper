package Sapper.scoreBoard;

import Sapper.sceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ScoreBoard implements Initializable {
    @FXML protected Button returnBtn;
    @FXML private TableView<PersonalRecord> recordTable;
    @FXML private TableColumn<PersonalRecord,String> playerCol;
    @FXML private TableColumn<PersonalRecord,String> levelCol;
    @FXML private TableColumn<PersonalRecord,String> timeCol;
    private final ObservableList<PersonalRecord> usersList = FXCollections.observableArrayList();

    @FXML private void loadRecords() throws FileNotFoundException {
        FileReader fr = new FileReader("src/Sapper/scoreBoard/scoreBoard.txt");
        BufferedReader bfr = new BufferedReader(fr);
        try {
            String line = "";
            while((line = bfr.readLine()) != null){
               String[] elements = line.split(":");
               usersList.add(new PersonalRecord(elements[0], elements[1], elements[2]));
            }
        } catch (IOException e) {
            System.out.println("error!");
            System.exit(2);
        }
    }

    @FXML private void returnToMenu(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)){
            Sapper.sceneController sceneController = new sceneController();
            try {
                sceneController.switchToMenu(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recordTable.setItems(usersList);
        playerCol.setCellValueFactory(row -> row.getValue().nameProperty());
        levelCol.setCellValueFactory(row -> row.getValue().levelProperty());
        timeCol.setCellValueFactory(row -> row.getValue().timeProperty());
        try {
            loadRecords();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
