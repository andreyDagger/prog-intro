package game.Board;

import game.Cell.BannedCell;
import game.Cell.CellCoordinates;
import game.Cell.CellCoordinatesRectangleBoard;
import game.CoordinatesParser.ParsingCoordinatesException;
import game.Field.RectangleField;
import game.GameConfiguration.GameConfiguration;
import game.GameConfiguration.TicTacToeGameConfiguration;
import game.Move.Move;
import game.Move.MoveResult;
import game.Position.PositionRectangleBoard;

import java.io.StringReader;
import java.util.List;
import java.util.Scanner;

public class TicTacToeBoard extends RectangleBoard implements PositionRectangleBoard, RectangleField {
    private int filledCellsCount;
    private final List<CellCoordinatesRectangleBoard> bannedCells;
    private final int k;

    public TicTacToeBoard(TicTacToeGameConfiguration ticTacToeGameConfiguration, List<CellCoordinatesRectangleBoard> bannedCells) {
        super(ticTacToeGameConfiguration);

        this.k = ticTacToeGameConfiguration.getK();
        this.bannedCells = bannedCells;
        for (CellCoordinatesRectangleBoard coordinates : bannedCells) {
            field[coordinates.getRow()][coordinates.getCol()] = new BannedCell();
        }
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if (field[row][col].isBannedCell()) {
                    filledCellsCount++;
                }
            }
        }
    }

    @Override
    public Board changeGameConfiguration(GameConfiguration gameConfiguration) {
        if (gameConfiguration instanceof TicTacToeGameConfiguration ticTacToeGameConfiguration) {
            return new TicTacToeBoard(ticTacToeGameConfiguration, bannedCells);
        }
        throw new AssertionError("gameConfiguration must be instance of TicTacToeGameConfiguration");
    }

    @Override
    public Board getCopy() {
        return new TicTacToeBoard((TicTacToeGameConfiguration) gameConfiguration, bannedCells);
    }

    @Override
    public MoveResult makeMove(Move move) {
        CellCoordinatesRectangleBoard coordinates = (CellCoordinatesRectangleBoard) move.getCellCoordinates();
        if (!isValid(move)) {
            return MoveResult.LOSE;
        }

        filledCellsCount++;
        field[coordinates.getRow()][coordinates.getCol()] = move.getValue();
        if (checkWin(move)) {
            return MoveResult.WIN;
        }

        if (checkDraw()) {
            return MoveResult.DRAW;
        }

        return MoveResult.NOTHING;
    }

    private boolean checkDraw() {
        return filledCellsCount == height * width;
    }

    private boolean checkWinDirection(CellCoordinatesRectangleBoard coordinates, int directionRow, int directionCol) {
        int countCells = 0;
        for (int col = coordinates.getCol(), row = coordinates.getRow();
             col >= 0 && row >= 0 && col < width && row < height;
             col += directionCol, row += directionRow) {
            if (!field[row][col].equals(field[coordinates.getRow()][coordinates.getCol()])) {
                break;
            }
            countCells++;
        }
        for (int col = coordinates.getCol(), row = coordinates.getRow();
             col >= 0 && row >= 0 && col < width && row < height;
             col -= directionCol, row -= directionRow) {
            if (!field[row][col].equals(field[coordinates.getRow()][coordinates.getCol()])) {
                break;
            }
            countCells++;
        }
        return countCells - 1 == k;
    }

    private boolean checkWin(Move move) {
        CellCoordinatesRectangleBoard coordinates = (CellCoordinatesRectangleBoard) move.getCellCoordinates();
        return checkWinDirection(coordinates, 1, 0) ||
                checkWinDirection(coordinates, 0, 1) ||
                checkWinDirection(coordinates, 1, 1) ||
                checkWinDirection(coordinates, 1, -1);
    }

    public boolean isValid(Move move) {
        CellCoordinates coordinates = move.getCellCoordinates();
        return isValidCoordinates(coordinates)
                && turn == move.getValue();
    }

    @Override
    public CellCoordinatesRectangleBoard parseCoordinates(String s) throws ParsingCoordinatesException {
        Scanner scanner = new Scanner(new StringReader(s));
        int[] coordinates = new int[2];
        for (int i = 0; i < 2; i++) {
            if (!scanner.hasNextInt()) {
                throw new ParsingCoordinatesException();
            }
            coordinates[i] = scanner.nextInt();
        }
        if (scanner.hasNext()) {
            throw new ParsingCoordinatesException();
        }
        CellCoordinatesRectangleBoard result = new CellCoordinatesRectangleBoard(coordinates[0] - 1, coordinates[1] - 1);
        if (isValidCoordinates(result)) {
            return result;
        } else {
            throw new ParsingCoordinatesException();
        }
    }
}
