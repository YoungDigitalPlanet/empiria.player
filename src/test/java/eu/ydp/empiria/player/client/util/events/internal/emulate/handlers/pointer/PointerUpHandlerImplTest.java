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
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerUpEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnEndHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class PointerUpHandlerImplTest {

    private PointerUpHandlerImpl testObj;

    @Mock
    private TouchOnEndHandler touchOnEndHandler;

    @Mock
    private PointerUpEvent pointerUpEvent;

    @Mock
    private NativeEvent nativeEvent;

    @Mock
    private PointerEventsCoordinates pointerEventsCoordinates;

    @Before
    public void setUp() {
        testObj = new PointerUpHandlerImpl(touchOnEndHandler, pointerEventsCoordinates);
    }

    @Test
    public void shouldCallOnEnd_andShouldRemoveEvent() {
        // given
        when(pointerUpEvent.getNativeEvent()).thenReturn(nativeEvent);
        when(pointerUpEvent.isTouchEvent()).thenReturn(true);

        // when
        testObj.onPointerUp(pointerUpEvent);

        // then
        verify(pointerEventsCoordinates).removeEvent(pointerUpEvent);
        verify(touchOnEndHandler).onEnd(nativeEvent);
    }

    @Test
    public void shouldNotCallOnEnd() {
        // given
        when(pointerUpEvent.getNativeEvent()).thenReturn(nativeEvent);
        when(pointerUpEvent.isTouchEvent()).thenReturn(false);

        // when
        testObj.onPointerUp(pointerUpEvent);

        // then
        verify(pointerEventsCoordinates, never()).removeEvent(pointerUpEvent);
        verify(touchOnEndHandler, never()).onEnd(nativeEvent);
    }
}
