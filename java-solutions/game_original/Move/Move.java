package game_original.Move;

import game_original.Cell.Cell;
import game_original.Cell.CellCoordinates;

public class Move {
    protected CellCoordinates cellCoordinates;
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
        return new String("Move(" + value + ", " + cellCoordinates + ")");
    }
}
