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

import com.google.common.base.Optional;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import eu.ydp.empiria.player.client.module.draggap.view.DragDataObjectFromEventExtractor;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;

public class SourceListViewDropHandler implements DropHandler {

    private final DragDataObjectFromEventExtractor objectFromEventExtractor;
    private final SourceListPresenter sourceListPresenter;

    public SourceListViewDropHandler(DragDataObjectFromEventExtractor objectFromEventExtractor, SourceListPresenter sourceListPresenter) {
        this.objectFromEventExtractor = objectFromEventExtractor;
        this.sourceListPresenter = sourceListPresenter;
    }

    @Override
    public void onDrop(DropEvent event) {
        Optional<DragDataObject> objectFromEvent = objectFromEventExtractor.extractDroppedObjectFromEvent(event);
        if (objectFromEvent.isPresent()) {
            DragDataObject dataObject = objectFromEvent.get();
            sourceListPresenter.onDropEvent(dataObject.getItemId(), dataObject.getSourceId());
        }
    }

}
