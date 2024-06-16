package game.GameConfiguration;

import java.io.StringReader;
import java.util.Scanner;

public class TicTacToeGameConfiguration extends RectangleBoardGameConfiguration {
    private final int k;

    private TicTacToeGameConfiguration(int n, int m, int k, int numberOfPlayers, boolean isTournament) {
        super(n, m, numberOfPlayers, isTournament);
        this.k = k;
    }

    private TicTacToeGameConfiguration(int[] configuration, boolean isTournament) {
        this(configuration[0], configuration[1], configuration[2], configuration[3], isTournament);
    }

    public int getK() {
        return k;
    }

    private static TicTacToeGameConfiguration parseConfiguration(String s) throws ParsingConfigurationException {
        Scanner scanner = new Scanner(new StringReader(s));
        int[] configuration = new int[4];
        String isTournamentAnswer;
        for (int i = 0; i < 4; i++) {
            if (!scanner.hasNextInt()) {
                throw new ParsingConfigurationException();
            }
            int nextInt = scanner.nextInt();
            if (nextInt <= 0) {
                throw new ParsingConfigurationException();
            }
            configuration[i] = nextInt;
        }

        int n = configuration[0];
        int m = configuration[1];
        int players = configuration[2];

        // checking, is configuration valid
        if (!scanner.hasNext() ||
                configuration[0] > MAX_BOARD_SIZE ||
                configuration[1] > MAX_BOARD_SIZE ||
                configuration[2] > Math.max(configuration[0], configuration[1]) ||
                configuration[3] > configuration[1] * configuration[2] ||
                configuration[3] < 1 ||
                configuration[3] > 4) {
            throw new ParsingConfigurationException();
        }
        isTournamentAnswer = scanner.next();
        if (scanner.hasNext()) {
            throw new ParsingConfigurationException();
        }
        if (isTournamentAnswer.equals("YES")) {
            return new TicTacToeGameConfiguration(configuration, true);
        } else if (isTournamentAnswer.equals("NO")) {
            return new TicTacToeGameConfiguration(configuration, false);
        } else {
            throw new ParsingConfigurationException();
        }
    }

    public static TicTacToeGameConfiguration inputGameConfiguration(Scanner scanner) {
        while (true) {
            System.out.println("Input parameters for game [n, m, k, numberOfPlayers, tournamentGame?\"YES/NO\"]\n" +
                    "(k <= max(n, m), 1 <= numberOfPlayers <= 4)\n" +
                    "in one line separated with spaces: ");
            String input = scanner.nextLine();
            TicTacToeGameConfiguration ticTacToeGameConfiguration;
            try {
                ticTacToeGameConfiguration = parseConfiguration(input);
                return ticTacToeGameConfiguration;
            } catch (ParsingConfigurationException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
