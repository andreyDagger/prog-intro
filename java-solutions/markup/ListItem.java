package markup;

import java.util.List;

public class ListItem implements Texable {
    private List<Itemizable> itemizableList;
    public ListItem(List<Itemizable> itemizableList) {
        this.itemizableList = List.copyOf(itemizableList);
    }

    @Override
    public void toTex(StringBuilder result) {
        result.append("\\item ");
        for (Itemizable item : itemizableList) {
            StringBuilder temp = new StringBuilder();
            item.toTex(temp);
            result.append(temp);
        }
    }
}
