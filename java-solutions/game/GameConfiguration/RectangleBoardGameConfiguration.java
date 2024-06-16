package game.GameConfiguration;

public class RectangleBoardGameConfiguration extends GameConfiguration {
    protected static final int MAX_BOARD_SIZE = 100;
    protected int n;
    protected int m;

    public RectangleBoardGameConfiguration(int n, int m, int numberOfPlayers, boolean isTournament) {
        super(numberOfPlayers, isTournament);
        this.m = m;
        this.n = n;
    }

    public int getN() {
        return n;
    }
    public int getM() {
        return m;
    }
}
