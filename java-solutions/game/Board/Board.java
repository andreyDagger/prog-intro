package game.Board;

import game.Cell.PlayerCell;
import game.CoordinatesParser.CoordinatesParser;
import game.Field.Field;
import game.GameConfiguration.GameConfiguration;
import game.Move.Move;
import game.Move.MoveResult;
import game.Position.Position;

public abstract class Board implements CoordinatesParser, Position, Field {
    protected final GameConfiguration gameConfiguration;
    protected PlayerCell turn;

    public Board(GameConfiguration gameConfiguration) {
        this.gameConfiguration = gameConfiguration;
    }

    public PlayerCell getTurn() {
        return turn;
    }

    public void setTurn(PlayerCell turn) {
        this.turn = turn;
    }

    public abstract Position getPosition();

    public abstract MoveResult makeMove(Move move);

    public abstract Board getCopy();

    public abstract Field getField();

    public abstract Board changeGameConfiguration(GameConfiguration gameConfiguration);

    public GameConfiguration getGameConfiguration() {
        return gameConfiguration;
    }
}
