package eu.ydp.empiria.player.client.module.ordering.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

public class OrderingItemsDao {

	private final Map<String, OrderingItem> orderingItemsMap = new HashMap<String, OrderingItem>();
	private OrderingItem lastClickedItem;
	private List<String> itemsOrder;

	public void addItem(OrderingItem orderingItem) {
		orderingItemsMap.put(orderingItem.getId(), orderingItem);
	}

	public Collection<OrderingItem> getItems(){
		Collection<OrderingItem> items = orderingItemsMap.values();
		List<OrderingItem> copyOfItems = new ArrayList<OrderingItem>(items);
		return copyOfItems;
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
	
	public List<String> getItemsOrder(){
		return this.itemsOrder;
	}
	
	public void setItemsOrder(List<String> itemsOrder){
		this.itemsOrder = itemsOrder;
	}
	
	public void createInitialItemsOrder() {
		List<String> initialItemsOrder = Lists.newArrayList();
		Collection<OrderingItem> items = getItems();
		for (OrderingItem orderingItem : items) {
			String itemId = orderingItem.getId();
			initialItemsOrder.add(itemId);
		}
		this.itemsOrder = initialItemsOrder;
	}
}
