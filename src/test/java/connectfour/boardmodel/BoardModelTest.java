package connectfour.boardmodel;

import connectfour.models.BoardModel;
import connectfour.models.Cell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardModelTest {

    private BoardModel testBoardModel = new BoardModel();
    private final Cell[][] testBoard = testBoardModel.getBoard();
    private final Cell testCurrentPlayer = Cell.RED;

    @Test
    void testBoardObjectCreation() {
        assertEquals(testBoardModel.getROWNUM(), 6);
        assertEquals(testBoardModel.getCOLNUM(), 7);
    }

    @Test
    void testCellColorAfterInitialization() {
        for (int i = 0; i < testBoardModel.getCOLNUM(); i++) {
            for (int j = 0; j < testBoardModel.getROWNUM(); j++) {
                assertEquals(testBoard[i][j], Cell.EMPTY);
            }
        }
    }

    @Test
    void testSetCellFunction() {
        for (int i = 0; i < testBoardModel.getCOLNUM(); i++) {
            for (int j = 0; j < testBoardModel.getROWNUM(); j++) {
                testBoardModel.setCell(i, j, Cell.RED);
                assertNotEquals(testBoard[i][j], Cell.EMPTY);
            }
        }
    }

    @Test
    void testInvalidIndexCellInitialization() {
        assertThrows(IndexOutOfBoundsException.class, () -> testBoardModel.setCell(-1, 0, Cell.RED));
        assertThrows(IndexOutOfBoundsException.class, () -> testBoardModel.setCell(0, -1, Cell.RED));
        assertThrows(IndexOutOfBoundsException.class, () -> testBoardModel.setCell(7, 0, Cell.RED));
        assertThrows(IndexOutOfBoundsException.class, () -> testBoardModel.setCell(0, 6, Cell.RED));
    }


    @Test
    void testHorizontalCheck() {
        testBoardModel.setCell(0, 0, testCurrentPlayer);
        testBoardModel.setCell(0, 1, testCurrentPlayer);
        testBoardModel.setCell(0, 2, testCurrentPlayer);
        testBoardModel.setCell(0, 3, testCurrentPlayer);

        assertTrue(testBoardModel.isWinning(testCurrentPlayer));
    }

    @Test
    void testVerticalCheck() {
        testBoardModel.setCell(0, 0, testCurrentPlayer);
        testBoardModel.setCell(1, 0, testCurrentPlayer);
        testBoardModel.setCell(2, 0, testCurrentPlayer);
        testBoardModel.setCell(3, 0, testCurrentPlayer);

        assertTrue(testBoardModel.isWinning(testCurrentPlayer));
    }

    @Test
    void testForwardDiagonalCheck() {
        testBoardModel.setCell(0, 0, testCurrentPlayer);
        testBoardModel.setCell(1, 1, testCurrentPlayer);
        testBoardModel.setCell(2, 2, testCurrentPlayer);
        testBoardModel.setCell(3, 3, testCurrentPlayer);

        assertTrue(testBoardModel.isWinning(testCurrentPlayer));
    }

    @Test
    void testBackwardDiagonalCheck() {
        testBoardModel.setCell(6, 0, testCurrentPlayer);
        testBoardModel.setCell(5, 1, testCurrentPlayer);
        testBoardModel.setCell(4, 2, testCurrentPlayer);
        testBoardModel.setCell(3, 3, testCurrentPlayer);

        assertTrue(testBoardModel.isWinning(testCurrentPlayer));
    }

    @Test
    void testPlacementLocation() {
        testBoardModel = new BoardModel();
        testBoardModel.setCell(0, 0, testCurrentPlayer);
        testBoardModel.setCell(0, 1, testCurrentPlayer);
        testBoardModel.setCell(0, 2, testCurrentPlayer);
        testBoardModel.setCell(0, 3, testCurrentPlayer);
        testBoardModel.setCell(0, 4, testCurrentPlayer);

        assertEquals(testBoardModel.getPlacementLocation(0), 5);

        testBoardModel.setCell(1, 0, testCurrentPlayer);
        testBoardModel.setCell(1, 1, testCurrentPlayer);
        testBoardModel.setCell(1, 2, testCurrentPlayer);
        testBoardModel.setCell(1, 3, testCurrentPlayer);
        testBoardModel.setCell(1, 4, testCurrentPlayer);

        assertEquals(testBoardModel.getPlacementLocation(1), 5);

    }
}
