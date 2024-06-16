package markup;

import java.util.List;

public class DedicatedText extends AbstractText {
    protected List<AbstractText> textList;
    protected String surroundsBy;
    protected String texCommand;

    protected DedicatedText(List<AbstractText> textList) {
        this.textList = List.copyOf(textList);
    }

    @Override
    public void toMarkdown(StringBuilder result) {
        result.append(surroundsBy);
        for (AbstractText text : textList) {
            StringBuilder temp = new StringBuilder();
            text.toMarkdown(temp);
            result.append(temp);
        }
        result.append(surroundsBy);
    }

    @Override
    public void toTex(StringBuilder result) {
        result.append(texCommand + "{");
        for (AbstractText text : textList) {
            StringBuilder temp = new StringBuilder();
            text.toTex(temp);
            result.append(temp);
        }
        result.append("}");
    }
}
