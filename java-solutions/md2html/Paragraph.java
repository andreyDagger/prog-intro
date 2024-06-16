package md2html;

import java.util.List;

public class Paragraph implements MainHtmlComponent {
    private final List<AbstractText> textList;

    public Paragraph(List<AbstractText> textList) {
        this.textList = textList;
    }

    @Override
    public void toHtml(StringBuilder result) {
        result.append("<p>");
        for (AbstractText text : textList) {
            text.toHtml(result);
        }
        result.append("</p>");
    }
}
