package connectfour.javafx.controllers;

import connectfour.javafx.utils.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.IOException;

@Slf4j
public class StartUpController {

    @Inject
    FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private TextField player1;

    @FXML
    private TextField player2;

    @FXML
    private Label requirementLabel;

    private String playerName1;
    private String playerName2;

    /**
     * Checks if playerName fields are empty, proceeds if they're filled.
     *
     * @param actionEvent Getting the actionEvent from fxml the button.
     * @throws IOException Default exception for FXMLLoader
     */
    @FXML
    public void handleSwitchToGameButton(ActionEvent actionEvent) throws IOException {
        if (player1.getText().isEmpty() || player2.getText().isEmpty()) {
            requirementLabel.setText("* Fill out player names first");
            log.trace("Fill player names field.");
        } else {
            setPlayerNames();
            SceneHandler.switchToGameScene(actionEvent, playerName1, playerName2);
        }
    }

    /**
     * Helper function for setting playerNames
     */

    private void setPlayerNames() {
        playerName1 = player1.getText();
        playerName2 = player2.getText();
    }

    @FXML
    public void handleSwitchToScoreBoardButton(ActionEvent actionEvent) throws IOException {
        SceneHandler.switchToScoreBoard(actionEvent);
    }

}
