package game.Cell;

public class EmptyCell implements Cell {
    @Override
    public boolean isBannedCell() {
        return false;
    }

    @Override
    public boolean isEmptyCell() {
        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof EmptyCell;
    }
}
