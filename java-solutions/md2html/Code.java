package md2html;

import java.util.List;

public class Code extends DedicatedText {
    public Code(List<AbstractText> textList) {
        super(textList);
        this.htmlLabel = "code";
    }
}
