package game.Position;

import game.Cell.CellCoordinates;
import game.Cell.PlayerCell;
import game.Move.Move;

public interface Position {
    PlayerCell getTurn();

    boolean isValid(Move move);

    boolean isValidCoordinates(CellCoordinates cellCoordinates);

    CellCoordinates getRandomCell();

    CellCoordinates getNextFreeCell();
}
