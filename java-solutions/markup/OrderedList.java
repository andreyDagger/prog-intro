package markup;

import java.util.List;

public class OrderedList extends AbstractList {
    public OrderedList(List<ListItem> items) {
        super(items);
        this.texCommand = "enumerate";
    }
}
