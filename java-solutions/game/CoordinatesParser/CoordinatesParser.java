package game.CoordinatesParser;

import game.Cell.CellCoordinates;

public interface CoordinatesParser {
    CellCoordinates parseCoordinates(String s) throws ParsingCoordinatesException, BanCoordinatesInputException;
}
