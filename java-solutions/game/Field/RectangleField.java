package game.Field;

import game.Cell.Cell;

public interface RectangleField extends Field {
    int getHeight();

    int getWidth();

    Cell getCell(int row, int col);
}
