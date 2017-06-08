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

package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

public class ConnectionItemsMockCreator {

    public ConnectionItems createConnectionItems(int countLeft, int itemOffsetLeft, int countRight, int itemOffsetRight, int itemWidth) {
        ConnectionItems items = mock(ConnectionItems.class);

        ArrayList<ConnectionItem> lefts = createItemsCollection(countLeft, itemOffsetLeft, itemWidth);
        stub(items.getLeftItems()).toReturn(lefts);

        ArrayList<ConnectionItem> rights = createItemsCollection(countRight, itemOffsetRight, itemWidth);
        stub(items.getRightItems()).toReturn(rights);

        List<ConnectionItem> allItems = Lists.newArrayList();
        allItems.addAll(lefts);
        allItems.addAll(rights);
        stub(items.getAllItems()).toReturn(allItems);

        return items;
    }

    private ArrayList<ConnectionItem> createItemsCollection(int count, int offsetLeft, int width) {
        ArrayList<ConnectionItem> lefts = new ArrayList<ConnectionItem>();
        for (int i = 0; i < count; i++) {
            ConnectionItem item = createItem(offsetLeft, width);
            lefts.add(item);
        }
        return lefts;
    }

    private ConnectionItem createItem(int left, int width) {
        ConnectionItem item = mock(ConnectionItem.class);
        stub(item.getOffsetLeft()).toReturn(left);
        stub(item.getWidth()).toReturn(width);
        return item;
    }
}
