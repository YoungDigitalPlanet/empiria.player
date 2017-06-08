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

package eu.ydp.empiria.player.client.module.feedback.text.blend;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.multiview.touch.TouchReservationCommand;
import eu.ydp.empiria.player.client.module.feedback.FeedbackStyleNameConstants;
import eu.ydp.empiria.player.client.module.feedback.text.TextFeedback;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class FeedbackBlendTest {

    private FeedbackBlend testObj;
    @Mock
    private FeedbackBlendView view;
    @Mock
    private FeedbackStyleNameConstants styleNameConstants;
    @Mock
    private RootPanelDelegate rootPanelDelegate;
    @Mock
    private RootPanel rootPanel;
    @Mock
    private UserInteractionHandlerFactory userInteractionHandlerFactory;
    @Mock
    private TextFeedback textFeedback;
    @Mock
    private TouchReservationCommand touchReservationCommand;
    @Mock
    private EventHandlerProxy eventHandlerProxy;
    @Captor
    private ArgumentCaptor<Command> commandCaptor;

    private String blendHidden ="blendHidden";

    @Before
    public void init(){
        when(userInteractionHandlerFactory.createUserTouchStartHandler(isA(Command.class))).thenReturn(eventHandlerProxy);
        when(styleNameConstants.QP_FEEDBACK_BLEND_HIDDEN()).thenReturn(blendHidden);
        when(rootPanelDelegate.getRootPanel()).thenReturn(rootPanel);
        Widget viewWidget = mock(Widget.class);
        when(view.asWidget()).thenReturn(viewWidget);

        testObj = new FeedbackBlend(view, styleNameConstants, rootPanelDelegate, userInteractionHandlerFactory, touchReservationCommand);
        verify(userInteractionHandlerFactory).applyUserClickHandler(commandCaptor.capture(), eq(viewWidget));
    }

    @Test
    public void shouldRemoveStyle_onShow(){
        // when
        testObj.show(textFeedback);

        // then
        verify(view).removeStyleName(blendHidden);
    }

    @Test
    public void shouldHideFeedback_onClick_whenShown(){
        // given
        NativeEvent nativeEvent = mock(NativeEvent.class);
        Command value = commandCaptor.getValue();
        testObj.show(textFeedback);

        // when
        value.execute(nativeEvent);

        // then
        verify(textFeedback).hideFeedback();
    }

    @Test
    public void shouldAddStyle_onHide() {
        // when
        testObj.hide();

        // then
        verify(view).addStyleName(blendHidden);
    }

    @Test
    public void shouldNotHideFeedback_onClick_whenNotShown(){
        // given
        NativeEvent nativeEvent = mock(NativeEvent.class);
        Command value = commandCaptor.getValue();

        // when
        value.execute(nativeEvent);

        // then
        verify(textFeedback, never()).hideFeedback();
    }

    @Test
    public void shouldHideBlend_onClick(){
        // given
        NativeEvent nativeEvent = mock(NativeEvent.class);
        Command value = commandCaptor.getValue();

        // when
        value.execute(nativeEvent);

        // then
        verify(view).addStyleName(blendHidden);
    }
}