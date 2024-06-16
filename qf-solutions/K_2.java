import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.util.NoSuchElementException;

public class K_2 {

    public static class MyScanner {
        private static final int MAX_MARKED_CHARACTERS = 1 << 24;
        private static final int STANDARD_BUFFER_SIZE = 1 << 13;
        private static final int MAX_BUFFER_SIZE = 1 << 20;
        private static final char EOF = (char) 0;
        private static final char LINE_SEPARATOR = '\n'; // Every possible line separator is changed by this symbol

        private final Reader reader;

        private char[] buffer;
        private int bufferPos;
        private int bufferCharsCnt;

        private boolean isMarked = false;
        private int markedPos = -1;
        private int markedCharLimit = -1;

        private boolean isNoIntegersLeft = false; // this flag is changed by nextInt()
        private boolean isEndOfLine = false; // this flag is changed by nextInt()

        public static char getEOFChar() {
            return EOF;
        }
        public static boolean isLineSeparator(char c) {
            return c == LINE_SEPARATOR;
        }
        public static boolean isEOF(char c) { return c == EOF; }

        public MyScanner(Reader reader, int sz) {
            if (sz <= 0 || sz > MAX_BUFFER_SIZE) {
                throw new IllegalArgumentException("Buffer size must be in range: (0, MAX_BUFFER_SIZE]");
            }
            this.reader = reader;
            buffer = new char[sz];
            bufferPos = bufferCharsCnt = 0;
        }

        public MyScanner(Reader reader) {
            this(reader, STANDARD_BUFFER_SIZE);
        }

        public MyScanner(String reader, int sz) {
            this(new StringReader(reader), sz);
        }

        public MyScanner(String reader) {
            this(new StringReader(reader), STANDARD_BUFFER_SIZE);
        }

        private void refillBuffer() throws IOException {
            int offset = 0;

            if (isMarked) {
                int delta = bufferPos - markedPos;
                if (delta >= markedCharLimit) {
                    throw new IOException("Maximum size of marked characters was exceeded");
                }

                if (markedCharLimit < buffer.length) {
                    System.arraycopy(buffer, markedPos, buffer, 0, delta);
                } else {
                    char[] temp = new char[2 * buffer.length];
                    System.arraycopy(buffer, markedPos, temp, 0, delta);
                    buffer = temp;
                }
                offset = delta;
                markedPos = 0;
            }

            int count = reader.read(buffer, offset, buffer.length - offset);
            if (count == -1) { // EOF
                return;
            }

            char[] temp = new char[count]; // changing all '\r\n' to LINE_SEPARATOR
            int tempPos = 0;
            for (int i = 0; i < count; i++) {
                if (i + 1 < count && buffer[i] == '\r' && buffer[i + 1] == '\n') {
                    temp[tempPos++] = LINE_SEPARATOR;
                    i++;
                }
                else {
                    temp[tempPos++] = buffer[i];
                }
            }

            count = tempPos;
            System.arraycopy(temp, 0, buffer, 0, count);

            bufferCharsCnt = offset + count;
            bufferPos = offset;
        }

        public char read() throws IOException {
            if (bufferPos >= bufferCharsCnt) {
                refillBuffer();
                if (bufferPos >= bufferCharsCnt) {
                    return EOF;
                }
            }
            return buffer[bufferPos++];
        }

        public String nextLine() throws IOException {
            StringBuilder result = new StringBuilder();
            while (true) {
                char symbol = read();

                if (isEOF(symbol) || isLineSeparator(symbol)) {
                    if (isEOF(symbol) && result.isEmpty()) {
                        throw new NoSuchElementException("Input is exhausted: there's no lines.");
                    }
                    break;
                }
                result.append(symbol);
            }
            return result.toString();
        }

        public boolean isEndOfLine() {
            return isEndOfLine;
        }

        public boolean isNoIntegersLeft() {
            return isNoIntegersLeft;
        }

        public int nextInt() throws IOException { // try to find next integer in current line
            isEndOfLine = false;
            isNoIntegersLeft = false;

            while (true) {
                char symbol = read();
                if (isLineSeparator(symbol) || isEOF(symbol)) {
                    isEndOfLine = true;
                    break;
                }
                if (Character.isWhitespace(symbol)) {
                    continue;
                }

                StringBuilder result = new StringBuilder();
                result.append(symbol);

                while (true) {
                    symbol = read();

                    if (isLineSeparator(symbol) || isEOF(symbol) || Character.isWhitespace(symbol)) {
                        if (isLineSeparator(symbol) || isEOF(symbol)) {
                            isEndOfLine = true;
                        }
                        break;
                    }

                    result.append(symbol);
                }

                return Integer.parseInt(result.toString());
            }

            isNoIntegersLeft = true;
            return 0; // No integer left in the line
        }

        public void close() throws IOException {
            reader.close();
        }
    }

