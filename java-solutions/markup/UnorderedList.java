package markup;

import java.util.List;

public class UnorderedList extends AbstractList {
    public UnorderedList(List<ListItem> items) {
        super(items);
        this.texCommand = "itemize";
    }
}
