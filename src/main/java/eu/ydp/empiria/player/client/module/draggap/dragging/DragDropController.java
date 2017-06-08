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

package eu.ydp.empiria.player.client.module.draggap.dragging;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.draggap.DragGapModuleFactory;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapView;
import eu.ydp.empiria.player.client.module.gap.DropZoneGuardian;
import eu.ydp.empiria.player.client.ui.drop.FlowPanelWithDropZone;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;
import eu.ydp.gwtutil.client.Wrapper;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class DragDropController {

    private final DragGapView dragGapView;
    private final DragGapModuleFactory dragGapModuleFactory;
    private DropZoneGuardian dropZoneGuardian;
    private final SourceListConnectedDragHandler dragHandler;
    private final SourceListConnectedDropHandler dropHandler;

    @Inject
    public DragDropController(@ModuleScoped DragGapView dragGapView, DragGapModuleFactory dragGapModuleFactory, SourceListConnectedDragHandler dragHandler,
                              SourceListConnectedDropHandler dropHandler) {
        this.dragGapView = dragGapView;
        this.dragGapModuleFactory = dragGapModuleFactory;
        this.dragHandler = dragHandler;
        this.dropHandler = dropHandler;
    }

    public void initializeDrop(String moduleIdentifier) {
        DroppableObject<FlowPanelWithDropZone> droppable = dragGapView.enableDropCapabilities();
        droppable.addDropHandler(dropHandler);
        dropZoneGuardian = dragGapModuleFactory.createDropZoneGuardian(droppable, droppable.getDroppableWidget());
    }

    public void initializeDrag(String moduleIdentifier, Wrapper<String> itemIdWrapper) {
        dragHandler.initialize(moduleIdentifier, itemIdWrapper);
        dragGapView.setDragStartHandler(dragHandler);
        dragGapView.setDragEndHandler(dragHandler);
    }

    public void lockDropZone() {
        dropZoneGuardian.lockDropZone();
    }

    public void unlockDropZone() {
        dropZoneGuardian.unlockDropZone();
    }
}
