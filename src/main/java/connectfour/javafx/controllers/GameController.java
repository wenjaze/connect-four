package connectfour.javafx.controllers;

import connectfour.javafx.utils.SceneHandler;
import connectfour.models.BoardModel;
import connectfour.models.Cell;
import connectfour.results.GameResult;
import connectfour.results.GameResultDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

/**
 * This controller handles the game states, using the {@link BoardModel}.
 * Recording data using the {@link GameResultDao} DAO class, extension of the abstract class {@code GenericJpaDao}.
 */
@Slf4j
public class GameController {

    @FXML
    private GridPane gridPane;
    private int circlePlaced;
    private Cell currentPlayer = Cell.EMPTY;
    private String playerName1;
    private String playerName2;
    private BoardModel board;
    private Instant gameStart;
    private GameResultDao gameResultDao;

    /**
     * Initializing the game board, instantiating {@link GameResultDao} Singleton class
     * and setting the current player to {@code Cell.RED}.
     */
    @FXML
    private void initialize() {
        circlePlaced = 0;
        gameResultDao = GameResultDao.getInstance();
        gameStart = Instant.now();
        board = new BoardModel();
        drawNewGameState();
        currentPlayer = Cell.RED;
    }

    /**
     * Clearing the {@code gridPane} and filling up the gridPane view
     * with cells.
     */
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

    /**
     *
     * Returns a GameResult pojo object which is string data
     * about the current game state.
     * @return a {@link GameResult} object.
     */
    private GameResult getGameResults() {
        return GameResult.builder()
                .player1(playerName1)
                .player2(playerName2)
                .duration(Duration.between(gameStart, Instant.now()))
                .winner(getWinnerFromColor())
                .circlesPlaced(circlePlaced)
                .build();
    }

    /**
     *
     * Creates a stackPane object which you can add to gridPane.
     * @param cellColor The color to set the cell to.
     * @return {@link StackPane} cell object
     */
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

    /**
     * Creates a Circle shape object, which you can add to {@link StackPane} later
     * @param cellColor Color of the circle you want to add.
     * @return A circle shape JavaFX object.
     */
    private Circle createCircle(Cell cellColor) {
        Circle circle = new Circle(board.getCellSize());
        circle.setFill(switch (cellColor) {
            case EMPTY -> Color.TRANSPARENT;
            case RED -> Color.rgb(255, 92, 92);
            case BLUE -> Color.rgb(79, 117, 255);
        });
        return circle;
    }

    /**
     * Switches the player to the opposite color.
     * (helper function)
     */
    private void switchPlayer() {
        currentPlayer = switch (currentPlayer) {
            case BLUE -> Cell.RED;
            case RED -> Cell.BLUE;
            case EMPTY -> throw new NullPointerException("currentPlayer cannot be empty!");
        };
    }

    /**
     * Switches the player to the opposite color.
     * (helper function)
     * @return a {@link Cell} type object.
     */
    private String getWinnerFromColor() {
        return switch (currentPlayer) {
            case BLUE -> playerName2;
            case RED -> playerName1;
            case EMPTY -> throw new NullPointerException("currentPlayer cannot be empty!");
        };
    }

    /**
     * This function is used to placed a cell of a specific color to the board.
     * Uses the {@code BoardModel.getPlacementLocation()} function to determine where
     * to place the cell.
     *
     * @param col The current column the player wants to place a Circle to.
     */
    private void placeColoredCircle(int col) {
        int row = board.getPlacementLocation(col);
        board.setCell(col, row, currentPlayer);
        StackPane cell = addCell(currentPlayer);
        gridPane.add(cell, col, row);
        log.trace(currentPlayer + " circle placed at : (" + row + " - " + col + ")");
    }

    /**
     * This gets called when a player clicks on row.
     * If its not full, then the currentPlayer places a cell,
     * otherwise it throws an alert.
     * <p>
     * Checks for {@code BoardModel.isWinning(currentPlayer)} every click.
     *
     * @param event Event of the mouseClick to get the clicked column from.
     * @throws IOException as default MouseEvent exception.
     */
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

    /**
     * Creates {@link GameResult} instance, to get the data to the JPA.
     * Persists the {@link GameResult} class.
     * Intantiates a new empty {@link BoardModel} class object, and fills the View according to it.
     *
     * @param winner To show the name in the winning screen alert.
     */
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

    /**
     * Helper function to create an alert popup window.
     *
     * @param headerMessage Content of the header message.
     * @param alertMessage  Main content of the alert message.
     * @param alertType     Type of the alert.
     */

    private void showAlert(String headerMessage, String alertMessage, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerMessage);
        alert.setContentText(alertMessage);
        alert.showAndWait();
        log.trace(alertMessage);
    }

    /**
     * A function to solve transmitting data between FXML {@link Scene}s.
     *
     * @param player1 Name of player1.
     * @param player2 Name of player2.
     */
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
