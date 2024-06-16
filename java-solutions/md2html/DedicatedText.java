package md2html;

import java.util.List;

public class DedicatedText extends AbstractText {
    private final List<AbstractText> textList;
    protected String htmlLabel;

    public DedicatedText(List<AbstractText> textList) {
        this.textList = textList;
    }

    @Override
    public void toHtml(StringBuilder result) {
        result.append("<").append(htmlLabel).append(">");
        for (AbstractText text : textList) {
            text.toHtml(result);
        }
        result.append("</").append(htmlLabel).append(">");
    }
}
