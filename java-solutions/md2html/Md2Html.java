package md2html;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class Md2Html {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Must be 2 file names in the arguments");
            return;
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("Couldn't open " + args[0] + " file. " + e.getMessage());
            return;
        }

        StringBuilder fileText = new StringBuilder();
        int c;
        try {
            while ((c = reader.read()) != -1) {
                fileText.append((char) c);
            }
        } catch (IOException e) {
            System.out.println("Couldn't read file " + args[0] + ". " + e.getMessage());
            System.exit(1);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println("Couldn't close reader. " + e.getMessage());
            }
        }

        HtmlMarkup markup = null;
        try {
            MarkdownParser markdownParser = new MarkdownParser();
            markup = markdownParser.parseFullFileToHtml(fileText.toString());
        } catch (UnknownMainHtmlComponent e) {
            System.err.println(e.getMessage());
            return;
        }

        StringBuilder result = new StringBuilder();
        markup.toHtml(result);

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
            writer.write(result.toString());
        } catch (IOException e) {
            System.err.println("IOException. " + e.getMessage());
            System.exit(1);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("Couldn't close reader. " + e.getMessage());
                System.exit(1);
            }
        }
    }
}

