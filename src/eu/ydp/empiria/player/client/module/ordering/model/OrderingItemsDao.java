package eu.ydp.empiria.player.client.module.ordering.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OrderingItemsDao {

	private Map<String, OrderingItem> orderingItemsMap = new HashMap<String, OrderingItem>();

	public void addItem(OrderingItem orderingItem) {
		orderingItemsMap.put(orderingItem.getId(), orderingItem);
	}

	public Collection<OrderingItem> getItems(){
		Collection<OrderingItem> items = orderingItemsMap.values();
		return items;
	}

	public OrderingItem getItem(String itemId) {
		OrderingItem orderingItem = orderingItemsMap.get(itemId);
		return orderingItem;
	}
}
