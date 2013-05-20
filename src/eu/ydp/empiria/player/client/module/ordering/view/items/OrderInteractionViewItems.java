package eu.ydp.empiria.player.client.module.ordering.view.items;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.ordering.view.OrderItemClickListener;

public interface OrderInteractionViewItems {

	public abstract OrderInteractionViewItem addItem(String itemId, IsWidget widget);

	public abstract OrderInteractionViewItem getItem(String itemId);

	public abstract List<IsWidget> getItemsInOrder(List<String> itemsOrder);

	public abstract void setItemClickListener(OrderItemClickListener itemClickListener);

}