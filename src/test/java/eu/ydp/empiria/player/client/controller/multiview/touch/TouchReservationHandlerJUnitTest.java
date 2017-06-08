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

package eu.ydp.empiria.player.client.controller.multiview.touch;

import com.google.gwt.event.dom.client.DomEvent.Type;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class TouchReservationHandlerJUnitTest {

    private EventsBus eventsBus;
    private IsWidget isWidget;
    private final ArgumentCaptor<Type> typeCaptor = ArgumentCaptor.forClass(Type.class);
    private final HandlerRegistration handlerRegistration = mock(HandlerRegistration.class);

    @Before
    public void before() {
        isWidget = mock(IsWidget.class);
        Widget widget = mock(Widget.class);
        doReturn(handlerRegistration).when(widget)
                .addDomHandler(Matchers.any(TouchStartHandler.class), Matchers.any(Type.class));
        doReturn(widget).when(isWidget)
                .asWidget();

        eventsBus = mock(EventsBus.class);

    }

    @Test
    public void TouchReservationHandler() throws Exception {
        TouchReservationHandler instance = new TouchReservationHandler(isWidget, eventsBus);
        verify(isWidget.asWidget()).addDomHandler(Matchers.any(TouchStartHandler.class), typeCaptor.capture());
        assertTrue(typeCaptor.getValue()
                .equals(TouchStartEvent.getType()));

    }

    @Test
    public void removeHandler() throws Exception {
        TouchReservationHandler instance = new TouchReservationHandler(isWidget, eventsBus);
        instance.removeHandler();
        verify(handlerRegistration).removeHandler();
    }

}
