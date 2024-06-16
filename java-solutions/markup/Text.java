package markup;

public class Text extends AbstractText {
    private String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void toMarkdown(StringBuilder result) {
        result.append(text);
    }

    @Override
    public void toTex(StringBuilder result) { result.append(text); }
}
