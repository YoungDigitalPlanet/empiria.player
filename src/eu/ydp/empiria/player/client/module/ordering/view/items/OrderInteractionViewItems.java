package eu.ydp.empiria.player.client.module.ordering.view.items;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;

public interface OrderInteractionViewItems {

	OrderInteractionViewItem addItem(String itemId, IsWidget widget);

	OrderInteractionViewItem getItem(String itemId);

	List<IsWidget> getItemsInOrder(List<String> itemsOrder);

}