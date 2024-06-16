package game_original.Board;

import java.util.Arrays;
import java.util.Random;

import game_original.Cell.Cell;
import game_original.Cell.CellCoordinates;
import game_original.Cell.CellCoordinatesRectangleBoard;
import game_original.Field.RectangleField;
import game_original.Position.Position;
import game_original.Position.PositionRectangleBoard;

public abstract class RectangleBoard implements Board, RectangleField, PositionRectangleBoard {
    protected int height;
    protected int width;
    protected Cell[][] field;
    protected Cell turn;

    protected RectangleBoard(int height, int width) {
        this.height = height;
        this.width = width;
        field = new Cell[height][width];
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.EMPTY);
        }
        turn = Cell.PLAYER1_CELL;
    }

    public RectangleField getRectangleField() {
        return this;
    }

    @Override
    public Cell getCell(int row, int col) {
        return field[row][col];
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public boolean isValidCoordinates(CellCoordinates cellCoordinates) {
        assert (cellCoordinates instanceof CellCoordinatesRectangleBoard);

        CellCoordinatesRectangleBoard coordinates = (CellCoordinatesRectangleBoard) cellCoordinates;
        return coordinates.getRow() >= 0 && coordinates.getCol() >= 0 &&
                coordinates.getRow() < getHeight() && coordinates.getCol() < getWidth() &&
                field[coordinates.getRow()][coordinates.getCol()] == Cell.EMPTY;
    }

    @Override
    public CellCoordinates getRandomCell() {
        Random random = new Random();
        return new CellCoordinatesRectangleBoard(random.nextInt(height), random.nextInt(width));
    }

    @Override
    public CellCoordinates getNextFreeCell() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (field[row][col] == Cell.EMPTY) {
                    return new CellCoordinatesRectangleBoard(row, col);
                }
            }
        }
        throw new AssertionError("No free cells");
    }
}
