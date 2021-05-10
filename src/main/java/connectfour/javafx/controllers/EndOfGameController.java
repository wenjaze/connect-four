package connectfour.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;


public class EndOfGameController {

    @Inject
    FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private Button newGameButton;

    @FXML
    private Button viewScoreBoardButton;

    @FXML
    private Label congratsText;
    private String winnerColor;
    private String playerName;


    @FXML
    private void newGame(ActionEvent actionEvent) throws IOException {
        swapToGameScene(actionEvent);
    }

    @FXML
    private void viewScoreBoard(ActionEvent actionEvent) throws IOException {
        swapToScoreBoard(actionEvent);
    }

    public void setEndOfGameMessage(String winnerColor, String playerName) {
        this.winnerColor = winnerColor;
        this.playerName = playerName;
        congratsText.setText("Congratulations " + playerName + "! You have won!".toUpperCase());
    }

    private void swapToGameScene(ActionEvent actionEvent) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/" + "game" + ".fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

    private void swapToScoreBoard(ActionEvent actionEvent) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/" + "scoreboard" + ".fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
