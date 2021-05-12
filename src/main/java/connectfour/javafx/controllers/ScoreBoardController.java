package connectfour.javafx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.awt.event.ActionEvent;
import java.io.IOException;


@Slf4j
public class ScoreBoardController {

    @Inject
    FXMLLoader fxmlLoader;

    @FXML
    void handleBackToGameButton(ActionEvent actionEvent) throws IOException {
        log.info("Switching back to game scene...");
    }

}