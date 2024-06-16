package game_original.Position;

import game_original.Cell.Cell;
import game_original.Cell.CellCoordinates;
import game_original.Move.Move;

public class PositionWrapper {
    private Position position;

    public Cell getTurn() {
        return position.getTurn();
    }

    public boolean isValid(Move move) {
        return position.isValid(move);
    }

    public PositionWrapper(Position position) {
        this.position = position;
    }

    public boolean isValidCoordinates(CellCoordinates cellCoordinates) {
        return position.isValidCoordinates(cellCoordinates);
    }

    public CellCoordinates getRandomCell() {
        return position.getRandomCell();
    }

    public CellCoordinates getNextFreeCell() {
        return position.getNextFreeCell();
    }
}
