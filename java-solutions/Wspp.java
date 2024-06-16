import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class Wspp {

    public static boolean correctCharacter(char c) {
        return Character.isAlphabetic(c) || c == '\'' || (Character.getType(c) == Character.DASH_PUNCTUATION);
    }

    public static void addWord(String word, int pos, Map<String, IntList> wordToCount) {
        String loweredWord = word.toLowerCase();
        if (!wordToCount.containsKey(loweredWord)) {
            wordToCount.put(loweredWord, new IntList());
        }
        wordToCount.get(loweredWord).pushBack(pos);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Error: there must be 2 arguments [input_file, output_file]");
            System.exit(1);
        }
        MyScanner reader = null;
        Map<String, IntList> wordToPositions = new LinkedHashMap<>();
        try {
            reader = new MyScanner(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
            StringBuilder word = new StringBuilder();
            boolean isCollecting = false;
            int pos = 0;
            while (true) {

                char symbol = reader.read();
                if (MyScanner.isEOF(symbol)) {
                    if (!word.isEmpty()) {
                        pos++;
                        addWord(word.toString(), pos, wordToPositions);
                    }
                    break;
                }

                if (correctCharacter(symbol)) {
                    word.append(symbol);
                    isCollecting = true;
                } else {
                    if (isCollecting) {
                        pos++;
                        addWord(word.toString(), pos, wordToPositions);
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
            for (Map.Entry<String, IntList> entry : wordToPositions.entrySet()) {
                IntList positionsList = entry.getValue();

                writer.write(entry.getKey() + ' ');
                writer.write(Integer.toString(positionsList.size()));
                for (int i = 0; i < positionsList.size(); i++) {
                    writer.write(' ' + Integer.toString(positionsList.get(i)));
                }
                writer.newLine();
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
