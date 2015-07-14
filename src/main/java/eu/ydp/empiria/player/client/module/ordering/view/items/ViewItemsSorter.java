package eu.ydp.empiria.player.client.module.ordering.view.items;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.IsWidget;

import java.util.List;
import java.util.Map;

public class ViewItemsSorter {
    public List<IsWidget> getItemsInOrder(List<String> itemsOrder, final Map<String, ? extends IsWidget> items) {
        return Lists.transform(itemsOrder, new Function<String, IsWidget>() {
            @Override
            public IsWidget apply(String itemId) {
                return items.get(itemId);
            }
        });
    }
}
