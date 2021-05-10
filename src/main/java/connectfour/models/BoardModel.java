package connectfour.models;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BoardModel {

    private final int rowNum = 6;
    private final int colNum = 7;
    private final double cellSize = 30;

    private Cell[][] board = new Cell[colNum][rowNum];

    public BoardModel() {
        for (int i = 0; i < colNum; i++) {
            for (int j = 0; j < rowNum; j++) {
                board[i][j] = Cell.EMPTY;
            }
        }
    }

    public Cell getCellFill(int i, int j) {
        return board[i][j];
    }

    public void setCell(int row, int col, Cell player) {
        board[row][col] = player;
    }

    public int putCircle(int col, Cell currentPlayer) {
        int whereToColor = 0;
        boolean found = false;
        int currentRow = colNum - 1;
        while (!found && (currentRow != 0)) {
            if (board[currentRow][col] == Cell.EMPTY) {
                whereToColor = currentRow;
                found = true;
            }
            currentRow--;

        }
        return whereToColor;
    }

    public boolean isColFull(int col) {
        return board[0][col] != Cell.EMPTY;
    }

    public boolean areFourConnected(Cell player) {

        // horizontalCheck
        for (int j = 0; j < getRowNum() - 3; j++) {
            for (int i = 0; i < getColNum(); i++) {
                if (this.board[i][j] == player && this.board[i][j + 1] == player && this.board[i][j + 2] == player && this.board[i][j + 3] == player) {
                    return true;
                }
            }
        }
        // verticalCheck
        for (int i = 0; i < getColNum() - 3; i++) {
            for (int j = 0; j < this.getRowNum(); j++) {
                if (this.board[i][j] == player && this.board[i + 1][j] == player && this.board[i + 2][j] == player && this.board[i + 3][j] == player) {
                    return true;
                }
            }
        }
        // ascendingDiagonalCheck
        for (int i = 3; i < getColNum(); i++) {
            for (int j = 0; j < getRowNum() - 3; j++) {
                if (this.board[i][j] == player && this.board[i - 1][j + 1] == player && this.board[i - 2][j + 2] == player && this.board[i - 3][j + 3] == player)
                    return true;
            }
        }
        // descendingDiagonalCheck
        for (int i = 3; i < getColNum(); i++) {
            for (int j = 3; j < getRowNum(); j++) {
                if (this.board[i][j] == player && this.board[i - 1][j - 1] == player && this.board[i - 2][j - 2] == player && this.board[i - 3][j - 3] == player)
                    return true;
            }
        }
        return false;
    }

}
