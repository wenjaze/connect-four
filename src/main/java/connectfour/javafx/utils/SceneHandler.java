package connectfour.javafx.utils;

import connectfour.javafx.controllers.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Data
public class SceneHandler {

    @Inject
    private static FXMLLoader fxmlLoader = new FXMLLoader();

    public static void switchToScoreBoard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(SceneHandler.class.getResource("/fxml/scoreboard.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Switching to ScoreBoardScene...");
    }

    public static void switchToGameScene(ActionEvent actionEvent, String playerName1, String playerName2) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneHandler.class.getResource("/fxml/game.fxml"));
        Parent root = fxmlLoader.load();
        fxmlLoader.<GameController>getController().initWithData(playerName1, playerName2);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Switching to GameScene...");
    }

    public static void commenceWithStartUpScene(Stage stage) throws IOException {
        fxmlLoader.setLocation(SceneHandler.class.getResource("/fxml/startup.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("Connect Four");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Commencing application...");
    }

    public static void switchToStartUpScene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(SceneHandler.class.getResource("/fxml/startup.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Switching to StartUpScene...");
    }


}
