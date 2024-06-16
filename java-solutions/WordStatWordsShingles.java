import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class WordStatWordsShingles {

    public static final int SUBSTR_LEN = 3;

    public static boolean correctCharacter(char c) {
        return Character.isAlphabetic(c) || c == '\'' || (Character.getType(c) == Character.DASH_PUNCTUATION);
    }

    public static void addWord(String word, Map<String, Integer> wordToCount) {
        String stringWord = word.toLowerCase();
        if (wordToCount.containsKey(stringWord)) {
            int cnt = wordToCount.get(stringWord);
            wordToCount.put(stringWord, cnt + 1);
        } else {
            wordToCount.put(stringWord, 1);
        }
    }

    public static void addWords(String word, Map<String, Integer> wordToCount) {
        if (word.length() < SUBSTR_LEN) {
            addWord(word, wordToCount);
            return;
        }
        for (int i = 0; i < word.length() - 2; i++) {
            addWord(word.substring(i, i + 3), wordToCount);
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Must be 2 arguments [\"input_file\", \"output_file\"]");
            System.exit(1);
        }
        MyScanner reader = null;
        Map<String, Integer> wordToCount = new TreeMap<>();
        try {
            reader = new MyScanner(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
            StringBuilder word = new StringBuilder();
            boolean isCollecting = false;

            while (true) {
                char symbol = reader.read();
                if (symbol == MyScanner.getEOFChar()) {
                    if (!word.isEmpty()) {
                        addWord(word.toString(), wordToCount);
                    }
                    break;
                }

                if (correctCharacter(symbol)) {
                    word.append(symbol);
                    isCollecting = true;
                } else {
                    if (isCollecting) {
                        addWords(word.toString(), wordToCount);
                        word.delete(0, word.length());
                    }
                    isCollecting = false;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error, input file not found. " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Input error: " + e.getMessage());
            System.exit(1);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Error: couldn't close reader. " + e.getMessage());
            }
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
            for (Map.Entry<String, Integer> entry : wordToCount.entrySet()) {
                String word = entry.getKey();
                writer.write(word);
                writer.write(' ');
                writer.write(entry.getValue().toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("Error: couldn't close writer. " + e.getMessage());
            }
        }
    }
}
