package game.Player;

import game.CoordinatesParser.BanCoordinatesInputException;
import game.Move.Move;
import game.Position.PositionWrapper;

public abstract class Player {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract Move makeMove(PositionWrapper position) throws BanCoordinatesInputException;
}
