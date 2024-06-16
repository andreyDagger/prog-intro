package game_original.Player;

import game_original.Cell.CellCoordinates;
import game_original.CoordinatesParser.BanCoordinatesInputException;
import game_original.CoordinatesParser.CoordinatesParser;
import game_original.CoordinatesParser.ParsingCoordinatesException;
import game_original.Move.Move;
import game_original.Position.PositionWrapper;
import game_original.Visualiser.Visualiser;

import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Scanner in;
    private final Visualiser visualiser;
    private final CoordinatesParser coordinatesParser;

    private CellCoordinates inputCellCoordinates(PositionWrapper position) throws BanCoordinatesInputException {
        while (true) {
            visualiser.writeInfoForHumanMove(position);
            String s = in.nextLine();
            try {
                return coordinatesParser.parseCoordinates(s);
            }
            catch (ParsingCoordinatesException e) {
                visualiser.writeErrorInfo(e.getMessage());
            }
        }
    }

    public HumanPlayer(Scanner in, Visualiser visualiser, CoordinatesParser coordinatesParser) {
        this.in = in;
        this.visualiser = visualiser;
        this.coordinatesParser = coordinatesParser;
    }

    @Override
    public Move makeMove(PositionWrapper position) throws BanCoordinatesInputException {
        CellCoordinates cellCoordinates = inputCellCoordinates(position);
        return new Move(cellCoordinates, position.getTurn());
    }
}
