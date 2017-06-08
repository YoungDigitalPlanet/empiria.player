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

import com.google.common.base.Optional;
import com.google.gwt.event.dom.client.DropEvent;
import eu.ydp.empiria.player.client.module.draggap.SourceListManagerAdapter;
import eu.ydp.empiria.player.client.module.draggap.view.DragDataObjectFromEventExtractor;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SourceListConnectedDropHandlerTest {

    private SourceListConnectedDropHandler dropHandler;
    @Mock
    private DragDataObjectFromEventExtractor dragDataObjectFromEventExtractor;
    @Mock
    private SourceListManagerAdapter sourceListManagerAdapter;

    @Before
    public void setUp() throws Exception {
        dropHandler = new SourceListConnectedDropHandler(sourceListManagerAdapter, dragDataObjectFromEventExtractor);
    }

    @Test
    public void shouldExtractDataFromEventAndInformSourcelistManager() throws Exception {
        DropEvent event = Mockito.mock(DropEvent.class);

        DragDataObject dragDataObject = Mockito.mock(DragDataObject.class);
        Optional<DragDataObject> optionalDragData = Optional.fromNullable(dragDataObject);
        when(dragDataObjectFromEventExtractor.extractDroppedObjectFromEvent(event)).thenReturn(optionalDragData);

        String itemId = "itemId";
        when(dragDataObject.getItemId()).thenReturn(itemId);

        String sourceId = "sourceId";
        when(dragDataObject.getSourceId()).thenReturn(sourceId);

        dropHandler.onDrop(event);

        verify(sourceListManagerAdapter).dragEnd(itemId, sourceId);
    }
}
