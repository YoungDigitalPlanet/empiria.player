package eu.ydp.empiria.player.client.module.ordering.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Optional;

public class OrderingItemsDao {

	private final Map<String, OrderingItem> orderingItemsMap = new HashMap<String, OrderingItem>();
	private OrderingItem lastClickedItem;

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
	
	public void setLastClickedItem(OrderingItem lastClickedItem){
		this.lastClickedItem = lastClickedItem;
	}

	public Optional<OrderingItem> getLastClickedItem() {
		return Optional.fromNullable(lastClickedItem);
	}
}
