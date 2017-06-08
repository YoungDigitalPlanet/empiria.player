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

package eu.ydp.empiria.player.client.module.draggap.view;

import com.google.common.base.Optional;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.dom.drag.NativeDragDataObject;

public class DragDataObjectFromEventExtractor {

    private final OverlayTypesParser overlayTypesParser;

    @Inject
    public DragDataObjectFromEventExtractor(OverlayTypesParser overlayTypesParser) {
        this.overlayTypesParser = overlayTypesParser;
    }

    public Optional<DragDataObject> extractDroppedObjectFromEvent(DropEvent dropEvent) {
        String jsonObject = dropEvent.getData("json");
        Optional<DragDataObject> dragData;
        if (overlayTypesParser.isValidJSON(jsonObject)) {
            NativeDragDataObject nativeDragData = overlayTypesParser.get(jsonObject);
            DragDataObject dragDataObject = nativeDragData;
            dragData = Optional.fromNullable(dragDataObject);
        } else {
            dragData = Optional.absent();
        }
        dropEvent.stopPropagation();
        dropEvent.preventDefault();

        return dragData;
    }
}
