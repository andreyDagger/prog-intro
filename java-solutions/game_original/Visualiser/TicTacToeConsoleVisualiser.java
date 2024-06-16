package game_original.Visualiser;

import game_original.Cell.Cell;
import game_original.Field.RectangleField;
import game_original.GameResult;
import game_original.Move.MoveResult;
import game_original.Position.PositionWrapper;
import game_original.Move.Move;

import java.util.HashMap;
import java.util.Map;

public class TicTacToeConsoleVisualiser extends ConsoleVisualiser {
    Map<Cell, Character> cellToChar;
    RectangleField rectangleField;

    public TicTacToeConsoleVisualiser(char emptyCell, char player1Cell,
                                      char player2Cell, char bannedCell, RectangleField rectangleField) {
        cellToChar = new HashMap<>();
        cellToChar.put(Cell.EMPTY, emptyCell);
        cellToChar.put(Cell.PLAYER1_CELL, player1Cell);
        cellToChar.put(Cell.PLAYER2_CELL, player2Cell);
        cellToChar.put(Cell.BANNED, bannedCell);

        this.rectangleField = rectangleField;
    }

    @Override
    public void writeInfoForHumanMove(PositionWrapper position) {
        System.out.println();
        System.out.println("Current position");
        drawBoardInformation();
        System.out.println("Enter your move for " + cellToChar.get(position.getTurn()) + " in format [row, column]");
    }

    @Override
    public void drawBoardInformation() {
        int n = rectangleField.getHeight();
        int m = rectangleField.getWidth();

        int indent1 = Integer.toString(m).length();
        int indent2 = Integer.toString(n).length();

        System.out.print(" ".repeat(indent2 + 1));
        for (int c = 1; c <= m; c++) {
            int cLength = Integer.toString(c).length();
            System.out.print(c + " ".repeat(indent1 - cLength + 1));
        }
        System.out.println();
        for (int r = 1; r <= n; r++) {
            int rLength = Integer.toString(r).length();
            System.out.print(r + " ".repeat(indent2 - rLength + 1));
            for (int c = 1; c <= m; c++) {
                System.out.print(cellToChar.get(rectangleField.getCell(r - 1, c - 1)) + " ".repeat(indent1));
            }
            System.out.println();
        }
    }

    @Override
    public void writeLogInformation(int no, Move move, MoveResult moveResult) {
        System.out.println();
        System.out.println("Player: " + no);
        System.out.println(move);
        drawBoardInformation();
        System.out.println("Move result: " + moveResult);
    }
}
