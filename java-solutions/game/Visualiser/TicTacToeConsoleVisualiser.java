package game.Visualiser;

import java.util.Map;

import game.Cell.Cell;
import game.Field.Field;
import game.Field.RectangleField;
import game.Move.Move;
import game.Move.MoveResult;
import game.Position.PositionWrapper;

public class TicTacToeConsoleVisualiser implements ConsoleVisualiser {
    private final Map<Cell, Character> cellToChar;
    private Field field;

    public TicTacToeConsoleVisualiser(Map<Cell, Character> cellToChar, RectangleField rectangleField) {
        this.cellToChar = cellToChar;
        this.field = rectangleField;
    }

    @Override
    public Field getField() {
        return field;
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
        final RectangleField rectangleField = (RectangleField) field;
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

    @Override
    public void setField(Field field) {
        this.field = field;
    }
}
