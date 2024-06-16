package game_original.CoordinatesParser;

public class BanCoordinatesInputException extends Exception {
    @Override
    public String getMessage() {
        return "You are banned for making illegal moves, cry about it\n";
    }
}
