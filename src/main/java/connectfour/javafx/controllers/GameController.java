package connectfour.javafx.controllers;

import connectfour.javafx.utils.data.PlayerData;
import connectfour.models.BoardModel;
import connectfour.models.Cell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.IOException;

@Slf4j
public class GameController {

    @Inject
    FXMLLoader fxmlLoader = new FXMLLoader();
    private String playerName1;

    private BoardModel board = new BoardModel();

    @FXML
    private GridPane gridPane;

    private Cell currentPlayer = Cell.EMPTY;
    private String playerName2;

    @FXML
    private void initialize() {
        drawNewGameState();
        System.out.println(PlayerData.getPlayerName1());
        currentPlayer = Cell.RED;
    }

    private void drawNewGameState() {
        log.trace("Resetting board...");
        gridPane.getChildren().clear();
        log.trace("Done clearing..");
        for (int i = 0; i < board.getColNum(); i++) {
            for (int j = 0; j < board.getRowNum(); j++) {
                StackPane cell = addCell(board.getCellFill(i, j));
                gridPane.add(cell, i, j);
            }
        }
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
            case RED -> Color.RED;
            case BLUE -> Color.BLUE;
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
        int row = board.getPlacementLocation(col, currentPlayer);
        board.setCell(row, col, currentPlayer);
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
                switchPlayer();
            }
        } else {
            showAlert("Can't place circle", "Column " + (col + 1) + " is full.", Alert.AlertType.INFORMATION);
        }
    }

    private void doIfPlayerWon(Cell winner) {
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

    private void swapEndOfGameScene(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/endofgame.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.trace("Finished game, loading Top Ten scene.");
    }

    public void initWithData(String player1, String player2) {
        playerName1 = player1;
        playerName2 = player2;
    }
}
