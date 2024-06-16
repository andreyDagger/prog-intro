package game.GameResult;

public class DrawGameResult extends GameResult {

    @Override
    public boolean isDraw() {
        return true;
    }

    @Override
    public boolean isWin() {
        return false;
    }

    @Override
    public String toString() {
        return "Draw";
    }
}
