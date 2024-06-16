package game_original.Visualiser;

import game_original.GameResult;
import game_original.Move.MoveResult;
import game_original.Position.PositionWrapper;
import game_original.Move.Move;

public interface Visualiser {
    public void write(String text);
    public void writeErrorInfo(String text);
    public void writeInfoForHumanMove(PositionWrapper position);
    public void drawBoardInformation();
    public void writeLogInformation(int no, Move move, MoveResult gameResult);
}
