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
        resetBoard();
    }

    private void resetBoard() {
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
        board[col][row] = player;
    }

    public int getPlacementLocation(int col, Cell currentPlayer) {
        int whereToColor = 0;
        boolean found = false;
        int currentRow = rowNum - 1;
        while (!found && (currentRow != 0)) {
            if (board[col][currentRow] == Cell.EMPTY) {
                whereToColor = currentRow;
                found = true;
            }
            currentRow--;

        }
        return whereToColor;
    }

    public boolean isColFull(int col) {
        return board[col][0] != Cell.EMPTY;
    }

    public boolean isWinning(Cell player) {
        return verticalCheck(player) || horizontalCheck(player) || forwardDiagonalCheck(player) || backwardDiagonalCheck(player);
    }


    private boolean verticalCheck(Cell player) {
        for (int i = 0; i < getColNum() - 3; i++) {
            for (int j = 0; j < this.getRowNum(); j++) {
                if (this.board[i][j] == player && this.board[i + 1][j] == player && this.board[i + 2][j] == player && this.board[i + 3][j] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean horizontalCheck(Cell player) {
        // horizontalCheck
        for (int j = 0; j < getRowNum() - 3; j++) {
            for (int i = 0; i < getColNum(); i++) {
                if (this.board[i][j] == player && this.board[i][j + 1] == player && this.board[i][j + 2] == player && this.board[i][j + 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean forwardDiagonalCheck(Cell player) {
        for (int i = 3; i < getColNum(); i++) {
            for (int j = 0; j < getRowNum() - 3; j++) {
                if (this.board[i][j] == player && this.board[i - 1][j + 1] == player && this.board[i - 2][j + 2] == player && this.board[i - 3][j + 3] == player)
                    return true;
            }
        }
        return false;
    }

    private boolean backwardDiagonalCheck(Cell player) {
        for (int i = 3; i < getColNum(); i++) {
            for (int j = 3; j < getRowNum(); j++) {
                if (this.board[i][j] == player && this.board[i - 1][j - 1] == player && this.board[i - 2][j - 2] == player && this.board[i - 3][j - 3] == player)
                    return true;
            }
        }
        return false;
    }

}
