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

package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem.Column;

import java.util.*;

public class ConnectionItems {

    @Inject
    private ConnectionModuleFactory connectionModuleFactory;

    private final Set<ConnectionItem> leftColumnItems = new HashSet<ConnectionItem>();
    private final Set<ConnectionItem> rightColumnItems = new HashSet<ConnectionItem>();

    private final Map<String, ConnectionItem> items = new HashMap<String, ConnectionItem>();

    private final InlineBodyGeneratorSocket bodyGenerator;

    @Inject
    public ConnectionItems(@Assisted InlineBodyGeneratorSocket bodyGenerator) {
        this.bodyGenerator = bodyGenerator;
    }

    private void addItem(ConnectionItem item, PairChoiceBean bean) {
        items.put(bean.getIdentifier(), item);
    }

    public ConnectionItem addItemToRightColumn(PairChoiceBean choice) {
        ConnectionItem item = connectionModuleFactory.getConnectionItem(choice, bodyGenerator, Column.RIGHT);
        rightColumnItems.add(item);
        addItem(item, choice);
        return item;
    }

    public ConnectionItem addItemToLeftColumn(PairChoiceBean choice) {
        ConnectionItem item = connectionModuleFactory.getConnectionItem(choice, bodyGenerator, Column.LEFT);
        leftColumnItems.add(item);
        addItem(item, choice);
        return item;
    }

    public Set<ConnectionItem> getConnectionItems(ConnectionItem selectedItem) {
        return rightColumnItems.contains(selectedItem) ? leftColumnItems : rightColumnItems;
    }

    public void resetAllItems() {
        for (ConnectionItem item : items.values()) {
            item.reset();
        }
    }

    public boolean isIdentifiersCorrect(String... identifiers) {
        for (String identifire : identifiers) {
            if (!items.containsKey(identifire)) {
                return false; // NOPMD
            }
        }
        return true;
    }

    public ConnectionItem getConnectionItem(String identifire) {
        return items.get(identifire);
    }

    public Collection<ConnectionItem> getAllItems() {
        return Collections.unmodifiableCollection(items.values());
    }

    public Collection<ConnectionItem> getLeftItems() {
        return Collections.unmodifiableCollection(leftColumnItems);
    }

    public Collection<ConnectionItem> getRightItems() {
        return Collections.unmodifiableCollection(rightColumnItems);
    }

}
