package game_original;

import game_original.Board.Board;
import game_original.CoordinatesParser.BanCoordinatesInputException;
import game_original.Move.Move;
import game_original.Move.MoveResult;
import game_original.Player.Player;
import game_original.Position.PositionWrapper;
import game_original.Visualiser.Visualiser;

public class TwoPlayerGame {
    private final Board board;
    private final Player player1;
    private final Player player2;

    public TwoPlayerGame(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    private GameResult MoveResultToGameResult(MoveResult moveResult, int playerId) {
        if (moveResult == MoveResult.DRAW) {
            return GameResult.DRAW;
        }
        else if (moveResult == MoveResult.WIN) {
            if (playerId == 1) {
                return GameResult.PLAYER1_WIN;
            }
            else {
                return GameResult.PLAYER2_WIN;
            }
        }
        else if (moveResult == MoveResult.LOSE){
            if (playerId == 1) {
                return GameResult.PLAYER2_WIN;
            }
            else {
                return GameResult.PLAYER1_WIN;
            }
        }
        else {
            throw new AssertionError("Invalid MoveResult");
        }
    }

    public GameResult play(Visualiser visualiserForLog) {
        while (true) {
            final MoveResult result1 = makeMove(player1, 1, visualiserForLog);
            if (result1 != MoveResult.NOTHING)  {
                return MoveResultToGameResult(result1, 1);
            }
            final MoveResult result2 = makeMove(player2, 2, visualiserForLog);
            if (result2 != MoveResult.NOTHING)  {
                return MoveResultToGameResult(result2, 1);
            }
        }
    }

    private MoveResult makeMove(Player player, int no, Visualiser visualiserForLog) {
        Move move = null;
        MoveResult result = null;

        try {
            move = player.makeMove(new PositionWrapper(board.getPosition()));
            result = board.makeMove(move);
            if (visualiserForLog != null) {
                visualiserForLog.writeLogInformation(no, move, result);
            }
        }
        catch (BanCoordinatesInputException e) {
            result = MoveResult.LOSE;
        }

        return result;
    }
}
