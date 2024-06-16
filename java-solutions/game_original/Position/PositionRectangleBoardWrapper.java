package game_original.Position;

public class PositionRectangleBoardWrapper extends PositionWrapper {
    private PositionRectangleBoard position;

    public PositionRectangleBoardWrapper(PositionRectangleBoard position) {
        super(position);
        this.position = position;
    }
    public int getHeight() {
        return position.getHeight();
    }

    public int getWidth() {
        return position.getWidth();
    }
}
