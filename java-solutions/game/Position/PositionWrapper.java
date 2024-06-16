package game.Position;

import game.Cell.CellCoordinates;
import game.Cell.PlayerCell;
import game.Move.Move;

public class PositionWrapper {
    private Position position;

    public PlayerCell getTurn() {
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
