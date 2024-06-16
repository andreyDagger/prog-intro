package game_original.Board;

import game_original.CoordinatesParser.CoordinatesParser;
import game_original.Field.Field;
import game_original.GameResult;
import game_original.Move.Move;
import game_original.Move.MoveResult;
import game_original.Position.Position;

public interface Board extends CoordinatesParser, Position, Field {
    Position getPosition();

    MoveResult makeMove(Move move);
}
