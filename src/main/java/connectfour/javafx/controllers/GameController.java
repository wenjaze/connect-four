package connectfour.javafx.controllers;

import connectfour.models.BoardModel;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;

@Slf4j
public class GameController {

    private final BoardModel board = new BoardModel();
    @FXML
    private GridPane gridPane;

    @Inject
    FXMLLoader fxmlLoader;

    @FXML
    private void init() {
        for (int i = 0; i < gridPane.getRowCount(); i++) {
            for (int j = 0; j < gridPane.getColumnCount(); j++) {
                StackPane cell = addCell(i, j);
                gridPane.add(cell, i, j);
            }
        }
    }


    // TODO : implement ui.css

    private StackPane addCell(int i, int j) {
        StackPane cell = new StackPane();
        cell.getStyleClass().add("cell");

        Circle circle = new Circle(board.getCellSize());

        circle.fillProperty().bind(
                new ObjectBinding<Paint>() {
                    @Override
                    protected Paint computeValue() {
                        return switch (board.getCellFill(i, j)) {
                            case EMPTY -> Color.TRANSPARENT;
                            case RED -> Color.RED;
                            case BLUE -> Color.BLUE;
                        };
                    }
                }
        );

        cell.getChildren().add(circle);
        cell.setOnMouseClicked(this::handleMouseClick);

        return cell;
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        System.out.printf("Click on square (%d,%d)\n", row, col);
        board.move(row, col);
    }

}
