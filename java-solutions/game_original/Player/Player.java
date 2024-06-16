package game_original.Player;

import game_original.CoordinatesParser.BanCoordinatesInputException;
import game_original.Move.Move;
import game_original.Position.PositionWrapper;

public interface Player {
    Move makeMove(PositionWrapper position) throws BanCoordinatesInputException;
}
