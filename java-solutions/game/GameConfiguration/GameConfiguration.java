package game.GameConfiguration;

public class GameConfiguration {
    protected int numberOfPlayers;
    protected boolean isTournament;

    protected GameConfiguration(int numberOfPlayers, boolean isTournament) {
        this.numberOfPlayers = numberOfPlayers;
        this.isTournament = isTournament;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public boolean getIsTournament() {
        return isTournament;
    }

    public void setNumberOfPlayers(int newNumberOfPlayers) {
        numberOfPlayers = newNumberOfPlayers;
    }
}
