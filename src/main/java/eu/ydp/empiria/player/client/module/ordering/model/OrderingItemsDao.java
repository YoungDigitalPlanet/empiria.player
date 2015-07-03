package eu.ydp.empiria.player.client.module.ordering.model;

import com.google.common.collect.Lists;

import java.util.*;

public class OrderingItemsDao {

    private final Map<String, OrderingItem> orderingItemsMap = new HashMap<String, OrderingItem>();
    private List<String> itemsOrder;

    public void addItem(OrderingItem orderingItem) {
        orderingItemsMap.put(orderingItem.getId(), orderingItem);
    }

    public Collection<OrderingItem> getItems() {
        Collection<OrderingItem> items = orderingItemsMap.values();
        List<OrderingItem> copyOfItems = new ArrayList<OrderingItem>(items);
        return copyOfItems;
    }

    public OrderingItem getItem(String itemId) {
        OrderingItem orderingItem = orderingItemsMap.get(itemId);
        return orderingItem;
    }

    public List<String> getItemsOrder() {
        return this.itemsOrder;
    }

    public void setItemsOrder(List<String> itemsOrder) {
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
