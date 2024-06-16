package markup;

import java.util.List;

public class Paragraph implements Markdownable, Texable, Itemizable {
    private List<AbstractText> textList;

    public Paragraph(List<AbstractText> textList) {
        this.textList = List.copyOf(textList);
    }

    @Override
    public void toMarkdown(StringBuilder result) {
        for (AbstractText dedicatedText : textList) {
            StringBuilder temp = new StringBuilder();
            dedicatedText.toMarkdown(temp);
            result.append(temp);
        }
    }

    @Override
    public void toTex(StringBuilder result) {
        for (AbstractText dedicatedTex : textList) {
            StringBuilder temp = new StringBuilder();
            dedicatedTex.toTex(temp);
            result.append(temp);
        }
    }
}