    static int[] iPos = new int[26];
    static int[] jPos = new int[26];
    static char[][] matrix;

    static void fillRectangle(int i1, int i2, int j1, int j2) {
        if (i1 > i2 || j1 > j2) {
            return;
        }
        for (int letter = 1; letter < 26; letter++) {
            if (iPos[letter] == -1 || iPos[letter] < i1 || iPos[letter] > i2 || jPos[letter] < j1 || jPos[letter] > j2) {
                continue;
            }
            for (int i = iPos[letter] - 1; i >= i1 && matrix[i][jPos[letter]] == '.'; i--) {
                matrix[i][jPos[letter]] = (char)('a' + letter);
            }
            for (int i = iPos[letter] + 1; i <= i2 && matrix[i][jPos[letter]] == '.'; i++) {
                matrix[i][jPos[letter]] = (char)('a' + letter);
            }
        }

        boolean flag = true;
        for (int j = j1; j <= j2; j++) {
            if (flag) {
                if (matrix[i1][j] != '.') {
                    flag = false;
                }
                continue;
            }
            if (matrix[i1][j] != '.') {
                continue;
            }
            for (int i = i1; i <= i2; i++) {
                matrix[i][j] = matrix[i][j - 1];
            }
        }
        for (int j = j1; j <= j2; j++) {
            if (matrix[i1][j] != '.') {
                for (j = j - 1; j >= j1; j--) {
                    for (int i = i1; i <= i2; i++) {
                        matrix[i][j] = matrix[i][j + 1];
                    }
                }
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 26; i++) {
            iPos[i] = -1;
            jPos[i] = -1;
        }
        MyScanner sc = new MyScanner(new InputStreamReader(System.in));
        int n = sc.nextInt(), m = sc.nextInt();
        matrix = new char[n][m];
        int[][] d = new int[n][m];
        for (int i = 0; i < n; i++) {
            String s = sc.nextLine();
            for (int j = 0; j < m; j++) {
                matrix[i][j] = s.charAt(j);
                if (Character.isAlphabetic(matrix[i][j])) {
                    iPos[matrix[i][j] - 'A'] = i;
                    jPos[matrix[i][j] - 'A'] = j;
                    if (matrix[i][j] != 'A') {
                        matrix[i][j] = Character.toLowerCase(matrix[i][j]);
                        d[i][j] = i;
                    }
                    else {
                        d[i][j] = i > 0 ? d[i - 1][j] : -1;
                    }
                }
                else {
                    d[i][j] = i > 0 ? d[i - 1][j] : -1;
                }
            }
        }

        int maxSq = 0;
        int i_left = 0, j_left = 0;
        int h = 0, w = 0;

        for (int i = 0; i < n; i++) {
            int[] left = new int[m];
            int[] right = new int[m];

            int[] stack_val = new int[m];
            int[] stack_idx = new int[m];
            int sz = 0;

            for (int j = 0; j < m; j++) {
                while (sz > 0 && stack_val[sz - 1] <= d[i][j]) {
                    sz--;
                }
                left[j] = sz == 0 ? -1 : stack_idx[sz - 1];
                stack_val[sz] = d[i][j];
                stack_idx[sz++] = j;
            }

            sz = 0;
            for (int j = m - 1; j >= 0; j--) {
                while (sz > 0 && stack_val[sz - 1] <= d[i][j]) {
                    sz--;
                }
                right[j] = sz == 0 ? m : stack_idx[sz - 1];
                stack_val[sz] = d[i][j];
                stack_idx[sz++] = j;
            }

            for (int j = 0; j < m; j++) {
                int cur = (i - d[i][j]) * (right[j] - left[j] - 1);
                if (iPos[0] <= d[i][j] || iPos[0] > i || jPos[0] >= right[j] || jPos[0] <= left[j]) {
                    continue;
                }
                if (cur > maxSq) {
                    maxSq = cur;
                    i_left = i;
                    j_left = left[j] + 1;
                    h = i - d[i][j];
                    w = right[j] - left[j] - 1;
                }
            }
        }

        for (int i = i_left; i > i_left - h; i--) {
            for (int j = j_left; j < j_left + w; j++) {
                matrix[i][j] = 'a';
            }
        }

        fillRectangle(0, i_left - h, 0, m - 1);
        fillRectangle(i_left + 1, n - 1, 0, m - 1);
        fillRectangle(i_left - h + 1, i_left, 0, j_left - 1);
        fillRectangle(i_left - h + 1, i_left, j_left + w, m - 1);

        for (int letter = 0; letter < 26; letter++) {
            if (iPos[letter] == -1) {
                continue;
            }
            matrix[iPos[letter]][jPos[letter]] = (char)('A' + letter);
        }

        PrintWriter out = new PrintWriter(System.out);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                out.print(matrix[i][j]);
            }
            out.print('\n');
        }
        out.flush();
    }
}