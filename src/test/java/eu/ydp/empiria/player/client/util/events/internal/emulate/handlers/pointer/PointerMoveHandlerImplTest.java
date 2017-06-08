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
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnMoveHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class PointerMoveHandlerImplTest {
    private PointerMoveHandlerImpl testObj;

    @Mock
    private TouchOnMoveHandler touchMoveHandler;

    @Mock
    private PointerMoveEvent pointerMoveEvent;

    @Mock
    private NativeEvent nativeEvent;

    @Before
    public void setUp() {
        testObj = new PointerMoveHandlerImpl(touchMoveHandler);
    }

    @Test
    public void shouldCallOnMove() {
        // given
        when(pointerMoveEvent.getNativeEvent()).thenReturn(nativeEvent);
        when(pointerMoveEvent.isTouchEvent()).thenReturn(true);

        // when
        testObj.onPointerMove(pointerMoveEvent);

        // then
        verify(touchMoveHandler).onMove(nativeEvent);
    }

    @Test
    public void shouldntCallOnMove() {
        // given
        when(pointerMoveEvent.getNativeEvent()).thenReturn(nativeEvent);
        when(pointerMoveEvent.isTouchEvent()).thenReturn(false);

        // when
        testObj.onPointerMove(pointerMoveEvent);

        // then
        verify(touchMoveHandler, never()).onMove(nativeEvent);
    }
}
