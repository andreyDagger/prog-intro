package md2html;

import java.util.List;

public class Strikeout extends DedicatedText {

    public Strikeout(List<AbstractText> textList) {
        super(textList);
        this.htmlLabel = "s";
    }
}
