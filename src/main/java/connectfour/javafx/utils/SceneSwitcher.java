package connectfour.javafx.utils;

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

@Slf4j
@Data
public class SceneSwitcher {

    @Inject
    private static FXMLLoader fxmlLoader = new FXMLLoader();

    private static void switchScene(String fxmlName, ActionEvent actionEvent) throws IOException {

        fxmlLoader.setLocation(SceneSwitcher.class.getResource("/fxml/" + fxmlName + ".fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void switchToGame(ActionEvent actionEvent) throws IOException {
        switchScene("game", actionEvent);
    }

    public static void switchToStartup(Stage stage) throws IOException {
        fxmlLoader.setLocation(SceneSwitcher.class.getResource("/fxml/startup.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("Connect Four");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void switchToScoreBoard(ActionEvent actionEvent) throws IOException {
        switchScene("scoreboard", actionEvent);
    }

}
