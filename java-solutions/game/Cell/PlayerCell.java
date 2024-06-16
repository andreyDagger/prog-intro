package game.Cell;

public class PlayerCell implements Cell {
    private final int playerId;

    public PlayerCell(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    @Override
    public boolean isBannedCell() {
        return false;
    }

    @Override
    public boolean isEmptyCell() {
        return false;
    }

    @Override
    public int hashCode() {
        return playerId;
    }

    @Override
    public boolean equals(Object playerCell) {
        if (playerCell instanceof PlayerCell cell) {
            return cell.playerId == playerId;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Player" + playerId;
    }
}
