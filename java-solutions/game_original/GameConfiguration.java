package game_original;

import java.io.StringReader;
import java.util.Scanner;

public class GameConfiguration {
    public static final int MAX_BOARD_SIZE = 100;
    public int n;
    public int m;
    public int k;

    private GameConfiguration(int n, int m, int k) {
        this.n = n;
        this.m = m;
        this.k = k;
    }

    private GameConfiguration(int[] configuration) {
        this(configuration[0], configuration[1], configuration[2]);
    }

    private static int[] getConfiguration(String s) {
        Scanner scanner = new Scanner(new StringReader(s));
        int[] configuration = new int[3];
        for (int i = 0; i < 3; i++) {
            if (!scanner.hasNextInt()) {
                return new int[]{};
            }
            int nextInt = scanner.nextInt();
            if (nextInt <= 0) {
                return new int[]{};
            }
            configuration[i] = nextInt;
        }
        if (scanner.hasNext() ||
                configuration[0] > MAX_BOARD_SIZE ||
                configuration[1] > MAX_BOARD_SIZE ||
                configuration[2] > Math.max(configuration[0], configuration[1])) { // checking, is configuration valid
            return new int[]{};
        }
        return configuration;
    }

    public static GameConfiguration inputGameConfiguration(Scanner scanner) {
        while (true) {
            System.out.println("Input three integers [n, m, k] (k <= max(n, m)) in one line separated with spaces: ");
            String input = scanner.nextLine();
            int[] configuration = getConfiguration(input);
            if (configuration.length > 0) {
                return new GameConfiguration(configuration);
            }
            else {
                System.err.println("Wrong input format");
            }
        }
    }
}
