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

package eu.ydp.empiria.player.client.module.mathjax.common;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class MathJaxPresenterTest {

    @InjectMocks
    private MathJaxPresenter testObj;
    @Mock
    private MathJaxNative mathJaxNative;
    @Mock
    private MathJaxView view;
    @Mock
    private Element scriptElement;

    @Before
    public void init() {
        Widget widget = mock(Widget.class);
        com.google.gwt.user.client.Element viewElement = mock(com.google.gwt.user.client.Element.class);
        when(view.asWidget()).thenReturn(widget);
        when(widget.getElement()).thenReturn(viewElement);
        when(viewElement.getFirstChildElement()).thenReturn(scriptElement);
    }

    @Test
    public void shouldSetUpView_andAddScriptElementToRender() {
        // given
        String script = "script";

        // when
        testObj.setMmlScript(script);

        // then
        verify(view).setMmlScript(script);
        verify(mathJaxNative).addElementToRender(scriptElement);
    }

}
