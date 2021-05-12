package connectfour.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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

    public void handleSwitchToGameButton(ActionEvent actionEvent) throws IOException {
        log.info("Switching to GameScene...");
        swapToGameScene(actionEvent);
    }

    private void swapToGameScene(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
        Parent root = fxmlLoader.load();
        fxmlLoader.<GameController>getController().initWithData(player1.getText(), player2.getText());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.trace("Finished game, loading Top Ten scene.");
    }

}
