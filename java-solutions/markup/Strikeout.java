package markup;

import java.util.List;

public class Strikeout extends DedicatedText {
    public Strikeout(List<AbstractText> textList) {
        super(textList);
        this.surroundsBy = "~";
        this.texCommand = "\\textst";
    }

    @Override
    public void toMarkdown(StringBuilder result) {
        super.toMarkdown(result);
    }

    @Override
    public void toTex(StringBuilder result) { super.toTex(result); }
}
