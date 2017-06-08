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

package eu.ydp.empiria.player.client.module.ordering.view.items;

import com.google.common.collect.Maps;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.OrderInteractionModuleFactory;

import java.util.List;
import java.util.Map;

public class OrderInteractionViewItemsImpl implements OrderInteractionViewItems {

    private final Map<String, OrderInteractionViewItem> widgets = Maps.newHashMap();

    @Inject
    private ViewItemsSorter itemsSorter;

    @Inject
    private OrderInteractionModuleFactory moduleFactory;

    @Override
    public OrderInteractionViewItem addItem(final String itemId, IsWidget widget) {
        OrderInteractionViewItem viewItem = createAndPutViewItem(itemId, widget);
        return viewItem;
    }

    private OrderInteractionViewItem createAndPutViewItem(String itemId, IsWidget widget) {
        OrderInteractionViewItem viewItem = moduleFactory.getOrderInteractionViewItem(widget, itemId);
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
}
