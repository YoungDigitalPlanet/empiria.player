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

package eu.ydp.empiria.player.client.module.button;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.xml.client.Element;
import com.google.inject.Binder;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.controller.workmode.ModeStyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;
import org.junit.*;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings("PMD")
public class CheckButtonModuleTest extends AbstractTestBaseWithoutAutoInjectorInit {

    private static final String ENABLED_STYLE_NAME = "qp-markall-button";
    private static final String DISABLED_STYLE_NAME = "qp-markall-button-disabled";

    CheckButtonModule instance;
    EventsBus eventsBus;
    FlowRequestInvoker requestInvoker;
    private ModeStyleNameConstants styleNameConstants;
    private CustomPushButton button;
    protected ClickHandler handler;

    private static class CustomGuiceModule implements Module {
        @Override
        public void configure(Binder binder) {
            binder.bind(CustomPushButton.class)
                    .toInstance(mock(CustomPushButton.class));
        }
    }

    @Before
    public void before() {
        setUp(new Class<?>[]{CustomPushButton.class}, new Class<?>[]{}, new Class<?>[]{EventsBus.class}, new CustomGuiceModule());
        instance = spy(injector.getInstance(CheckButtonModule.class));
        eventsBus = injector.getInstance(EventsBus.class);
        styleNameConstants = injector.getInstance(ModeStyleNameConstants.class);
        requestInvoker = mock(FlowRequestInvoker.class);
        button = injector.getInstance(CustomPushButton.class);
        doAnswer(new Answer<ClickHandler>() {

            @Override
            public ClickHandler answer(InvocationOnMock invocation) throws Throwable {
                handler = (ClickHandler) invocation.getArguments()[0];
                return null;
            }
        }).when(button)
                .addClickHandler(any(ClickHandler.class));
    }

    @After
    public void after() {
        Mockito.verifyNoMoreInteractions(requestInvoker);
    }

    @BeforeClass
    public static void disarm() {
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void rearm() {
        GWTMockUtilities.restore();
    }

    @Test
    public void testInitModuleElement() {
        instance.initModule(mock(Element.class));
        verify(eventsBus).addHandler(Matchers.eq(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGING)), Matchers.eq(instance));
    }

    @Test
    public void testOnDeliveryEvent() {
        DeliveryEvent event = mock(DeliveryEvent.class);
        Map<String, Object> params = new HashMap<String, Object>();
        when(event.getParams()).thenReturn(params);

        when(event.getType()).thenReturn(DeliveryEventType.CHECK);
        instance.onDeliveryEvent(event);
        assertEquals(true, instance.isSelected);
        verify(instance).updateStyleName();

        when(event.getType()).thenReturn(DeliveryEventType.CONTINUE);
        instance.onDeliveryEvent(event);
        assertEquals(false, instance.isSelected);
        verify(instance, times(2)).updateStyleName();

        instance.isSelected = true;
        when(event.getType()).thenReturn(DeliveryEventType.SHOW_ANSWERS);
        instance.onDeliveryEvent(event);
        assertEquals(false, instance.isSelected);
        verify(instance, times(3)).updateStyleName();

        instance.isSelected = true;
        when(event.getType()).thenReturn(DeliveryEventType.RESET);
        instance.onDeliveryEvent(event);
        assertEquals(false, instance.isSelected);
        verify(instance, times(4)).updateStyleName();
    }

    @Test
    public void testInvokeRequest() {
        instance.setFlowRequestsInvoker(requestInvoker);
        doReturn(null).when(instance)
                .getCurrentGroupIdentifier();

        instance.isSelected = true;
        instance.invokeRequest();
        verify(requestInvoker).invokeRequest(Matchers.any(FlowRequest.Continue.class));

        Mockito.reset(requestInvoker);
        instance.isSelected = false;
        instance.invokeRequest();
        verify(requestInvoker).invokeRequest(Matchers.any(FlowRequest.ShowAnswers.class));
    }

    @Test
    public void testGetStyleName() {
        instance.isSelected = true;
        assertEquals("qp-continue-button", instance.getStyleName());
        instance.isSelected = false;
        assertEquals(ENABLED_STYLE_NAME, instance.getStyleName());
    }

    @Test
    public void testOnPlayerEvent() {
        PlayerEvent event = mock(PlayerEvent.class);
        when(event.getType()).thenReturn(PlayerEventTypes.PAGE_CHANGING);
        instance.setFlowRequestsInvoker(requestInvoker);
        doReturn(null).when(instance)
                .getCurrentGroupIdentifier();

        instance.isSelected = false;
        instance.onPlayerEvent(event);
        verify(requestInvoker, times(0)).invokeRequest(Matchers.any(FlowRequest.Continue.class));

        instance.isSelected = true;
        instance.onPlayerEvent(event);
        verify(requestInvoker, times(1)).invokeRequest(Matchers.any(FlowRequest.Continue.class));
    }

    @Test
    public void shouldNotInvokeActionInPreviewMode() {
        // given
        instance.initModule(mock(Element.class));
        doReturn(null).when(instance)
                .getCurrentGroupIdentifier();
        instance.setFlowRequestsInvoker(requestInvoker);
        instance.enablePreviewMode();

        // when
        handler.onClick(null);

        // then
        verifyZeroInteractions(requestInvoker);
    }

    @Test
    public void shouldNotOverwriteStyleInPreview() {
        // given
        final String inactiveStyleName = "STYLE_NAME";
        instance.initModule(mock(Element.class));
        doReturn(null).when(instance)
                .getCurrentGroupIdentifier();
        when(styleNameConstants.QP_MODULE_MODE_PREVIEW()).thenReturn(inactiveStyleName);
        instance.enablePreviewMode();

        // when
        instance.updateStyleName();

        // then
        InOrder inOrder = inOrder(button);
        inOrder.verify(button)
                .setStyleName(DISABLED_STYLE_NAME);
        inOrder.verify(button)
                .addStyleName(inactiveStyleName);
        inOrder.verify(button)
                .setStyleName(DISABLED_STYLE_NAME);
        inOrder.verify(button)
                .addStyleName(inactiveStyleName);
    }
}
