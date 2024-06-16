package game_original.Player;

import game_original.Move.Move;
import game_original.Position.PositionWrapper;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random = new Random();

    @Override
    public Move makeMove(PositionWrapper position) {
        while (true) {
            final Move move = new Move(
                    position.getRandomCell(),
                    position.getTurn()
            );
            if (position.isValid(move)) {
                return move;
            }
        }
    }
}
