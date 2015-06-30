package eu.ydp.empiria.player.client.module.ordering.view.items;

import com.google.gwt.user.client.ui.IsWidget;

import java.util.List;

public interface OrderInteractionViewItems {

    OrderInteractionViewItem addItem(String itemId, IsWidget widget);

    OrderInteractionViewItem getItem(String itemId);

    List<IsWidget> getItemsInOrder(List<String> itemsOrder);

}
