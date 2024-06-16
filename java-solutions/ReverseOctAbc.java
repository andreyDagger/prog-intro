import java.io.*;
import java.util.Arrays;

public class ReverseOctAbc {
    public final static int BUFFER_SIZE_FOR_LINE = 1 << 4;

    public static int countNumbers(String line) throws IOException {
        int res = 0;
        MyScanner scanner = new MyScanner(line, BUFFER_SIZE_FOR_LINE);
        while (scanner.hasNextInt()) {
            scanner.nextInt();
            res++;
        }
        scanner.close();
        return res;
    }

    public static int[] pushBackInt(int[] mas, int idx, int val) {
        if (idx == mas.length) {
            mas = normalizeInt(mas);
        }
        mas[idx] = val;
        return mas;
    }

    public static int[] normalizeInt(int[] mas) {
        int sz = mas.length;
        if (sz == 0) {
            mas = new int[1];
            return mas;
        }
        int[] temp;
        temp = Arrays.copyOf(mas, mas.length);
        mas = new int[2 * sz];
        System.arraycopy(temp, 0, mas, 0, sz);
        return mas;
    }

    public static int[][] normalizeIntInt(int[][] mas) {
        int sz = mas.length;
        if (sz == 0) {
            mas = new int[1][0];
            return mas;
        }
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

    public static void swap(int[] mas, int i, int j) {
        int temp = mas[i];
        mas[i] = mas[j];
        mas[j] = temp;
    }

    public static int[] cutExtraInts(int[] mas, int sz) {
        return Arrays.copyOf(mas, sz);
    }

    public static void main(String[] args) {
        MyScanner scanner = new MyScanner(new InputStreamReader(System.in));
        int linesCnt = 0;
        int[][] numbers = new int[0][];
        try {
            for (int i = 0; scanner.hasNextChar(); i++, linesCnt++) { // processing i-th line
                if (i == numbers.length) {
                    numbers = normalizeIntInt(numbers);
                }
                numbers[i] = new int[0];
                int numbersCnt = 0;

                for (int j = 0;; j++) { // processing current number in line
                    boolean isLineEnd = false;

                    int curNumber = scanner.nextInt();

                    if (scanner.isEndOfLine()) {
                        isLineEnd = true;
                    }
                    if (scanner.isNoIntegersLeft()) {
                        break;
                    }

                    numbersCnt++;
                    numbers[i] = pushBackInt(numbers[i], j, curNumber);

                    if (isLineEnd) {
                        break;
                    }
                }

                numbers[i] = cutExtraInts(numbers[i], numbersCnt);
                for (int l = 0, r = numbersCnt - 1;l < r; l++, r--) {
                    swap(numbers[i], l, r);
                }
            }
        }
        catch (IOException e) {
            System.err.println("Input error. " + e.getMessage());
            System.exit(1);
        }
        finally {
            try {
                scanner.close();
            }
            catch (IOException e) {
                System.err.println("Couldn't close scanner. " + e.getMessage());
            }
        }

        for (int i = linesCnt - 1; i >= 0; i--) {
            for (int j = 0; j < numbers[i].length; j++) {
                System.out.print(numbers[i][j]);
                if (j < numbers[i].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
