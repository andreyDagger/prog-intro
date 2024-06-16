import java.io.*;
import java.math.BigInteger;
import java.util.NoSuchElementException;

public class MyScanner {
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

    private final Parser parser;

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

        this.parser = new MyParser();
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

    private static boolean isMac(){
        return System.lineSeparator().equals("\r");
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

        char[] temp = new char[count]; // changing all '\r\n' and '\r' to LINE_SEPARATOR
        int tempPos = 0;
        for (int i = 0; i < count; i++) {
            if (isMac()) {
                if (buffer[i] == '\r') {
                    temp[tempPos++] = LINE_SEPARATOR;
                }
                else {
                    temp[tempPos++] = buffer[i];
                }
            }
            else {
                if (i + 1 < count && buffer[i] == '\r' && buffer[i + 1] == '\n') {
                    temp[tempPos++] = LINE_SEPARATOR;
                    i++;
                } else {
                    temp[tempPos++] = buffer[i];
                }
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

    private void mark(int markedCharLimit) {
        if (markedCharLimit < 0 || markedCharLimit > MAX_MARKED_CHARACTERS) {
            throw new IllegalArgumentException("markedCharLimit must be in range: [0, MAX_MARKED_CHARACTERS]");
        }
        isMarked = true;
        markedPos = bufferPos;
        this.markedCharLimit = markedCharLimit;
    }

    private void reset() {
        bufferPos = markedPos;
        isMarked = false;
    }

    public boolean hasNextChar() throws IOException {
        mark(1);

        char symbol = read();
        if (symbol == EOF) {
            return false;
        }

        reset();
        return true;
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

    public boolean hasNextInt() throws IOException {
        mark(MAX_MARKED_CHARACTERS);
        boolean result;

        while (true) {
            char symbol = read();

            if (symbol == EOF) {
                result = false;
                break;
            } else if (!Character.isWhitespace(symbol)) {

                StringBuilder curString = new StringBuilder();
                curString.append(symbol);

                while (true) {
                    symbol = read();
                    if (symbol == EOF || Character.isWhitespace(symbol)) {
                        break;
                    }
                    curString.append(symbol);
                }

                try {
                    Integer.parseInt(curString.toString()); // :NOTE: why not parser.parseInteger()?
                } catch (NumberFormatException e) {
                    result = false;
                    break;
                }

                result = true;
                break;
            }
        }

        reset();
        return result;
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

            return parser.parseInteger(result);
        }

        isNoIntegersLeft = true;
        return 0; // No integer left in the line
    }

    public void close() throws IOException {
        reader.close();
    }
}
