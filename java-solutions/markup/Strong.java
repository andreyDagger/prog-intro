package markup;

import java.util.List;

public class Strong extends DedicatedText {
    public Strong(List<AbstractText> textList) {
        super(textList);
        this.surroundsBy = "__";
        this.texCommand = "\\textbf";
    }

    @Override
    public void toMarkdown(StringBuilder result) {
        super.toMarkdown(result);
    }

    @Override
    public void toTex(StringBuilder result) { super.toTex(result); }
}
