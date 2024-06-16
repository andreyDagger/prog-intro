package game_original.CoordinatesParser;

public class ParsingCoordinatesException extends Exception {
    @Override
    public String getMessage() {
        return "Wrong input format\n";
    }
}
