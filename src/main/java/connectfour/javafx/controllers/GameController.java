package connectfour.javafx.controllers;

import javafx.fxml.FXMLLoader;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;

@Slf4j
public class GameController {

    @Inject
    FXMLLoader fxmlLoader;

    void switchToScoreboard() {
        fxmlLoader.setLocation(getClass().getResource("/fxml/scoreboard.fxml"));
    }

}
