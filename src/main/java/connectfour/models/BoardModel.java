package connectfour.models;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Model for the game board class.
 */

@Slf4j
@Data
public class BoardModel {

    private final int ROWNUM = 6;
    private final int COLNUM = 7;
    private int cellSize = 30;
    private Cell[][] board = new Cell[COLNUM][ROWNUM];


    /**
     * Creates a new instance of a {@link Cell} array, fills it with {@code Cell.EMPTY} cells.
     */
    public BoardModel() {
        resetBoard();
    }

    private void resetBoard() {
        for (int i = 0; i < COLNUM; i++) {
            for (int j = 0; j < ROWNUM; j++) {
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

    /**
     * @param col The current column the player wants to place a cell.
     * @return an integer which represents the row where the next free cell is in the
     * chosen column.
     */
    public int getPlacementLocation(int col) {
        int whereToColor = 0;
        boolean found = false;
        int currentRow = ROWNUM - 1;
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

    /**
     * Calls the functions: {@code verticalCheck()} , {@code horizontalCheck()} , {@code forwardDiagonalCheck()} , {@code backwardDiagonalCheck()}
     * and uses the OR operations between them, to check if we have 4 cells connected in the same color.
     *
     * @param player The current player color.
     * @return A boolean var whether someone won or not.
     */
    public boolean isWinning(Cell player) {
        return verticalCheck(player) || horizontalCheck(player) || forwardDiagonalCheck(player) || backwardDiagonalCheck(player);
    }

    private boolean verticalCheck(Cell player) {
        for (int i = 0; i < getCOLNUM() - 3; i++) {
            for (int j = 0; j < this.getROWNUM(); j++) {
                if (this.board[i][j] == player && this.board[i + 1][j] == player && this.board[i + 2][j] == player && this.board[i + 3][j] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean horizontalCheck(Cell player) {
        // horizontalCheck
        for (int j = 0; j < getROWNUM() - 3; j++) {
            for (int i = 0; i < getCOLNUM(); i++) {
                if (this.board[i][j] == player && this.board[i][j + 1] == player && this.board[i][j + 2] == player && this.board[i][j + 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean forwardDiagonalCheck(Cell player) {
        for (int i = 3; i < getCOLNUM(); i++) {
            for (int j = 0; j < getROWNUM() - 3; j++) {
                if (this.board[i][j] == player && this.board[i - 1][j + 1] == player && this.board[i - 2][j + 2] == player && this.board[i - 3][j + 3] == player)
                    return true;
            }
        }
        return false;
    }

    private boolean backwardDiagonalCheck(Cell player) {
        for (int i = 3; i < getCOLNUM(); i++) {
            for (int j = 3; j < getROWNUM(); j++) {
                if (this.board[i][j] == player && this.board[i - 1][j - 1] == player && this.board[i - 2][j - 2] == player && this.board[i - 3][j - 3] == player)
                    return true;
            }
        }
        return false;
    }

}
