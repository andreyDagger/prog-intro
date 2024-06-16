package md2html;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MarkdownParser {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    private static final String screenCommand = "\\";

    private abstract class Checker {
        protected boolean ignoring = false;
        protected abstract boolean isStartsWithThisCommand(String text, int startIndex);
        protected abstract void checkIfNeedIgnore(String text);
        protected abstract Token makeToken(String text, int startIndex);
    }

    private class StrongChecker extends Checker {
        private static final String strongCommandVar1 = "**";
        private static final String strongCommandVar2 = "__";

        protected boolean isStartsWithThisCommand(String text, int startIndex) {
            return (text.startsWith(strongCommandVar1, startIndex) || text.startsWith(strongCommandVar2, startIndex)) && !ignoring;
        }

        protected void checkIfNeedIgnore(String text) {
            ignoring = false;
            if (count(text, strongCommandVar1) == 1 || count(text, strongCommandVar2) == 1) {
                ignoring = true;
            }
        }

        protected Token makeToken(String text, int startIndex) {
            String command = text.substring(startIndex, startIndex + StrongChecker.strongCommandVar1.length());

            int leftBorder = startIndex + StrongChecker.strongCommandVar1.length();
            int rightBorder = text.indexOf(command, leftBorder) - 1;
            TokenType resultTokenType = TokenType.Strong;
            return new Token(leftBorder, rightBorder, resultTokenType);
        }
    }

    private class EmphasisChecker extends Checker {
        private static final String emphasisCommandVar1 = "*";
        private static final String emphasisCommandVar2 = "_";

        protected boolean isStartsWithThisCommand(String text, int startIndex) {
            return (text.startsWith(emphasisCommandVar1, startIndex) || text.startsWith(emphasisCommandVar2, startIndex)) && !ignoring;
        }

        protected void checkIfNeedIgnore(String text) {
            ignoring = false;
            if (count(text, emphasisCommandVar1) - count(text, screenCommand + emphasisCommandVar1) == 1 ||
                    count(text, emphasisCommandVar2) - count(text, screenCommand + emphasisCommandVar2) == 1) {
                ignoring = true;
            }
        }

        private static int findFirstEmphasisCommand(String text, int startIndex, String emphasisCommand) {
            while (startIndex < text.length()) {
                if (text.startsWith(StrongChecker.strongCommandVar1, startIndex)) {
                    startIndex += StrongChecker.strongCommandVar1.length();
                    continue;
                }
                if (text.startsWith(StrongChecker.strongCommandVar2, startIndex)) {
                    startIndex += StrongChecker.strongCommandVar2.length();
                    continue;
                }

                if (text.startsWith(emphasisCommand, startIndex)) {
                    return startIndex;
                }
                startIndex++;
            }
            return -1;
        }

        protected Token makeToken(String text, int startIndex) {
            String command = text.substring(startIndex, startIndex + EmphasisChecker.emphasisCommandVar1.length());

            int leftBorder = startIndex + EmphasisChecker.emphasisCommandVar1.length();
            int rightBorder = findFirstEmphasisCommand(text, leftBorder, command) - 1;
            TokenType resultTokenType = TokenType.Emphasis;
            return new Token(leftBorder, rightBorder, resultTokenType);
        }
    }

    private class StrikeoutChecker extends Checker {
        private static final String strikeoutCommand = "--";

        protected boolean isStartsWithThisCommand(String text, int startIndex) {
            return text.startsWith(strikeoutCommand, startIndex) && !ignoring;
        }

        protected void checkIfNeedIgnore(String text) {
            ignoring = false;
            if (count(text, strikeoutCommand) == 1) {
                ignoring = true;
            }
        }

        protected Token makeToken(String text, int startIndex) {
            String command = text.substring(startIndex, startIndex + strikeoutCommand.length());

            int leftBorder = startIndex + StrikeoutChecker.strikeoutCommand.length();
            int rightBorder = text.indexOf(command, leftBorder) - 1;
            TokenType resultTokenType = TokenType.Strikeout;
            return new Token(leftBorder, rightBorder, resultTokenType);
        }
    }

    private class CodeChecker extends Checker {
        private static final String codeCommand = "`";

        protected boolean isStartsWithThisCommand(String text, int startIndex) {
            return text.startsWith(codeCommand, startIndex) && !ignoring;
        }

        protected void checkIfNeedIgnore(String text) {
            ignoring = false;
            if (count(text, codeCommand) == 1) {
                ignoring = true;
            }
        }

        protected Token makeToken(String text, int startIndex) {
            String command = text.substring(startIndex, startIndex + CodeChecker.codeCommand.length());

            int leftBorder = startIndex + CodeChecker.codeCommand.length();
            int rightBorder = text.indexOf(command, leftBorder) - 1;
            TokenType resultTokenType = TokenType.Code;
            return new Token(leftBorder, rightBorder, resultTokenType);
        }
    }

    private class ReferenceChecker extends Checker {
        private static final String referenceUrlOpenCommand = "(";
        private static final String referenceUrlCloseCommand = ")";
        private static final String referenceMessageOpenCommand = "[";
        private static final String referenceMessageCloseCommand = "]";

        private boolean isStartsWithUrl(String text, int startIndex) {
            return text.startsWith(referenceUrlOpenCommand, startIndex) &&
                    text.indexOf(referenceUrlCloseCommand, startIndex) != -1;
        }

        protected void checkIfNeedIgnore(String text) {
            // Don't need to ignore references
        }

        protected boolean isStartsWithThisCommand(String text, int startIndex) {
            if (!text.startsWith(referenceMessageOpenCommand, startIndex) || ignoring) {
                return false;
            }
            if (text.startsWith(referenceMessageCloseCommand) && isStartsWithUrl(text, startIndex + 1))
                return true;
            int messageClose = text.indexOf(referenceMessageCloseCommand, startIndex);
            if (messageClose == -1) {
                return false;
            }
            return isStartsWithUrl(text, messageClose + 1);
        }

        private ReferenceToken getReferenceToken(String text, int startIndex) {
            // [message](url)
            // [ - refMessageOpenCommand
            // ] - refMessageCloseCommand
            // { - refUrlOpenCommand
            // } - refUrlCloseCommand

            int leftMessageBorder = startIndex + referenceUrlOpenCommand.length();
            int rightMessageBorder = text.indexOf(referenceMessageCloseCommand, leftMessageBorder) - 1;
            int leftUrlBorder = rightMessageBorder + referenceMessageCloseCommand.length()
                    + referenceUrlOpenCommand.length() + 1;
            int rightUrlBorder = text.indexOf(referenceUrlCloseCommand, leftUrlBorder) - 1;

            return new ReferenceToken(leftMessageBorder, rightMessageBorder, leftUrlBorder, rightUrlBorder);
        }

        protected Token makeToken(String text, int startIndex) {
            return getReferenceToken(text, startIndex);
        }
    }

    private final StrongChecker strongChecker = new StrongChecker();
    private final EmphasisChecker emphasisChecker = new EmphasisChecker();
    private final StrikeoutChecker strikeoutChecker = new StrikeoutChecker();
    private final CodeChecker codeChecker = new CodeChecker();
    private final ReferenceChecker referenceChecker = new ReferenceChecker();

    private static int count(String text, String c) {
        return count(text, c, 0);
    }

    private static int count(String text, String c, int offset) {
        int res = 0;
        for (int i = offset; i < text.length(); i++) {
            if (text.startsWith(c, i)) {
                res++;
            }
        }
        return res;
    }

    private enum TokenType {
        Strong,
        Emphasis,
        Strikeout,
        Text,
        Code,
        Reference
    }

    private static class Token {
        private final int leftBorder;
        private final int rightBorder;
        private final TokenType tokenType;

        public Token(int leftBorder, int rightBorder, TokenType tokenType) {
            this.leftBorder = leftBorder;
            this.rightBorder = rightBorder;
            this.tokenType = tokenType;
        }
    }

    private static class ReferenceToken extends Token {
        public int leftMessageBorder;
        public int rightMessageBorder;
        public int leftUrlBorder;
        public int rightUrlBorder;

        public ReferenceToken(int leftMessageBorder, int rightMessageBorder,
                              int leftUrlBorder, int rightUrlBorder) {
            super(leftMessageBorder, rightUrlBorder, TokenType.Reference);
            this.leftMessageBorder = leftMessageBorder;
            this.rightMessageBorder = rightMessageBorder;
            this.leftUrlBorder = leftUrlBorder;
            this.rightUrlBorder = rightUrlBorder;
        }
    }

    private boolean isCommandChar(String text, int startIndex) {
        if ((text.startsWith(ReferenceChecker.referenceMessageCloseCommand, startIndex)
                && text.startsWith(ReferenceChecker.referenceUrlOpenCommand, startIndex + 1))
                || referenceChecker.isStartsWithThisCommand(text, startIndex)) {
            return true;
        }

        return emphasisChecker.isStartsWithThisCommand(text, startIndex) ||
                strongChecker.isStartsWithThisCommand(text, startIndex) ||
                strikeoutChecker.isStartsWithThisCommand(text, startIndex) ||
                codeChecker.isStartsWithThisCommand(text, startIndex) ||
                referenceChecker.isStartsWithThisCommand(text, startIndex);
    }

    private static boolean isScreenChar(String text, int startIndex) {
        if (startIndex == 0) {
            return false;
        }
        return text.startsWith(screenCommand, startIndex - 1) &&
                (text.startsWith(EmphasisChecker.emphasisCommandVar1, startIndex) ||
                        text.startsWith(EmphasisChecker.emphasisCommandVar2, startIndex));
    }

    private int getFirstCommandChar(String text, int startIndex) {
        while (startIndex < text.length() &&
                (!isCommandChar(text, startIndex) || isScreenChar(text, startIndex))) {
            startIndex++;
        }
        return startIndex;
    }

    private Token nextTextToken(String text, int startIndex) {
        if (strongChecker.isStartsWithThisCommand(text, startIndex)) {
            return strongChecker.makeToken(text, startIndex);
        } else if (emphasisChecker.isStartsWithThisCommand(text, startIndex)) {
            return emphasisChecker.makeToken(text, startIndex);
        } else if (strikeoutChecker.isStartsWithThisCommand(text, startIndex)) {
            return strikeoutChecker.makeToken(text, startIndex);
        } else if (codeChecker.isStartsWithThisCommand(text, startIndex)) {
            return codeChecker.makeToken(text, startIndex);
        } else if (referenceChecker.isStartsWithThisCommand(text, startIndex)) {
            return referenceChecker.makeToken(text, startIndex);
        } else {
            int leftBorder = startIndex;
            int rightBorder = getFirstCommandChar(text, startIndex) - 1;
            return new Token(leftBorder, rightBorder, TokenType.Text);
        }
    }

    private static int headerLevel(String text) {
        if (text.isEmpty()) {
            throw new AssertionError();
        }

        if (text.charAt(0) != '#') {
            return -1;
        }

        int idx = 0;
        while (text.charAt(idx) == '#') {
            idx++;
        }

        if (text.charAt(idx) != ' ') {
            return -1;
        }
        else {
            return idx;
        }
    }

    private static boolean isParagraph(String text) {
        return headerLevel(text) == -1;
    }

    private static String removeScreenCommands(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (isScreenChar(text, i)) {
                result.deleteCharAt(result.length() - 1);
            }
            result.append(text.charAt(i));
        }
        return result.toString();
    }

    private List<AbstractText> parseText(String text) {
        List<AbstractText> result = new ArrayList<>();
        int startIndex = 0;
        while (startIndex < text.length()) {
            Token token = nextTextToken(text, startIndex);
            String tokenString = removeScreenCommands(text.substring(token.leftBorder, token.rightBorder + 1));

            switch (token.tokenType) {
                case Text -> {
                    result.add(new Text(tokenString));
                    startIndex = token.rightBorder + 1;
                }
                case Strong -> {
                    result.add(new Strong(parseText(tokenString)));
                    startIndex = token.rightBorder + StrongChecker.strongCommandVar1.length() + 1;
                }
                case Emphasis -> {
                    result.add(new Emphasis(parseText(tokenString)));
                    startIndex = token.rightBorder + EmphasisChecker.emphasisCommandVar1.length() + 1;
                }
                case Strikeout -> {
                    result.add(new Strikeout(parseText(tokenString)));
                    startIndex = token.rightBorder + StrikeoutChecker.strikeoutCommand.length() + 1;
                }
                case Code -> {
                    result.add(new Code(parseText(tokenString)));
                    startIndex = token.rightBorder + CodeChecker.codeCommand.length() + 1;
                }
                case Reference -> { // [message](url)
                    ReferenceToken referenceToken = (ReferenceToken) token;
                    String message = text.substring(referenceToken.leftMessageBorder,
                            referenceToken.rightMessageBorder + 1);
                    String url = text.substring(referenceToken.leftUrlBorder, referenceToken.rightUrlBorder + 1);

                    result.add(new Reference(url, parseText(message)));
                    startIndex = token.rightBorder + ReferenceChecker.referenceUrlCloseCommand.length() + 1;
                }
            }
        }
        return result;
    }

    private static String replaceSpecialChars(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private void checkCharsToIgnore(String text) {
        strongChecker.checkIfNeedIgnore(text);
        emphasisChecker.checkIfNeedIgnore(text);
        strikeoutChecker.checkIfNeedIgnore(text);
        codeChecker.checkIfNeedIgnore(text);
    }

    private MainHtmlComponent parseBlock(String block) throws UnknownMainHtmlComponent {
        block = replaceSpecialChars(block);
        block = block.stripTrailing();

        checkCharsToIgnore(block);

        int headerLevel = headerLevel(block);
        if (headerLevel != -1) {
            int level = 0;
            int startIndex = 0;

            while (block.charAt(startIndex) == '#') {
                level++;
                startIndex++;
            }

            String text = block.substring(startIndex + 1);
            return new Header(parseText(text), level);
        } else if (isParagraph(block)) {
            return new Paragraph(parseText(block));
        } else {
            throw new UnknownMainHtmlComponent(block);
        }
    }

    private static List<String> getBlockDistribution(String markdownText) {
        Scanner scanner = new Scanner(new StringReader(markdownText));
        List<String> result = new ArrayList<>();
        StringBuilder currentBlock = new StringBuilder();

        while (scanner.hasNextLine()) {
            String currentLine = scanner.nextLine();
            if (currentLine.isBlank()) {
                if (!currentBlock.isEmpty()) {
                    result.add(currentBlock.toString());
                    currentBlock.delete(0, currentBlock.length());
                }
            } else {
                currentBlock.append(currentLine).append(LINE_SEPARATOR);
            }
        }
        if (!currentBlock.isEmpty()) {
            result.add(currentBlock.toString());
        }

        return result;
    }

    public HtmlMarkup parseFullFileToHtml(String markdownText) throws UnknownMainHtmlComponent {
        List<String> blockDistribution = getBlockDistribution(markdownText);
        List<MainHtmlComponent> htmlComponents = new ArrayList<>();

        for (String block : blockDistribution) {
            htmlComponents.add(parseBlock(block));
        }

        return new HtmlMarkup(htmlComponents);
    }
}
