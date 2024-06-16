import java.io.*;
import java.util.Arrays;

public class ReverseAvg {
    public static final int AVERAGE_LINE_SIZE = 1 << 6;

    public static int countNumbers(String line) throws IOException {
        int res = 0;
        MyScanner scanner = new MyScanner(line, AVERAGE_LINE_SIZE);
        while (scanner.hasNextInt()) {
            scanner.nextInt();
            res++;
        }
        scanner.close();
        return res;
    }

    public static int[][] normalize(int[][] mas) {
        int sz = mas.length;
        int[][] temp = new int[sz][];
        for (int i = 0; i < sz; i++) {
            temp[i] = Arrays.copyOf(mas[i], mas[i].length);
        }
        mas = new int[2 * sz][];
        for (int i = 0; i < sz; i++) {
            mas[i] = Arrays.copyOf(temp[i], temp[i].length);
        }
        return mas;
    }

    public static void main(String[] args) {
        MyScanner scanner = new MyScanner(new InputStreamReader(System.in));
        int linesCnt = 0;
        int[][] numbers = new int[1][];
        int maxNumbersInRow = 0;

        try {
            for (int i = 0; scanner.hasNextChar(); i++, linesCnt++) {
                if (i == numbers.length) {
                    numbers = normalize(numbers);
                }

                String line = scanner.nextLine();
                int numbersCnt = countNumbers(line);
                MyScanner localScanner = new MyScanner(line, AVERAGE_LINE_SIZE);
                numbers[i] = new int[numbersCnt];
                int pos = 0;

                while (localScanner.hasNextInt()) {
                    numbers[i][pos] = localScanner.nextInt();
                    pos++;
                }
                maxNumbersInRow = Math.max(maxNumbersInRow, numbers[i].length);

                localScanner.close();
            }
        }
        catch (IOException e) {
            System.err.println("IO Error. " + e.getMessage());
            System.exit(1);
        }
        finally {
            try {
                scanner.close();
            }
            catch (IOException e) {
                System.err.println("ERROR: Couldn't close scanner. " + e.getMessage());
            }
        }

        long[] sumCol = new long[maxNumbersInRow];
        int[] cntCol = new int[maxNumbersInRow];
        for (int i = 0; i < linesCnt; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                sumCol[j] += numbers[i][j];
                cntCol[j]++;
            }
        }

        for (int i = 0; i < linesCnt; i++) {
            long sum = 0;
            for (int j = 0; j < numbers[i].length; j++) {
                sum += numbers[i][j];
            }
            for (int j = 0; j < numbers[i].length; j++) {
                long result = (sum + sumCol[j] - numbers[i][j]) / (cntCol[j] + numbers[i].length - 1);

                System.out.print(result);
                if (j < numbers[i].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
