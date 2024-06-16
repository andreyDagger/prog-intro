package game.GameResult;

public class TournamentResult {
    private final int[][] resultTable;
    private final int[] points;

    public TournamentResult(int size) {
        resultTable = new int[size][size];
        points = new int[size];
    }

    public void addPoints(int playerId, int value) {
        points[playerId] += value;
    }

    public void setAt(int firstPlayerId, int secondPlayerId, int value) {
        resultTable[firstPlayerId][secondPlayerId] = value;
    }

    public void setPointsAt(int playerId, int value) {
        points[playerId] = value;
    }

    public int getAt(int row, int col) {
        return resultTable[row][col];
    }

    public void printResult() {
        System.out.println();
        for (int row = 0; row < resultTable.length; row++) {
            for (int col = 0; col < resultTable[row].length; col++) {
                System.out.print(" ".repeat(3 - Integer.toString(resultTable[row][col]).length()) + resultTable[row][col]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("-1: Left player lost to upper player");
        System.out.println("0: Draw");
        System.out.println("1: Left player win upper player");
        System.out.println();
        for (int i = 0; i < resultTable.length; i++) {
            System.out.println("Player " + i + ": " + points[i]);
        }
    }
}
