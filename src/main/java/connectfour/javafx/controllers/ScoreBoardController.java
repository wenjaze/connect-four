package connectfour.javafx.controllers;

import connectfour.javafx.utils.SceneHandler;
import connectfour.results.GameResult;
import connectfour.results.GameResultDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controls the scoreBoard, gets data from GameResultDAO,
 * and adapts it into the {@link TableView} view.
 */

@Slf4j
public class ScoreBoardController {

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
    private TableColumn<GameResult, ZonedDateTime> date;


    /**
     * Instantiate a {@link GameResultDao} Singleton class.
     * Gathers the information through JPA.
     */
    @FXML
    public void initialize() {
        GameResultDao gameResultDao = GameResultDao.getInstance();
        List<GameResult> results = gameResultDao.findAll();

        setPropertyValues();
        formatDurationValue();
        formatDateValue();

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
        date.setCellValueFactory(new PropertyValueFactory<>("created"));
    }

    private void formatDurationValue() {
        duration.setCellFactory(column -> new TableCell<>() {

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

    private void formatDateValue() {
        date.setCellFactory(column -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss");

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
        SceneHandler.switchToStartUpScene(event);
    }
}
