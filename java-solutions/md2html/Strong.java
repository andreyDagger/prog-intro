package md2html;

import java.util.List;

public class Strong extends DedicatedText {
    public Strong(List<AbstractText> textList) {
        super(textList);
        this.htmlLabel = "strong";
    }
}
