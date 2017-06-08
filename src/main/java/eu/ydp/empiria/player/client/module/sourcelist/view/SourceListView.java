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

package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

public interface SourceListView extends IsWidget, LockUnlockDragDrop {
    void createAndBindUi();

    void createItem(SourcelistItemValue itemContent, InlineBodyGeneratorSocket inlineBodyGeneratorSocket);

    void hideItem(String itemId);

    void showItem(String itemId);

    SourcelistItemValue getItemValue(String itemId);

    void setSourceListPresenter(SourceListPresenter sourceListPresenter);

    void lockItemForDragDrop(String itemId);

    void unlockItemForDragDrop(String itemId);

    HasDimensions getMaxItemSize();
}
