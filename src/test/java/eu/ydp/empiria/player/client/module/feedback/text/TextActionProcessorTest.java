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

package eu.ydp.empiria.player.client.module.feedback.text;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.feedback.processor.TextActionProcessor;
import eu.ydp.empiria.player.client.module.feedback.text.blend.FeedbackBlend;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxNative;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(GwtMockitoTestRunner.class)
public class TextActionProcessorTest {

    @InjectMocks
    private TextActionProcessor testObj;
    @Mock
    private TextFeedback feedbackPresenter;
    @Mock
    private MathJaxNative mathJaxNative;
    @Mock
    private FeedbackBlend feedbackBlend;
    @Captor
    private ArgumentCaptor<ClickHandler> clickHandlerCaptor;

    @Test
    public void shouldShowFeedback() {
        //given
        Element element = mock(Element.class);
        testObj.initModule(element);
        verify(feedbackPresenter).addShowButtonClickHandler(clickHandlerCaptor.capture());
        ClickHandler clickHandler = clickHandlerCaptor.getValue();
        ClickEvent clickEvent = mock(ClickEvent.class);
        //when
        clickHandler.onClick(clickEvent);
        //then
        verify(feedbackPresenter).showFeedback();
        verify(feedbackBlend).show(feedbackPresenter);
    }

    @Test
    public void shouldHideFeedback() {
        //given
        Element element = mock(Element.class);
        testObj.initModule(element);
        verify(feedbackPresenter).addCloseButtonClickHandler(clickHandlerCaptor.capture());
        ClickHandler clickHandler = clickHandlerCaptor.getValue();
        ClickEvent clickEvent = mock(ClickEvent.class);

        //when
        clickHandler.onClick(clickEvent);

        //then
        verify(feedbackPresenter, times(2)).hideFeedback();
        verify(feedbackBlend, times(2)).hide();
    }
}
