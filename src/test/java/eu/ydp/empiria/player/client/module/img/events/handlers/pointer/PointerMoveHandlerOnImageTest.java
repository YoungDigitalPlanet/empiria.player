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

package eu.ydp.empiria.player.client.module.img.events.handlers.pointer;

import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerMoveEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class PointerMoveHandlerOnImageTest {

    @InjectMocks
    private PointerMoveHandlerOnImage testObj;
    @Mock
    private PointerEventsCoordinates pointerEventsCoordinates;
    @Mock
    private TouchOnImageMoveHandler touchOnImageMoveHandler;
    @Mock
    private TouchOnImageEvent touchOnImageEvent;
    @Mock
    private PointerMoveEvent event;

    @Test
    public void shouldRunOnStart_ifIsTouchEvent() {
        // given
        when(event.isTouchEvent()).thenReturn(true);

        when(pointerEventsCoordinates.getTouchOnImageEvent()).thenReturn(touchOnImageEvent);

        // when
        testObj.onPointerMove(event);

        // then
        verify(event).preventDefault();
        verify(pointerEventsCoordinates).addEvent(event);
        verify(touchOnImageMoveHandler).onMove(touchOnImageEvent);
    }

    @Test
    public void shouldNotRunOnStart_ifIsNotTouchEvent() {
        // given
        when(event.isTouchEvent()).thenReturn(false);

        when(pointerEventsCoordinates.getTouchOnImageEvent()).thenReturn(touchOnImageEvent);

        // when
        testObj.onPointerMove(event);

        // then
        verify(event, never()).preventDefault();
        verify(pointerEventsCoordinates, never()).addEvent(event);
        verify(touchOnImageMoveHandler, never()).onMove(touchOnImageEvent);
    }
}
