package game_original.CoordinatesParser;

import game_original.Cell.CellCoordinates;

public interface CoordinatesParser {
    CellCoordinates parseCoordinates(String s) throws ParsingCoordinatesException, BanCoordinatesInputException;
}
