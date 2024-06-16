package game_original.Position;

import game_original.Cell.Cell;

public interface PositionRectangleBoard extends Position {
    Cell getCell(int row, int column);

    int getHeight();
    int getWidth();
}
