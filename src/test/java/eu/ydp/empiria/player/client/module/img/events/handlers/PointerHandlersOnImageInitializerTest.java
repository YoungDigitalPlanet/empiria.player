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

package eu.ydp.empiria.player.client.module.img.events.handlers;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.gin.factory.TouchHandlerFactory;
import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.module.img.events.handlers.pointer.PointerDownHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.pointer.PointerMoveHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.pointer.PointerUpHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerUpEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class PointerHandlersOnImageInitializerTest {

    @InjectMocks
    private PointerHandlersOnImageInitializer testObj;
    @Mock
    private PointerEventsCoordinates pointerEventsCoordinates;
    @Mock
    private Widget listenOn;
    @Mock
    private TouchHandlerFactory touchHandlerFactory;

    @Test
    public void shouldAddPointerMoveHandlerOnImage() {
        // given
        TouchOnImageMoveHandler touchOnImageMoveHandler = mock(TouchOnImageMoveHandler.class);
        PointerMoveHandlerOnImage pointerMoveHandlerOnImage = mock(PointerMoveHandlerOnImage.class);
        when(touchHandlerFactory.createPointerMoveHandlerOnImage(touchOnImageMoveHandler)).thenReturn(pointerMoveHandlerOnImage);

        // when
        testObj.addTouchOnImageMoveHandler(touchOnImageMoveHandler, listenOn);

        // then
        verify(listenOn).addDomHandler(pointerMoveHandlerOnImage, PointerMoveEvent.getType());
    }

    @Test
    public void shouldAddPointerDownHandlerOnImage() {
        // given
        TouchOnImageStartHandler touchOnImageStartHandler = mock(TouchOnImageStartHandler.class);
        PointerDownHandlerOnImage pointerDownHandlerOnImage = mock(PointerDownHandlerOnImage.class);
        when(touchHandlerFactory.createPointerDownHandlerOnImage(touchOnImageStartHandler)).thenReturn(pointerDownHandlerOnImage);

        // when
        testObj.addTouchOnImageStartHandler(touchOnImageStartHandler, listenOn);

        // then
        verify(listenOn).addDomHandler(pointerDownHandlerOnImage, PointerDownEvent.getType());
    }

    @Test
    public void shouldAddPointerUpHandlerOnImage() {
        // given
        TouchOnImageEndHandler touchOnImageEndHandler = mock(TouchOnImageEndHandler.class);
        PointerUpHandlerOnImage pointerUpHandlerOnImage = mock(PointerUpHandlerOnImage.class);
        when(touchHandlerFactory.createPointerUpHandlerOnImage(touchOnImageEndHandler)).thenReturn(pointerUpHandlerOnImage);

        // when
        testObj.addTouchOnImageEndHandler(touchOnImageEndHandler, listenOn);

        // then
        verify(listenOn).addDomHandler(pointerUpHandlerOnImage, PointerUpEvent.getType());
    }
}
