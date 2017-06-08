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
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.dom.drag.NativeDragDataObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class DragDataObjectFromEventExtractorJUnitTest {

    private DragDataObjectFromEventExtractor dataObjectFromEventExtractor;
    private OverlayTypesParser overlayTypesParser;

    @Before
    public void setUp() throws Exception {
        overlayTypesParser = Mockito.mock(OverlayTypesParser.class);
        dataObjectFromEventExtractor = new DragDataObjectFromEventExtractor(overlayTypesParser);
    }

    @Test
    public void shouldExtractObjectWhenJsonIsValid() throws Exception {
        DropEvent dropEvent = Mockito.mock(DropEvent.class);

        String json = "some json";
        when(dropEvent.getData("json")).thenReturn(json);

        when(overlayTypesParser.isValidJSON(json)).thenReturn(true);

        NativeDragDataObject nativeDragData = Mockito.mock(NativeDragDataObject.class);
        when(overlayTypesParser.get(json)).thenReturn(nativeDragData);

        Optional<DragDataObject> extracted = dataObjectFromEventExtractor.extractDroppedObjectFromEvent(dropEvent);

        verify(dropEvent).stopPropagation();
        verify(dropEvent).preventDefault();
        assertThat(extracted.get()).isEqualTo(nativeDragData);
    }

    @Test
    public void shouldReturnAbsentWhenJsonIsNotValid() throws Exception {
        DropEvent dropEvent = Mockito.mock(DropEvent.class);

        String json = "some json";
        when(dropEvent.getData("json")).thenReturn(json);

        when(overlayTypesParser.isValidJSON(json)).thenReturn(false);

        NativeDragDataObject nativeDragData = Mockito.mock(NativeDragDataObject.class);
        when(overlayTypesParser.get(json)).thenReturn(nativeDragData);

        Optional<DragDataObject> extracted = dataObjectFromEventExtractor.extractDroppedObjectFromEvent(dropEvent);

        verify(dropEvent).stopPropagation();
        verify(dropEvent).preventDefault();
        assertThat(extracted.isPresent()).isEqualTo(false);
    }

}
