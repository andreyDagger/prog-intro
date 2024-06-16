package game.Board;

import game.Cell.*;
import game.Field.Field;
import game.Field.RectangleField;
import game.GameConfiguration.RectangleBoardGameConfiguration;
import game.Position.Position;
import game.Position.PositionRectangleBoard;

import java.util.Arrays;
import java.util.Random;

public abstract class RectangleBoard extends Board implements RectangleField, PositionRectangleBoard {
    protected int height;
    protected int width;
    protected Cell[][] field;

    protected RectangleBoard(RectangleBoardGameConfiguration gameConfiguration) {
        super(gameConfiguration);
        this.height = gameConfiguration.getN();
        this.width = gameConfiguration.getM();
        field = new Cell[height][width];
        for (Cell[] row : field) {
            Arrays.fill(row, new EmptyCell());
        }
    }

    public RectangleField getRectangleField() {
        return this;
    }

    @Override
    public Field getField() {
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
    public Position getPosition() {
        return this;
    }

    @Override
    public boolean isValidCoordinates(CellCoordinates cellCoordinates) {
        CellCoordinatesRectangleBoard coordinates = (CellCoordinatesRectangleBoard) cellCoordinates;
        return coordinates.getRow() >= 0 && coordinates.getCol() >= 0 &&
                coordinates.getRow() < getHeight() && coordinates.getCol() < getWidth() &&
                field[coordinates.getRow()][coordinates.getCol()].isEmptyCell();
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
                if (field[row][col].isEmptyCell()) {
                    return new CellCoordinatesRectangleBoard(row, col);
                }
            }
        }
        throw new AssertionError("No free cells");
    }
}
