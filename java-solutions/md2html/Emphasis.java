package md2html;

import java.util.List;

public class Emphasis extends DedicatedText {
    public Emphasis(List<AbstractText> textList) {
        super(textList);
        this.htmlLabel = "em";
    }
}
