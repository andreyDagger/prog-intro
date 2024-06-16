package game.Visualiser;

import game.Field.Field;
import game.Move.Move;
import game.Move.MoveResult;
import game.Position.PositionWrapper;

public interface Visualiser {
    Field getField();

    void write(String text);

    void writeErrorInfo(String text);

    void writeInfoForHumanMove(PositionWrapper position);

    void drawBoardInformation();

    void writeLogInformation(int no, Move move, MoveResult gameResult);

     void setField(Field field);
}
