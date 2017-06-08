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

package eu.ydp.empiria.player.client.module.mathjax.inline;

import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.gin.factory.MathJaxModuleFactory;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxPresenter;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class InlineMathJaxModuleTest {

    @Mock
    private MathJaxPresenter presenter;

    private InlineMathJaxModule testObj;

    @Before
    public void init() {
        MathJaxView view = mock(MathJaxView.class);
        MathJaxModuleFactory factory = mock(MathJaxModuleFactory.class);
        when(factory.getMathJaxPresenter(view)).thenReturn(presenter);

        testObj = new InlineMathJaxModule(factory, view);
    }

    @Test
    public void shouldSetMathScriptOnPresenter() {
        // given
        String script = "script";
        Element element = mock(Element.class, RETURNS_DEEP_STUBS);
        when(element.getChildNodes().toString()).thenReturn(script);

        // when
        testObj.initModule(element);

        // then
        verify(presenter).setMmlScript(script);
    }
}
