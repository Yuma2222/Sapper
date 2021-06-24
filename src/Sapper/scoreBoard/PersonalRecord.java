package Sapper.scoreBoard;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonalRecord {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty level = new SimpleStringProperty();
    private final StringProperty time = new SimpleStringProperty();

    protected PersonalRecord(String name, String level, String time) {
        this.name.set(name);
        this.level.set(level);
        this.time.set(time);
    }

    protected StringProperty nameProperty() {
        return name;
    }


    protected StringProperty levelProperty() {
        return level;
    }


    protected StringProperty timeProperty() {
        return time;
    }

}
