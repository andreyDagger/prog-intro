import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class WsppCountLastL {

    public static boolean correctCharacter(char c) {
        return Character.isAlphabetic(c) || c == '\'' || (Character.getType(c) == Character.DASH_PUNCTUATION);
    }

    public static void updPosAndCnt(String word, int pos,
                                    Map<String, Integer> wordToPositionInLine,
                                    Map<String, Integer> wordToCount) {
        String loweredWord = word.toLowerCase();

        int curCnt = wordToCount.getOrDefault(loweredWord, 0);
        wordToCount.put(loweredWord, curCnt + 1);

        wordToPositionInLine.put(loweredWord, pos);
    }

    public static void addPositions(Map<String, IntList> wordToPositions, Map<String, Integer> wordToPositionInLine) {
        for (Map.Entry<String, Integer> entry : wordToPositionInLine.entrySet()) {
            String word = entry.getKey();
            int pos = entry.getValue();

            wordToPositions.putIfAbsent(word, new IntList());
            wordToPositions.get(word).pushBack(pos);
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Error: there must be 2 arguments [input_file, output_file]");
            System.exit(1);
        }
        MyScanner reader = null;
        Map<String, IntList> wordToPositions = new LinkedHashMap<>();
        Map<String, Integer> wordToCount = new LinkedHashMap<>();
        try {
            Map<String, Integer> wordToPositionInLine = new LinkedHashMap<>();
            reader = new MyScanner(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
            StringBuilder word = new StringBuilder();
            boolean isCollecting = false;
            int pos = 0;

            while (true) {

                char symbol = reader.read();

                if (MyScanner.isLineSeparator(symbol) || MyScanner.isEOF(symbol)) {
                    if (!word.isEmpty()) {
                        pos++;
                        updPosAndCnt(word.toString(), pos, wordToPositionInLine, wordToCount);
                        word.delete(0, word.length());
                        isCollecting = false;
                    }

                    pos = 0;
                    addPositions(wordToPositions, wordToPositionInLine);
                    wordToPositionInLine.clear();

                    if (MyScanner.isEOF(symbol)) {
                        break;
                    }
                    else {
                        continue;
                    }
                }

                if (correctCharacter(symbol)) {
                    word.append(symbol);
                    isCollecting = true;
                } else {
                    if (isCollecting) {
                        pos++;
                        updPosAndCnt(word.toString(), pos, wordToPositionInLine, wordToCount);
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

        Map<Integer, ArrayList<String>> cntToWords = new TreeMap<>();
        for (Map.Entry<String, Integer> entry : wordToCount.entrySet()) {
            String word = entry.getKey();
            Integer countOccur = entry.getValue();

            if (!cntToWords.containsKey(countOccur)) {
                cntToWords.put(countOccur, new ArrayList<String>());
            }
            cntToWords.get(countOccur).add(word);
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
            for (Map.Entry<Integer, ArrayList<String>> entry : cntToWords.entrySet()) {
                ArrayList<String> allWords = entry.getValue();
                int posSize = entry.getKey();

                for (String word : allWords) {
                    writer.write(word + ' ');
                    writer.write(Integer.toString(posSize));

                    IntList allPositions = wordToPositions.get(word);
                    for (int i = 0; i < allPositions.size(); i++) {
                        writer.write(' ' + Integer.toString(allPositions.get(i)));
                    }

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
