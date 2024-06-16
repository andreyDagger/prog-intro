package expression.parser;

import java.nio.CharBuffer;

public interface CharSource {
    boolean hasNext();
    char next();
    char[] read(int count);
    IllegalArgumentException error(String message);
}
