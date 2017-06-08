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

import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.draggap.DragGapModuleFactory;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapView;
import eu.ydp.empiria.player.client.module.gap.DropZoneGuardian;
import eu.ydp.empiria.player.client.ui.drop.FlowPanelWithDropZone;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;
import eu.ydp.gwtutil.client.Wrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class DragDropControllerTest {

    private DragDropController dragDropController;
    private DragGapView dragGapView;
    private DragGapModuleFactory dragGapModuleFactory;
    private final String moduleIdentifier = "moduleId";
    private final Wrapper<String> itemIdWrapper = Wrapper.of("itemId");
    private DropZoneGuardian dropZoneGuardian;
    private SourceListConnectedDragHandler dragHandler;
    private SourceListConnectedDropHandler dropHandler;

    @Before
    public void setUp() throws Exception {
        dragGapView = Mockito.mock(DragGapView.class);
        dragGapModuleFactory = Mockito.mock(DragGapModuleFactory.class);
        dragHandler = Mockito.mock(SourceListConnectedDragHandler.class);
        dropHandler = Mockito.mock(SourceListConnectedDropHandler.class);
        dragDropController = new DragDropController(dragGapView, dragGapModuleFactory, dragHandler, dropHandler);
    }

    @Test
    public void shouldSetUpDragHandlers() throws Exception {
        dragDropController.initializeDrag(moduleIdentifier, itemIdWrapper);

        verify(dragGapView).setDragStartHandler(dragHandler);
        verify(dragGapView).setDragEndHandler(dragHandler);
        verify(dragHandler).initialize(moduleIdentifier, itemIdWrapper);
    }

    @Test
    public void shouldLockDropZone() throws Exception {
        mockDropEnabling();
        dragDropController.initializeDrop(moduleIdentifier);

        // when
        dragDropController.lockDropZone();

        // then
        verify(dropZoneGuardian).lockDropZone();
    }

    @Test
    public void shouldUnlockDropZone() throws Exception {
        mockDropEnabling();
        dragDropController.initializeDrop(moduleIdentifier);

        // when
        dragDropController.unlockDropZone();

        // then
        verify(dropZoneGuardian).unlockDropZone();
    }

    private void mockDropEnabling() {
        @SuppressWarnings("unchecked")
        DroppableObject<FlowPanelWithDropZone> droppable = Mockito.mock(DroppableObject.class);
        when(dragGapView.enableDropCapabilities()).thenReturn(droppable);

        Widget droppableWidget = Mockito.mock(Widget.class);
        when(droppable.getDroppableWidget()).thenReturn(droppableWidget);

        dropZoneGuardian = Mockito.mock(DropZoneGuardian.class);
        when(dragGapModuleFactory.createDropZoneGuardian(droppable, droppableWidget)).thenReturn(dropZoneGuardian);
    }
}
