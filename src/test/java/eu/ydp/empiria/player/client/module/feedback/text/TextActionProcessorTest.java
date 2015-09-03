package eu.ydp.empiria.player.client.module.feedback.text;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.feedback.processor.TextActionProcessor;
import eu.ydp.empiria.player.client.module.feedback.text.blend.FeedbackBlend;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxNative;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(GwtMockitoTestRunner.class)
public class TextActionProcessorTest {

    TextActionProcessor testObj;
    @Mock
    private TextFeedback feedbackPresenter;
    @Mock
    private MathJaxNative mathJaxNative;
    @Mock
    private FeedbackBlend feedbackBlend;
    @Captor
    private ArgumentCaptor<ClickHandler> clickHandlerCaptor;

    @Before
    public void init() {
        testObj = new TextActionProcessor(feedbackPresenter, mathJaxNative, feedbackBlend);
    }

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
        verify(feedbackPresenter).hideFeedback();
        verify(feedbackBlend).hide();
    }

}
