package game.Player;

import game.Move.Move;
import game.Position.PositionWrapper;

public class SequentialPlayer extends Player {
    @Override
    public Move makeMove(PositionWrapper position) {
        return new Move(position.getNextFreeCell(), position.getTurn());
    }
}
