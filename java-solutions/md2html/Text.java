package md2html;

public class Text extends AbstractText {
    private String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void toHtml(StringBuilder result) {
        result.append(text);
    }
}
