package game_original.Board;

import game_original.*;
import game_original.Cell.Cell;
import game_original.Cell.CellCoordinatesRectangleBoard;
import game_original.Cell.CellCoordinates;
import game_original.CoordinatesParser.BanCoordinatesInputException;
import game_original.CoordinatesParser.ParsingCoordinatesException;
import game_original.Field.RectangleField;
import game_original.Move.Move;
import game_original.Move.MoveRectangleBoard;
import game_original.Move.MoveResult;
import game_original.Position.Position;
import game_original.Position.PositionRectangleBoard;

import java.io.StringReader;
import java.util.*;

public class TicTacToeBoard extends RectangleBoard implements PositionRectangleBoard, RectangleField {
    private static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.EMPTY, ".",
            Cell.PLAYER1_CELL, "X",
            Cell.PLAYER2_CELL, "0"
    );

    private int filledCellsCount;
    private final GameConfiguration gameConfiguration;

    public TicTacToeBoard(GameConfiguration gameConfiguration, List<CellCoordinatesRectangleBoard> banedCells) {
        super(gameConfiguration.n, gameConfiguration.m);
        for (CellCoordinatesRectangleBoard coordinates : banedCells) {
            field[coordinates.getRow()][coordinates.getCol()] = Cell.BANNED;
        }
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if (field[row][col] == Cell.BANNED) {
                    filledCellsCount++;
                }
            }
        }
        this.gameConfiguration = gameConfiguration;
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

        turn = turn == Cell.PLAYER1_CELL ? Cell.PLAYER2_CELL : Cell.PLAYER1_CELL;
        return MoveResult.NOTHING;
    }

    private boolean checkDraw() {
        return filledCellsCount == gameConfiguration.m * gameConfiguration.n;
    }

    private boolean checkWinHorizontal(CellCoordinatesRectangleBoard coordinates) {
        int upBorder = 0;
        int bottomBorder = 0;
        for (int row = coordinates.getRow(); row >= 0; row--) {
            if (field[row][coordinates.getCol()] != field[coordinates.getRow()][coordinates.getCol()]) {
                upBorder = row + 1;
                break;
            }
            if (row == 0) {
                upBorder = row;
                break;
            }
        }
        for (int row = coordinates.getRow(); row < gameConfiguration.n; row++) {
            if (field[row][coordinates.getCol()] != field[coordinates.getRow()][coordinates.getCol()]) {
                bottomBorder = row - 1;
                break;
            }
            if (row == gameConfiguration.n - 1) {
                bottomBorder = row;
            }
        }
        return bottomBorder - upBorder + 1 == gameConfiguration.k;
    }

    private boolean checkWinVertical(CellCoordinatesRectangleBoard coordinates) {
        int leftBorder = 0;
        int rightBorder = 0;
        for (int col = coordinates.getCol(); col >= 0; col--) {
            if (field[coordinates.getRow()][col] != field[coordinates.getRow()][coordinates.getCol()]) {
                leftBorder = col + 1;
                break;
            }
            if (col == 0) {
                leftBorder = col;
                break;
            }
        }
        for (int col = coordinates.getCol(); col < gameConfiguration.m; col++) {
            if (field[coordinates.getRow()][col] != field[coordinates.getRow()][coordinates.getCol()]) {
                rightBorder = col - 1;
                break;
            }
            if (col == gameConfiguration.m - 1) {
                rightBorder = col;
            }
        }
        return rightBorder - leftBorder + 1 == gameConfiguration.k;
    }

    private boolean checkWinMainDiagonal(CellCoordinatesRectangleBoard coordinates) {
        int leftBorder = 0;
        int rightBorder = 0;
        for (int col = coordinates.getCol(), row = coordinates.getRow(); col >= 0 && row >= 0; col--, row--) {
            if (field[row][col] != field[coordinates.getRow()][coordinates.getCol()]) {
                leftBorder = col + 1;
                break;
            }
            if (Math.min(row, col) == 0) {
                leftBorder = col;
                break;
            }
        }
        for (int col = coordinates.getCol(), row = coordinates.getRow(); col < gameConfiguration.m && row < gameConfiguration.m; col++, row++) {
            if (field[row][col] != field[coordinates.getRow()][coordinates.getCol()]) {
                rightBorder = col - 1;
                break;
            }
            if (row == gameConfiguration.n - 1 || col == gameConfiguration.m - 1) {
                rightBorder = col;
                break;
            }
        }
        return rightBorder - leftBorder + 1 == gameConfiguration.k;
    }

    private boolean checkWinSecondaryDiagonal(CellCoordinatesRectangleBoard coordinates) {
        int leftBorder = 0;
        int rightBorder = 0;
        for (int col = coordinates.getCol(), row = coordinates.getRow(); col >= 0 && row < gameConfiguration.n; col--, row++) {
            if (field[row][col] != field[coordinates.getRow()][coordinates.getCol()]) {
                leftBorder = col + 1;
                break;
            }
            if (row == gameConfiguration.n - 1 || col == 0) {
                leftBorder = col;
                break;
            }
        }
        for (int col = coordinates.getCol(), row = coordinates.getRow(); col < gameConfiguration.m && row >= 0; col++, row--) {
            if (field[row][col] != field[coordinates.getRow()][coordinates.getCol()]) {
                rightBorder = col - 1;
                break;
            }
            if (row == 0 || col == gameConfiguration.m - 1) {
                rightBorder = col;
            }
        }
        return rightBorder - leftBorder + 1 == gameConfiguration.k;
    }

    private boolean checkWin(Move move) {
        CellCoordinatesRectangleBoard coordinates = (CellCoordinatesRectangleBoard) move.getCellCoordinates();
        return checkWinHorizontal(coordinates) || checkWinVertical(coordinates) ||
                checkWinMainDiagonal(coordinates) || checkWinSecondaryDiagonal(coordinates);
    }

    public boolean isValid(Move move) {
        CellCoordinatesRectangleBoard coordinates = (CellCoordinatesRectangleBoard) move.getCellCoordinates();
        return isValidCoordinates(coordinates) && turn == move.getValue();
    }

    @Override
    public CellCoordinatesRectangleBoard parseCoordinates(String s) throws ParsingCoordinatesException, BanCoordinatesInputException {
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
        }
        else {
            throw new ParsingCoordinatesException();
        }
    }
}
