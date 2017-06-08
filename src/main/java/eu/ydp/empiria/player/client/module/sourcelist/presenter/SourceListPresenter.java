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

package eu.ydp.empiria.player.client.module.sourcelist.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.sourcelist.SourceListLocking;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.events.internal.dragdrop.DragDropEventTypes;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

import java.util.List;

public interface SourceListPresenter extends IsWidget, SourceListLocking {
    void setBean(SourceListBean bean);

    void createAndBindUi(InlineBodyGeneratorSocket inlineBodyGeneratorSocket);

    void onDragEvent(DragDropEventTypes eventType, String itemId);

    void onDropEvent(String itemId, String sourceModuleId);

    void setModuleId(String moduleId);

    void useItem(String itemId);

    void restockItem(String itemId);

    void useAndRestockItems(List<String> itemsIds);

    DragDataObject getDragDataObject(String itemId);

    SourcelistItemValue getItemValue(String itemId);

    HasDimensions getMaxItemSize();

}
