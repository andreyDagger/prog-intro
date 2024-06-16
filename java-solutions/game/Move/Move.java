package game.Move;

import game.Cell.Cell;
import game.Cell.CellCoordinates;

public class Move {
    private final CellCoordinates cellCoordinates;
    private final Cell value;

    public Move(CellCoordinates cellCoordinates, Cell value) {
        this.cellCoordinates = cellCoordinates;
        this.value = value;
    }

    public CellCoordinates getCellCoordinates() {
        return cellCoordinates;
    }

    public Cell getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Move(" + value + ", " + cellCoordinates + ")";
    }
}
