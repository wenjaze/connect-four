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

import java.io.IOException;
import java.util.Objects;

/**
 * This class is responsible for the transitions between JavaFX scenes, so you don't have to
 * repeat yourself.
 */

@Slf4j
@Data
public class SceneHandler {

    private static FXMLLoader fxmlLoader = new FXMLLoader();

    /**
     * Switches the stage to the Scoreboard scene.
     *
     * @param actionEvent The event of clicking on the Scoreboard button
     * @throws IOException This is inherited from {@code FXMLloader.load()}
     */
    public static void switchToScoreBoard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(SceneHandler.class.getResource("/fxml/scoreboard.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Switching to ScoreBoardScene...");
    }

    /**
     * @param actionEvent The event of clicking on the Scoreboard button
     * @param playerName1 The name of player1.
     * @param playerName2 The name of player2.
     * @throws IOException This is inherited from {@code FXMLloader.load()}
     */
    public static void switchToGameScene(ActionEvent actionEvent, String playerName1, String playerName2) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneHandler.class.getResource("/fxml/game.fxml"));
        Parent root = fxmlLoader.load();
        fxmlLoader.<GameController>getController().initWithData(playerName1, playerName2);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Switching to GameScene...");
    }

    /**
     * Starts the {@link connectfour.javafx.controllers.StartUpController} scene.
     *
     * @param stage Needs a stage to put the scene to.
     * @throws IOException This comes from {@link FXMLLoader}.load().
     */
    public static void commenceWithStartUpScene(Stage stage) throws IOException {
        fxmlLoader.setLocation(SceneHandler.class.getResource("/fxml/startup.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("Connect Four");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Commencing application...");
    }

    /**
     * This function switches from another already open scene to
     * the StartUpScene.
     *
     * @param actionEvent Catches an event which it can bind the scene switch to.
     * @throws IOException Comes from {@link FXMLLoader}.
     */

    public static void switchToStartUpScene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(SceneHandler.class.getResource("/fxml/startup.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Switching to StartUpScene...");
    }


}
