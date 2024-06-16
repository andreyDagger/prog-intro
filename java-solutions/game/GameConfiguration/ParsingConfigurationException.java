package game.GameConfiguration;

public class ParsingConfigurationException extends Exception {
    @Override
    public String getMessage() {
        return "Wrong input format";
    }
}
