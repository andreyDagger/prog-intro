package game.Player;

import game.Move.Move;
import game.Position.PositionWrapper;

import java.util.Random;

public class RandomPlayer extends Player {
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
