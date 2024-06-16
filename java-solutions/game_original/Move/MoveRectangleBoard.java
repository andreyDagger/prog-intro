package game_original.Move;

import game_original.Cell.Cell;
import game_original.Cell.CellCoordinatesRectangleBoard;

public class MoveRectangleBoard extends Move {

    public MoveRectangleBoard(CellCoordinatesRectangleBoard cellCoordinates, Cell value) {
        super(cellCoordinates, value);
    }
    public int getRow() {
        return ((CellCoordinatesRectangleBoard) cellCoordinates).getRow();
    }

    public int getCol() {
        return ((CellCoordinatesRectangleBoard) cellCoordinates).getCol();
    }

}
