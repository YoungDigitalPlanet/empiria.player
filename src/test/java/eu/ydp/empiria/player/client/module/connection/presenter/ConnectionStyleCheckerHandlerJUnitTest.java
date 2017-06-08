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

package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.connection.exception.CssStyleException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class ConnectionStyleCheckerHandlerJUnitTest {

    private IsWidget view;
    private ConnectionStyleChecker connectionStyleChecker;
    private ConnectionStyleCheckerHandler instance;
    private Element element;

    @Before
    public void before() {
        view = mock(IsWidget.class);
        Widget widget = cretateWidgetAndElementMock();
        doReturn(widget).when(view).asWidget();

        connectionStyleChecker = mock(ConnectionStyleChecker.class);
        instance = new ConnectionStyleCheckerHandler(view, connectionStyleChecker);
    }

    private Widget cretateWidgetAndElementMock() {
        Widget widget = mock(Widget.class);
        element = mock(Element.class);
        doReturn(element).when(widget).getElement();
        return widget;
    }

    @Test
    public void testOnAttachOrDetachCorrectStyles() {
        doNothing().when(connectionStyleChecker).areStylesCorrectThrowsExceptionWhenNot(Matchers.any(IsWidget.class));
        instance.onAttachOrDetach(null);
        verifyZeroInteractions(view);

    }

    @Test
    public void testOnAttachOrDetachNotCorrectStyles() {
        String message = "error";
        doThrow(new CssStyleException(message)).when(connectionStyleChecker).areStylesCorrectThrowsExceptionWhenNot(Matchers.any(IsWidget.class));
        instance.onAttachOrDetach(null);
        verify(element).setInnerText(Matchers.eq(message));
    }

}
