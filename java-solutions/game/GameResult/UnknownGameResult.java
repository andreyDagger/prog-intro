package game.GameResult;

public class UnknownGameResult extends GameResult {
    @Override
    public String toString() {
        return "Unknown";
    }

    @Override
    public boolean isDraw() {
        return false;
    }

    @Override
    public boolean isWin() {
        return false;
    }
}
