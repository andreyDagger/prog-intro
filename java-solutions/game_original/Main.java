package game_original;

import game_original.Board.TicTacToeBoard;
import game_original.Cell.Cell;
import game_original.Cell.CellCoordinates;
import game_original.Cell.CellCoordinatesRectangleBoard;
import game_original.Player.*;
import game_original.Board.Board;
import game_original.Visualiser.TicTacToeConsoleVisualiser;
import game_original.Visualiser.Visualiser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameConfiguration gameConfiguration = GameConfiguration.inputGameConfiguration(scanner);
        List<CellCoordinatesRectangleBoard> bannedCells = new ArrayList<>();

        for (int i = 0; i < Math.min(gameConfiguration.m, gameConfiguration.n); i++) {
            bannedCells.add(new CellCoordinatesRectangleBoard(i, i));
            bannedCells.add(new CellCoordinatesRectangleBoard(i, gameConfiguration.m - i - 1));
        }

        TicTacToeBoard ticTacToeBoard = new TicTacToeBoard(gameConfiguration, bannedCells);
        Visualiser ticTacToeConsoleVisualiser = new TicTacToeConsoleVisualiser(
                '.', 'X', 'O', '#', ticTacToeBoard.getRectangleField());
        final GameResult result = new TwoPlayerGame(
                ticTacToeBoard,
                 new RandomPlayer(),
               new SequentialPlayer()
                // new HumanPlayer(new Scanner(System.in), ticTacToeConsoleVisualiser, ticTacToeBoard),
               // new HumanPlayer(new Scanner(System.in), ticTacToeConsoleVisualiser, ticTacToeBoard)
//                new HumanPlayer(new Scanner(System.in))
        ).play(ticTacToeConsoleVisualiser);
        switch (result) {
            case PLAYER1_WIN:
                System.out.println("First player won");
                break;
            case PLAYER2_WIN:
                System.out.println("Second player won");
                break;
            case DRAW:
                System.out.println("Draw");
                break;
            default:
                throw new AssertionError("Unknown result " + result);
        }
    }
}
