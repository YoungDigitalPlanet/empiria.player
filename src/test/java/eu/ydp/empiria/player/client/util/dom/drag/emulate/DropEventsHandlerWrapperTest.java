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

package eu.ydp.empiria.player.client.util.dom.drag.emulate;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.web.bindery.event.shared.HandlerRegistration;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParserMock;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.events.OutDroppableEvent.OutDroppableEventHandler;
import gwtquery.plugins.droppable.client.events.OverDroppableEvent.OverDroppableEventHandler;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

@SuppressWarnings("PMD")
public class DropEventsHandlerWrapperTest {
    @BeforeClass
    public static void disarm() {
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void rearm() {
        GWTMockUtilities.restore();
    }

    DropEventsHandlerWrapper instance;
    DroppableWidget<?> droppableWidget;

    @Before
    public void before() {
        AbstractHTML5DragDropWrapper.parser = new OverlayTypesParserMock();
        droppableWidget = mock(DroppableWidget.class);
        instance = new DropEventsHandlerWrapper(droppableWidget, AbstractHTML5DragDropWrapper.parser);
    }

    @Test
    public void addDragEnterHandlerTest() {
        DragEnterHandler handler = mock(DragEnterHandler.class);
        instance.wrap(handler);
        verify(droppableWidget).addOverDroppableHandler(Matchers.any(OverDroppableEventHandler.class));
    }

    OverDroppableEventHandler droppableEventHandler;

    @Test
    public void fireDragEnterHandlerTest() {
        when(droppableWidget.addOverDroppableHandler(Matchers.any(OverDroppableEventHandler.class))).then(new Answer<HandlerRegistration>() {
            @Override
            public HandlerRegistration answer(InvocationOnMock invocation) throws Throwable {
                droppableEventHandler = (OverDroppableEventHandler) invocation.getArguments()[0];
                return null;
            }
        });
        DragEnterHandler handler = mock(DragEnterHandler.class);
        instance.wrap(handler);
        droppableEventHandler.onOverDroppable(null);
        verify(handler).onDragEnter(Matchers.any(DragEnterEvent.class));
    }

    @Test
    public void addDragLeaveHandlerTest() {
        DragLeaveHandler handler = mock(DragLeaveHandler.class);
        instance.wrap(handler);
        verify(droppableWidget).addOutDroppableHandler(Matchers.any(OutDroppableEventHandler.class));
    }

    OutDroppableEventHandler outDroppableEventHandler;

    @Test
    public void fireDragLeaveHandlerTest() {
        when(droppableWidget.addOutDroppableHandler(Matchers.any(OutDroppableEventHandler.class))).then(new Answer<HandlerRegistration>() {
            @Override
            public HandlerRegistration answer(InvocationOnMock invocation) throws Throwable {
                outDroppableEventHandler = (OutDroppableEventHandler) invocation.getArguments()[0];
                return null;
            }
        });
        DragLeaveHandler handler = mock(DragLeaveHandler.class);
        instance.wrap(handler);
        outDroppableEventHandler.onOutDroppable(null);
        verify(handler).onDragLeave(Matchers.any(DragLeaveEvent.class));
    }

    @Test
    public void addDragOverHandlerTest() {
        DragOverHandler handler = mock(DragOverHandler.class);
        instance.wrap(handler);
        verify(droppableWidget).addOverDroppableHandler(Matchers.any(OverDroppableEventHandler.class));
    }

    @Test
    public void fireDragOverHandlerTest() {
        when(droppableWidget.addOverDroppableHandler(Matchers.any(OverDroppableEventHandler.class))).then(new Answer<HandlerRegistration>() {
            @Override
            public HandlerRegistration answer(InvocationOnMock invocation) throws Throwable {
                droppableEventHandler = (OverDroppableEventHandler) invocation.getArguments()[0];
                return null;
            }
        });
        DragOverHandler handler = mock(DragOverHandler.class);
        instance.wrap(handler);
        droppableEventHandler.onOverDroppable(null);
        verify(handler).onDragOver(Matchers.any(DragOverEvent.class));
    }

    @Test
    public void addDropHandlerTest() {
        DropHandler handler = mock(DropHandler.class);
        instance.wrap(handler);
        verify(droppableWidget).addDropHandler(Matchers.any(DropEventHandler.class));
    }

    DropEventHandler dropEventHandler;

    @Test
    public void fireDropHandlerTest() {
        when(droppableWidget.addDropHandler(Matchers.any(DropEventHandler.class))).then(new Answer<HandlerRegistration>() {
            @Override
            public HandlerRegistration answer(InvocationOnMock invocation) throws Throwable {
                dropEventHandler = (DropEventHandler) invocation.getArguments()[0];
                return null;
            }
        });
        DropHandler handler = mock(DropHandler.class);
        instance.wrap(handler);
        dropEventHandler.onDrop(null);
        verify(handler).onDrop(Matchers.any(DropEvent.class));
    }
}
