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

package eu.ydp.empiria.player.client.view.player.styles;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.data.ItemDataSourceCollectionManager;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;

@Singleton
public class CurrentItemStyleProvider {

    private final ItemDataSourceCollectionManager itemDataSourceCollectionManager;
    private final ItemStylesContainer itemStylesContainer;
    private final FlowDataSupplier flowData;

    @Inject
    public CurrentItemStyleProvider(FlowDataSupplier flowData, ItemDataSourceCollectionManager itemDataSourceCollectionManager, ItemStylesContainer itemStylesContainer) {
        this.flowData = flowData;
        this.itemDataSourceCollectionManager = itemDataSourceCollectionManager;
        this.itemStylesContainer = itemStylesContainer;
    }

    public Optional<String> getCurrentItemStyle() {
        String itemIdentifier = getCurrentItemIdentifier();
        return itemStylesContainer.getStyle(itemIdentifier);
    }

    private String getCurrentItemIdentifier() {
        int pageIndex = flowData.getCurrentPageIndex();
        return itemDataSourceCollectionManager.getItemIdentifier(pageIndex);
    }
}
