package game_original.Position;

import game_original.Cell.Cell;
import game_original.Cell.CellCoordinates;
import game_original.Move.Move;

public interface Position {
    Cell getTurn();

    boolean isValid(Move move);
    boolean isValidCoordinates(CellCoordinates cellCoordinates);

    CellCoordinates getRandomCell();
    CellCoordinates getNextFreeCell();
}
