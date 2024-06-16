package markup;

import java.util.List;

public class Emphasis extends DedicatedText {
    public Emphasis(List<AbstractText> textList) {
        super(textList);
        this.surroundsBy = "*";
        this.texCommand = "\\emph";
    }

    @Override
    public void toMarkdown(StringBuilder result) {
        super.toMarkdown(result);
    }

    @Override
    public void toTex(StringBuilder result) { super.toTex(result); }
}
/**
 * __current-repo/java-solutions/markup/MarkupListTest.java:22: error: duplicate class: markup.MarkupListTest
 * public final class MarkupListTest {
 *              ^
 */