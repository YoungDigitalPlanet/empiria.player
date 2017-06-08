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

import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.draggap.SourceListManagerAdapter;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;
import eu.ydp.empiria.player.client.util.dom.drag.NativeDragDataObject;
import eu.ydp.gwtutil.client.Wrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class SourceListConnectedDragHandlerTest {

    private SourceListConnectedDragHandler dragHandler;
    private final String moduleIdentifier = "moduleId";
    private final Wrapper<String> itemIdWrapper = Wrapper.of("itemId");
    private SourceListManagerAdapter sourcelistManagerAdapter;
    private OverlayTypesParser overlayTypesParser;

    @Before
    public void setUp() throws Exception {
        sourcelistManagerAdapter = Mockito.mock(SourceListManagerAdapter.class);
        overlayTypesParser = Mockito.mock(OverlayTypesParser.class);
        dragHandler = new SourceListConnectedDragHandler(sourcelistManagerAdapter, overlayTypesParser);
        dragHandler.initialize(moduleIdentifier, itemIdWrapper);
    }

    @Test
    public void shouldInformSourcelistManagerOnDragEnd() throws Exception {
        DragEndEvent event = Mockito.mock(DragEndEvent.class);

        dragHandler.onDragEnd(event);

        verify(sourcelistManagerAdapter).dragFinished();
    }

    @Test
    public void shouldInformSourcelistManagerAndExtendEventDataOnDragStart() throws Exception {
        DragStartEvent event = Mockito.mock(DragStartEvent.class);

        NativeDragDataObject dragDataObject = Mockito.mock(NativeDragDataObject.class);
        when(overlayTypesParser.get()).thenReturn(dragDataObject);

        String jsonString = "jsonString";
        when(dragDataObject.toJSON()).thenReturn(jsonString);

        dragHandler.onDragStart(event);

        verify(sourcelistManagerAdapter).dragStart();

        InOrder inOrder = Mockito.inOrder(overlayTypesParser, dragDataObject);
        inOrder.verify(overlayTypesParser)
                .get();
        inOrder.verify(dragDataObject)
                .setItemId(itemIdWrapper.getInstance());
        inOrder.verify(dragDataObject)
                .setSourceId(moduleIdentifier);
    }
}
