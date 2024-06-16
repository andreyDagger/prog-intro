package game.Cell;

public class BannedCell implements Cell {
    @Override
    public boolean isBannedCell() {
        return true;
    }

    @Override
    public boolean isEmptyCell() {
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof BannedCell;
    }
}
