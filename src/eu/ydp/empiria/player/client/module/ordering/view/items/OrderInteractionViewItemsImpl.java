package eu.ydp.empiria.player.client.module.ordering.view.items;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.OrderInteractionModuleFactory;
import eu.ydp.empiria.player.client.module.ordering.view.OrderItemClickListener;

public class OrderInteractionViewItemsImpl implements OrderInteractionViewItems {

	private final Map<String, OrderInteractionViewItem> widgets = Maps.newHashMap();

	@Inject
	private ViewItemsSorter itemsSorter;

	@Inject
	private OrderInteractionViewItemClickEventDelegator clickEventDelegator;

	@Inject
	private OrderInteractionModuleFactory moduleFactory;

	private OrderItemClickListener itemClickListener;

	@Override
	public OrderInteractionViewItem addItem(final String itemId, IsWidget widget) {
		OrderInteractionViewItem viewItem = createAndPutViewItem(itemId, widget);
		addClickListenerToItem(viewItem);
		return viewItem;
	}

	private void addClickListenerToItem(OrderInteractionViewItem viewItem) {
		clickEventDelegator.bind(viewItem, itemClickListener);
	}

	private OrderInteractionViewItem createAndPutViewItem(String itemId, IsWidget widget) {
		OrderInteractionViewItem viewItem = moduleFactory.getOrderInteractionViewItem(widget,itemId);
		widgets.put(itemId, viewItem);
		return viewItem;
	}

	@Override
	public OrderInteractionViewItem getItem(String itemId) {
		return widgets.get(itemId);
	}

		@Override
	public List<IsWidget> getItemsInOrder(List<String> itemsOrder) {
		return itemsSorter.getItemsInOrder(itemsOrder, widgets);
	}

	@Override
	public void setItemClickListener(OrderItemClickListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}
}
