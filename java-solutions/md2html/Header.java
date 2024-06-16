package md2html;

import java.util.ArrayList;
import java.util.List;

public class Header implements MainHtmlComponent {
    private final int level;
    private final List<AbstractText> textList;

    public Header(List<AbstractText> textList, int level) {
        this.level = level;
        this.textList = textList;
    }

    public Header(int level) {
        this(new ArrayList<>(), level);
    }

    @Override
    public void toHtml(StringBuilder result) {
        result.append("<h").append(level).append(">");
        for (AbstractText abstractText : textList) {
            abstractText.toHtml(result);
        }
        result.append("</h").append(level).append(">");
    }
}
