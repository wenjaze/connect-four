package connectfour.models;

import lombok.Data;

@Data
public class BoardModel {

    private final int rowNum = 6;
    private final int colNum = 7;
    private final double cellSize = 30;

    private Cell[][] board = new Cell[rowNum][colNum];

    public BoardModel() {
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                board[i][j] = Cell.EMPTY;
            }
        }
    }

    public Cell getCellFill(int i, int j) {
        return board[i][j];
    }

}
