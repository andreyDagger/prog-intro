package game.Player;

import game.Cell.CellCoordinates;
import game.CoordinatesParser.BanCoordinatesInputException;
import game.CoordinatesParser.CoordinatesParser;
import game.CoordinatesParser.ParsingCoordinatesException;
import game.Move.Move;
import game.Position.PositionWrapper;
import game.Visualiser.Visualiser;

import java.util.Scanner;

public class HumanPlayer extends Player {
    private final Scanner in;
    private final Visualiser visualiser;
    private final CoordinatesParser coordinatesParser;

    public HumanPlayer(Scanner in, Visualiser visualiser, CoordinatesParser coordinatesParser) {
        this.in = in;
        this.visualiser = visualiser;
        this.coordinatesParser = coordinatesParser;
    }

    private CellCoordinates inputCellCoordinates(PositionWrapper position) throws BanCoordinatesInputException {
        while (true) {
            visualiser.writeInfoForHumanMove(position);
            String s = in.nextLine();
            try {
                return coordinatesParser.parseCoordinates(s);
            } catch (ParsingCoordinatesException e) {
                visualiser.writeErrorInfo(e.getMessage());
            }
        }
    }

    @Override
    public Move makeMove(PositionWrapper position) throws BanCoordinatesInputException {
        CellCoordinates cellCoordinates = inputCellCoordinates(position);
        return new Move(cellCoordinates, position.getTurn());
    }
}
