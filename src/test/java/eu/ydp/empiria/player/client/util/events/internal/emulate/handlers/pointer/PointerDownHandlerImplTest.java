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

package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.pointer;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnStartHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class PointerDownHandlerImplTest {

    private PointerDownHandlerImpl testObj;

    @Mock
    private TouchOnStartHandler touchOnStartHandler;

    @Mock
    private PointerDownEvent pointerDownEvent;

    @Mock
    private NativeEvent nativeEvent;

    @Mock
    private PointerEventsCoordinates pointerEventsCoordinates;

    @Before
    public void setUp() {
        testObj = new PointerDownHandlerImpl(touchOnStartHandler, pointerEventsCoordinates);
    }

    @Test
    public void shouldCallOnStart() {
        // given
        when(pointerDownEvent.getNativeEvent()).thenReturn(nativeEvent);
        when(pointerDownEvent.isTouchEvent()).thenReturn(true);

        // when
        testObj.onPointerDown(pointerDownEvent);

        // then
        verify(pointerEventsCoordinates).addEvent(pointerDownEvent);
        verify(touchOnStartHandler).onStart(nativeEvent);
    }

    @Test
    public void shouldntCallOnStart() {
        // given
        when(pointerDownEvent.getNativeEvent()).thenReturn(nativeEvent);
        when(pointerDownEvent.isTouchEvent()).thenReturn(false);

        // when
        testObj.onPointerDown(pointerDownEvent);

        // then
        verify(pointerEventsCoordinates, never()).addEvent(pointerDownEvent);
        verify(touchOnStartHandler, never()).onStart(nativeEvent);
    }
}
