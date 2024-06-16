package game_original.Field;

import game_original.Cell.Cell;

public interface RectangleField extends Field {
    int getHeight();
    int getWidth();

    Cell getCell(int row, int col);
}
