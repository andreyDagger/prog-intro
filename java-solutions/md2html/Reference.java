package md2html;

import java.util.List;

public class Reference extends AbstractText {
    private final String url;
    List<AbstractText> messageTextList;

    public Reference(String url, List<AbstractText> messageTextList) {
        this.url = url;
        this.messageTextList = messageTextList;
    }

    public void toHtml(StringBuilder result) {
        result.append("<a href='").append(url).append("'>");
        for (AbstractText text : messageTextList) {
            text.toHtml(result);
        }
        result.append("</a>");
    }
}
