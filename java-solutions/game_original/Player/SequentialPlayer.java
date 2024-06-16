package game_original.Player;

import game_original.Move.Move;
import game_original.Position.PositionWrapper;

public class SequentialPlayer implements Player {
    @Override
    public Move makeMove(PositionWrapper position) {
        return new Move(position.getNextFreeCell(), position.getTurn());
    }
}
