package game.GameResult;

public class WinGameResult extends GameResult {
    private final int playerId;

    public WinGameResult(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    @Override
    public boolean isWin() {
        return true;
    }

    @Override
    public boolean isDraw() {
        return false;
    }

    @Override
    public String toString() {
        return "Player " + playerId + " Win";
    }
}
