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

package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.gin.factory.TouchHandlerFactory;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerUpEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.pointer.PointerDownHandlerImpl;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.pointer.PointerMoveHandlerImpl;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.pointer.PointerUpHandlerImpl;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnEndHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnMoveHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnStartHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class PointerHandlersInitializerTest {

    @InjectMocks
    private PointerHandlersInitializer testObj;
    @Mock
    private TouchHandlerFactory touchHandlerFactory;
    @Mock
    private Widget listenOn;

    @Test
    public void shouldAddTouchMoveHandler() {
        // given
        TouchOnMoveHandler touchOnMoveHandler = mock(TouchOnMoveHandler.class);
        PointerMoveHandlerImpl pointerMoveHandlerImpl = mock(PointerMoveHandlerImpl.class);
        when(touchHandlerFactory.createPointerMoveHandler(touchOnMoveHandler)).thenReturn(pointerMoveHandlerImpl);

        // when
        testObj.addTouchMoveHandler(touchOnMoveHandler, listenOn);

        // then
        verify(listenOn).addDomHandler(pointerMoveHandlerImpl, PointerMoveEvent.getType());
    }

    @Test
    public void shouldAddTouchStartHandler() {
        // given
        TouchOnStartHandler touchOnStartHandler = mock(TouchOnStartHandler.class);
        PointerDownHandlerImpl pointerDownHandlerImpl = mock(PointerDownHandlerImpl.class);
        when(touchHandlerFactory.createPointerDownHandler(touchOnStartHandler)).thenReturn(pointerDownHandlerImpl);

        // when
        testObj.addTouchStartHandler(touchOnStartHandler, listenOn);

        // then
        verify(listenOn).addDomHandler(pointerDownHandlerImpl, PointerDownEvent.getType());
    }

    @Test
    public void shouldAddTouchEndHandler() {
        // given
        TouchOnEndHandler touchOnEndHandler = mock(TouchOnEndHandler.class);
        PointerUpHandlerImpl pointerUpHandlerImpl = mock(PointerUpHandlerImpl.class);
        when(touchHandlerFactory.createPointerUpHandler(touchOnEndHandler)).thenReturn(pointerUpHandlerImpl);

        // when
        testObj.addTouchEndHandler(touchOnEndHandler, listenOn);

        // then
        verify(listenOn).addDomHandler(pointerUpHandlerImpl, PointerUpEvent.getType());
    }
}
