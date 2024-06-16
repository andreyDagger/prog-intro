package markup;

import java.util.List;

public class AbstractList implements Texable, Itemizable {
    protected List<ListItem> items; // NOTE модфикатолры доступа
    protected String texCommand;

    public AbstractList(List<ListItem> items) {
        this.items = List.copyOf(items);
    }


    @Override
    public void toTex(StringBuilder result) {
        result.append("\\begin{" + texCommand + "}");
        for (ListItem item : items) {
            StringBuilder temp = new StringBuilder();
            item.toTex(temp);
            result.append(temp);
        }
        result.append("\\end{" + texCommand + "}");
    }
}
