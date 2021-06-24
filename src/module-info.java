module Sapper {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    opens Sapper.menu;
    opens Sapper.difficultySetter;
    opens Sapper.gameSession;
    opens Sapper.scoreBoard;
    opens Sapper;
}