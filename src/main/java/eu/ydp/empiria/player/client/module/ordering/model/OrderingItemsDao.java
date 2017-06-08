/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
