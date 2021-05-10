package connectfour.javafx.controllers;

import connectfour.javafx.utils.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.IOException;

@Slf4j
public class StartUpController {

    @Inject
    FXMLLoader fxmlLoader = new FXMLLoader();

    public void handleSwitchToGameButton(ActionEvent actionEvent) throws IOException {
        log.info("Switching to GameScene...");
        swapToGameScene(actionEvent);
    }

    private void swapToGameScene(ActionEvent actionEvent) throws IOException {
        fxmlLoader.setLocation(SceneHandler.class.getResource("/fxml/" + "game" + ".fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
