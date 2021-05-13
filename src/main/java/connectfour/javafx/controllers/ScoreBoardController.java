package connectfour.javafx.controllers;

import connectfour.results.GameResult;
import connectfour.results.GameResultDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
public class ScoreBoardController {

    @Inject
    FXMLLoader fxmlLoader;

    @FXML
    private TableView<GameResult> scoreBoard;

    @FXML
    private TableColumn<GameResult, String> player1;

    @FXML
    private TableColumn<GameResult, String> player2;

    @FXML

    private TableColumn<GameResult, String> winner;

    @FXML
    private TableColumn<GameResult, String> circlesPlaced;

    @FXML
    private TableColumn<GameResult, Duration> duration;

    @FXML
    private TableColumn<GameResult, ZonedDateTime> created;

    private GameResultDao gameResultDao;


    // TODO : implement scoreboardcontroller to display data stored on servers.
    // TODO : Add back to menu button to game scene.

    @FXML
    public void initialize() {
        gameResultDao = GameResultDao.getInstance();
        List<GameResult> results = gameResultDao.findAll();

        setPropertyValues();
        formatDurationValue();
        formatCreatedValue();

        ObservableList<GameResult> gameResultObservableArray = FXCollections.observableArrayList();
        gameResultObservableArray.addAll(results);
        scoreBoard.setItems(gameResultObservableArray);
    }

    private void setPropertyValues() {
        player1.setCellValueFactory(new PropertyValueFactory<>("player1"));
        player2.setCellValueFactory(new PropertyValueFactory<>("player2"));
        winner.setCellValueFactory(new PropertyValueFactory<>("winner"));
        circlesPlaced.setCellValueFactory(new PropertyValueFactory<>("circlesPlaced"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        created.setCellValueFactory(new PropertyValueFactory<>("created"));
    }

    private void formatDurationValue() {
        duration.setCellFactory(column -> new TableCell<GameResult, Duration>() {

            @Override
            protected void updateItem(Duration item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(DurationFormatUtils.formatDuration(item.toMillis(), "H:mm:ss"));
                }
            }
        });
    }

    private void formatCreatedValue() {
        created.setCellFactory(column -> new TableCell<GameResult, ZonedDateTime>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss Z");

            @Override
            protected void updateItem(ZonedDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.format(formatter));
                }
            }
        });
    }

    @FXML
    void handleBackToStartUpButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/startup.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}