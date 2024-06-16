package md2html;

public class UnknownMainHtmlComponent extends Exception {
    public UnknownMainHtmlComponent(String htmlText) {
        super("Unknown MainHtmlComponent:" + System.lineSeparator() + System.lineSeparator() + htmlText);
    }
}
