package connectfour.javafx.controllers;

import connectfour.javafx.utils.SceneHandler;
import connectfour.models.BoardModel;
import connectfour.models.Cell;
import connectfour.results.GameResult;
import connectfour.results.GameResultDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Slf4j
public class GameController {

    @Inject
    FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private GridPane gridPane;
    private int circlePlaced;
    private Cell currentPlayer = Cell.EMPTY;
    private String playerName1;
    private String playerName2;
    private BoardModel board;
    private Instant gameStart;
    private GameResultDao gameResultDao;

    @FXML
    private void initialize() {
        circlePlaced = 0;
        gameResultDao = GameResultDao.getInstance();
        gameStart = Instant.now();
        board = new BoardModel();
        drawNewGameState();
        currentPlayer = Cell.RED;
    }

    private void drawNewGameState() {
        log.trace("Resetting board...");
        gridPane.getChildren().clear();
        log.trace("Done clearing..");
        for (int i = 0; i < board.getCOLNUM(); i++) {
            for (int j = 0; j < board.getROWNUM(); j++) {
                StackPane cell = addCell(board.getCellFill(i, j));
                gridPane.add(cell, i, j);
            }
        }
    }

    private GameResult getGameResults() {

        return GameResult.builder()
                .player1(playerName1)
                .player2(playerName2)
                .duration(Duration.between(gameStart, Instant.now()))
                .winner(getWinnerFromColor())
                .circlesPlaced(circlePlaced)
                .build();
    }

    private StackPane addCell(Cell cellColor) {
        StackPane cell = new StackPane();
        cell.getStyleClass().add("cell");
        cell.getChildren().add(createCircle(cellColor));
        cell.setOnMouseClicked(event -> {
            try {
                handleMouseClick(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return cell;
    }

    private Circle createCircle(Cell cellColor) {
        Circle circle = new Circle(board.getCellSize());
        circle.setFill(switch (cellColor) {
            case EMPTY -> Color.TRANSPARENT;
            case RED -> Color.rgb(255, 92, 92);
            case BLUE -> Color.rgb(79, 117, 255);
        });
        return circle;
    }

    private void switchPlayer() {
        currentPlayer = switch (currentPlayer) {
            case BLUE -> Cell.RED;
            case RED -> Cell.BLUE;
            case EMPTY -> throw new NullPointerException("currentPlayer cannot be empty!");
        };
    }

    private String getWinnerFromColor() {
        return switch (currentPlayer) {
            case BLUE -> playerName2;
            case RED -> playerName1;
            case EMPTY -> throw new NullPointerException("currentPlayer cannot be empty!");
        };
    }

    private void placeColoredCircle(int col) {
        int row = board.getPlacementLocation(col);
        board.setCell(col, row, currentPlayer);
        StackPane cell = addCell(currentPlayer);
        gridPane.add(cell, col, row);
        log.trace(currentPlayer + " circle placed at : (" + row + " - " + col + ")");
    }

    @FXML
    private void handleMouseClick(MouseEvent event) throws IOException {
        int col = getColFromMousePos(event);
        if (!board.isColFull(col)) {
            placeColoredCircle(col);
            if (board.isWinning(currentPlayer)) {
                doIfPlayerWon(currentPlayer);
            } else {
                circlePlaced++;
                switchPlayer();
            }
        } else {
            showAlert("Can't place circle", "Column " + (col + 1) + " is full.", Alert.AlertType.INFORMATION);
        }
    }


    private void doIfPlayerWon(Cell winner) {
        GameResult actualGameResult = getGameResults();
        log.info(actualGameResult.getPlayer1(), actualGameResult.getPlayer2(), actualGameResult.getWinner());
        gameResultDao.persist(getGameResults());
        showAlert("Congratulations!", getWinnerFromColor() + " has won the game!", Alert.AlertType.CONFIRMATION);
        board = new BoardModel();
        drawNewGameState();
        currentPlayer = Cell.RED;
    }

    private int getColFromMousePos(MouseEvent event) {
        StackPane square = (StackPane) event.getSource();
        return GridPane.getColumnIndex(square);
    }

    private void showAlert(String headerMessage, String alertMessage, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerMessage);
        alert.setContentText(alertMessage);
        alert.showAndWait();
        log.trace(alertMessage);
    }

    public void initWithData(String player1, String player2) {
        playerName1 = player1;
        playerName2 = player2;
    }


    public void handleBackToStartUpButton(ActionEvent actionEvent) throws IOException {
        SceneHandler.switchToStartUpScene(actionEvent);
    }

    public void handleResetButton(ActionEvent actionEvent) {
        initialize();
    }
}
