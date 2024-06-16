package md2html;

import java.util.List;

public class HtmlMarkup implements HtmlComponent {
    private final List<MainHtmlComponent> componentsList;
    private final String LINE_SEPARATOR = System.lineSeparator();

    public HtmlMarkup(List<MainHtmlComponent> componentsList) {
        this.componentsList = componentsList;
    }

    @Override
    public void toHtml(StringBuilder result) {
        for (MainHtmlComponent component : componentsList) {
            StringBuilder temp = new StringBuilder();
            component.toHtml(temp);
            result.append(temp).append(LINE_SEPARATOR);
        }
    }
}
