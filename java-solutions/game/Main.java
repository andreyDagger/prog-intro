package game;

import game.Board.TicTacToeBoard;
import game.Cell.*;
import game.GameConfiguration.TicTacToeGameConfiguration;
import game.GameResult.GameResult;
import game.GameResult.TournamentResult;
import game.Player.Player;
import game.Player.RandomPlayer;
import game.Player.SequentialPlayer;
import game.Visualiser.TicTacToeConsoleVisualiser;
import game.Visualiser.Visualiser;

import java.util.*;

public class Main {
    private static void fill_CELL_TO_CHAR(Map<Cell, Character> CELL_TO_CHAR, TicTacToeGameConfiguration ticTacToeGameConfiguration) {
        CELL_TO_CHAR.put(new EmptyCell(), '.');
        CELL_TO_CHAR.put(new BannedCell(), '#');
        CELL_TO_CHAR.put(new PlayerCell(0), 'X');
        CELL_TO_CHAR.put(new PlayerCell(1), 'O');
        if (ticTacToeGameConfiguration.getNumberOfPlayers() >= 3) {
            CELL_TO_CHAR.put(new PlayerCell(2), '-');
        }
        if (ticTacToeGameConfiguration.getNumberOfPlayers() >= 4) {
            CELL_TO_CHAR.put(new PlayerCell(3), '|');
        }
        if (ticTacToeGameConfiguration.getNumberOfPlayers() > 4) {
            throw new AssertionError();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicTacToeGameConfiguration ticTacToeGameConfiguration = TicTacToeGameConfiguration.inputGameConfiguration(scanner);
        Map<Cell, Character> CELL_TO_CHAR = new HashMap<>();
        fill_CELL_TO_CHAR(CELL_TO_CHAR, ticTacToeGameConfiguration);

        List<CellCoordinatesRectangleBoard> bannedCells = new ArrayList<>();
//        for (int i = 0; i < Math.min(ticTacToeGameConfiguration.getM(), ticTacToeGameConfiguration.getN()); i++) {
//            bannedCells.add(new CellCoordinatesRectangleBoard(i, i));
//            bannedCells.add(new CellCoordinatesRectangleBoard(i, ticTacToeGameConfiguration.getM() - i - 1));
//        }

        TicTacToeBoard ticTacToeBoard = new TicTacToeBoard(ticTacToeGameConfiguration, bannedCells);
        Visualiser ticTacToeConsoleVisualiser = new TicTacToeConsoleVisualiser(
                CELL_TO_CHAR, ticTacToeBoard.getRectangleField());

        List<Player> players = new ArrayList<>();
        players.add(new RandomPlayer());
        players.add(new SequentialPlayer());//new HumanPlayer(new Scanner(System.in), ticTacToeConsoleVisualiser, ticTacToeBoard));
        if (ticTacToeGameConfiguration.getNumberOfPlayers() >= 3) {
            players.add(new RandomPlayer());//HumanPlayer(new Scanner(System.in), ticTacToeConsoleVisualiser, ticTacToeBoard));
        }
        if (ticTacToeGameConfiguration.getNumberOfPlayers() >= 4) {
            players.add(new RandomPlayer());
        }

        MultiPlayerGame game = new MultiPlayerGame(ticTacToeBoard, players, true);
        if (ticTacToeGameConfiguration.getIsTournament()) {
            final TournamentResult result = game.playTournament(ticTacToeConsoleVisualiser);
            result.printResult();
        }
        else {
            final GameResult result = game.play(ticTacToeConsoleVisualiser);
            System.out.println(result);
        }
    }
}
