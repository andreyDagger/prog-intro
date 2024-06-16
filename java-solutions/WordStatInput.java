import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class WordStatInput {

    public static boolean correctCharacter(char c) {
        return Character.isAlphabetic(c) || c == '\'' || (Character.getType(c) == Character.DASH_PUNCTUATION);
    }

    public static void addWord(String word, ArrayList<String> words, HashMap<String, Integer> wordToCount) {
        String stringWord = word.toLowerCase();
        if (wordToCount.containsKey(stringWord)) {
            int cnt = wordToCount.get(stringWord);
            wordToCount.put(stringWord, cnt + 1);
        } else {
            words.add(stringWord);
            wordToCount.put(stringWord, 1);
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Must be 2 arguments [\"input_file\", \"output_file\"]");
            System.exit(1);
        }
        MyScanner reader = null;
        HashMap<String, Integer> wordToCount = new HashMap<String, Integer>();
        ArrayList<String> words = new ArrayList<>();
        try {
            reader = new MyScanner(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
            StringBuilder word = new StringBuilder();
            boolean isCollecting = false;
            while (true) {
                char symbol = reader.read();
                if (symbol == MyScanner.getEOFChar()) {
                    if (!word.isEmpty()) {
                        addWord(word.toString(), words, wordToCount);
                    }
                    break;
                }

                if (correctCharacter(symbol)) {
                    word.append(symbol);
                    isCollecting = true;
                } else {
                    if (isCollecting) {
                        addWord(word.toString(), words, wordToCount);
                        word.delete(0, word.length());
                    }
                    isCollecting = false;
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Error, file not found. " + e.getMessage());
            System.exit(1);
        }
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (IOException e) {
                System.out.println("Error: couldn't close reader. " + e.getMessage());
                System.exit(1);
            }
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
            for (int i = 0; i < words.size(); i++) {
                String word = words.get(i);
                writer.write(word);
                writer.write(' ');
                writer.write(wordToCount.get(word).toString());
                if (i < words.size() - 1) {
                    writer.newLine();
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (IOException e) {
                System.out.println("Error: couldn't close writer. " + e.getMessage());
                System.exit(1);
            }
        }
    }
}
