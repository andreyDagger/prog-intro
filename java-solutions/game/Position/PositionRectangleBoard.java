package game.Position;

import game.Cell.Cell;

public interface PositionRectangleBoard extends Position {
    Cell getCell(int row, int column);

    int getHeight();

    int getWidth();
}
