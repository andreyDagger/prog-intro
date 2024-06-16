package game.Cell;

public class CellCoordinatesRectangleBoard implements CellCoordinates {
    private final int row;
    private final int col;

    public CellCoordinatesRectangleBoard(int row, int col) {
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return (row + 1) + ", " + (col + 1);
    }
}
