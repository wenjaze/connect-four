package connectfour.javafx.controllers;

import connectfour.models.BoardModel;
import connectfour.models.Cell;
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

@Slf4j
public class GameController {

    private final BoardModel board = new BoardModel();
    @FXML
    private GridPane gridPane;

    private Cell currentPlayer = Cell.EMPTY;

    @Inject
    FXMLLoader fxmlLoader;


    @FXML
    private void initialize() {
        for (int i = 0; i < board.getRowNum(); i++) {
            for (int j = 0; j < board.getColNum(); j++) {
                StackPane cell = addCell(currentPlayer);
                gridPane.add(cell, j, i);
            }
        }
        currentPlayer = Cell.RED;
    }

    // TODO : implement ui.css

    private StackPane addCell(Cell cellColor) {

        Circle circle = new Circle(board.getCellSize());
        circle.setFill(switch (cellColor) {
            case EMPTY -> Color.TRANSPARENT;
            case RED -> Color.RED;
            case BLUE -> Color.BLUE;
        });

        StackPane cell = new StackPane();
        //cell.getStyleClass().add("cell");
        cell.getChildren().add(circle);
        cell.setOnMouseClicked(this::handleMouseClick);

        return cell;
    }

    private void switchPlayer() {
        currentPlayer = switch (currentPlayer) {
            case BLUE -> Cell.RED;
            case RED -> Cell.BLUE;
            case EMPTY -> throw new NullPointerException("currentPlayer cannot be empty!");
        };
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        int col = getColFromMousePos(event);
        if (!board.isColFull(col)) {
            int row = board.putCircle(col, currentPlayer);
            board.setCell(row, col, currentPlayer);
            StackPane cell = addCell(currentPlayer);
            log.trace(currentPlayer + " circle placed at : (" + row + " - " + col + ")");
            gridPane.add(cell, col, row);
            log.trace("win ? " + board.areFourConnected(currentPlayer));
            switchPlayer();
        } else {
            showAlert("Can't place circle", "Column " + (col + 1) + " is full.", Alert.AlertType.INFORMATION);
        }
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
}
