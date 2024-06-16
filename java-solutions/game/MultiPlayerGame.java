package game;

import game.Board.Board;
import game.Cell.PlayerCell;
import game.CoordinatesParser.BanCoordinatesInputException;
import game.GameConfiguration.GameConfiguration;
import game.GameResult.*;
import game.Move.Move;
import game.Move.MoveResult;
import game.Player.Player;
import game.Position.PositionWrapper;
import game.Visualiser.Visualiser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiPlayerGame {
    private final Board board;
    private final List<Player> players;
    private final Map<Player, Boolean> isLosingPlayer;
    private int eliminatedPlayersCnt = 0;

    public MultiPlayerGame(Board board, List<Player> players, boolean changePlayersId) {
        this.board = board;
        this.players = players;
        isLosingPlayer = new HashMap<>();

        int id = 0;
        for (Player player : players) {
            isLosingPlayer.put(player, false);
            if (changePlayersId) {
                player.setId(id++);
            }
        }
    }

    private GameResult moveResultToGameResult(MoveResult moveResult, int playerId) {
        return switch(moveResult) {
            case DRAW -> new DrawGameResult();
            case WIN -> new WinGameResult(playerId);
            case LOSE, NOTHING -> throw new AssertionError("Only draw or win supported");
        };
    }

    public TournamentResult playTournament(Visualiser visualiserForLog) {
        TournamentResult tournamentResult = new TournamentResult(players.size());
        for (int firstPlayer = 0; firstPlayer < players.size(); firstPlayer++) {
            for (int secondPlayer = 0; secondPlayer < players.size(); secondPlayer++) {
                if (firstPlayer == secondPlayer) {
                    continue;
                }

                List<Player> twoPlayers = List.of(players.get(firstPlayer), players.get(secondPlayer));

                GameConfiguration gameConfigurationForTwoPlayers = board.getGameConfiguration();
                gameConfigurationForTwoPlayers.setNumberOfPlayers(2);

                Board boardForTwoPlayers = board.changeGameConfiguration(gameConfigurationForTwoPlayers);

                MultiPlayerGame multiPlayerGame = new MultiPlayerGame(boardForTwoPlayers, twoPlayers, false);
                visualiserForLog.setField(multiPlayerGame.board.getField());
                GameResult result = multiPlayerGame.play(visualiserForLog);

                if (result.isDraw()) {
                    tournamentResult.addPoints(firstPlayer, 1);
                    tournamentResult.addPoints(secondPlayer, 1);
                    tournamentResult.setAt(firstPlayer, secondPlayer, 0);
                } else if (result.isWin()) {
                    int winningPlayer = ((WinGameResult) result).getPlayerId();
                    if (winningPlayer == firstPlayer) {
                        tournamentResult.setAt(firstPlayer, secondPlayer, 1);
                        tournamentResult.addPoints(firstPlayer, 3);
                    }
                    else {
                        tournamentResult.setAt(firstPlayer, secondPlayer, -1);
                        tournamentResult.addPoints(secondPlayer, 3);
                    }
                } else {
                    throw new AssertionError("Unknown GameResult");
                }
            }
        }
        return tournamentResult;
    }

    public GameResult play(Visualiser visualiserForLog) {
        while (true) {
            for (Player player : players) {
                int currentPlayerId = player.getId();
                if (isLosingPlayer.get(player)) {
                    continue;
                }

                if (eliminatedPlayersCnt == players.size() - 1) {
                    return new WinGameResult(currentPlayerId);
                }

                final MoveResult result = makeMove(player, currentPlayerId, visualiserForLog);
                if (result == MoveResult.DRAW || result == MoveResult.WIN) {
                    return moveResultToGameResult(result, currentPlayerId);
                }
                if (result == MoveResult.LOSE) {
                    isLosingPlayer.put(player, true);
                    eliminatedPlayersCnt++;
                }
            }
        }
    }

    private MoveResult makeMove(Player player, int no, Visualiser visualiserForLog) {
        Move move;
        MoveResult result;

        try {
            board.setTurn(new PlayerCell(player.getId())); // Setting player cell that currently makes move
            move = player.makeMove(new PositionWrapper(board.getPosition()));
            result = board.makeMove(move);
            if (visualiserForLog != null) {
                visualiserForLog.writeLogInformation(no, move, result);
            }
        } catch (BanCoordinatesInputException e) {
            result = MoveResult.LOSE;
        }

        return result;
    }
}
