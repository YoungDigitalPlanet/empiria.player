package eu.ydp.empiria.player.client.module.slideshow.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxNative;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideNarrationBean;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideTitleBean;
import eu.ydp.empiria.player.client.module.slideshow.structure.SourceBean;
import eu.ydp.empiria.player.client.module.slideshow.view.slide.SlideView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class SlidePresenterTest {

    @InjectMocks
    private SlidePresenter testObj;
    @Mock
    private SlideView view;
    @Mock
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;

    @Mock
    private Element titleElement;
    @Mock
    private XMLContent titleXmlContent;
    @Mock
    private Widget titleView;

    @Mock
    private Element narrationElement;
    @Mock
    private XMLContent narrationXmlContent;
    @Mock
    private Widget narrationView;
    @Mock
    private MathJaxNative mathJaxNative;

    private final String src = "src";
    private SlideBean slide;

    @Before
    public void init() {
        slide = new SlideBean();
        SourceBean sourceBean = new SourceBean();
        sourceBean.setSrc(src);
        slide.setSource(sourceBean);
        testObj.setInlineBodyGenerator(inlineBodyGeneratorSocket);
    }

    @Test
    public void shouldReplaceSlideNarrationAndImageSource() {
        // given
        SlideNarrationBean slideNarration = new SlideNarrationBean();
        when(narrationXmlContent.getValue()).thenReturn(narrationElement);
        slideNarration.setNarrationValue(narrationXmlContent);

        when(inlineBodyGeneratorSocket.generateInlineBody(narrationElement)).thenReturn(narrationView);

        slide.setNarration(slideNarration);

        // when
        testObj.replaceViewData(slide);

        // then
        verifyClearText();
        verify(view).setNarration(narrationView);
        verify(view).setImage(src);
        verify(view, never()).setSlideTitle(any(Widget.class));
    }

    @Test
    public void shouldReplaceOnlySlideTitle() {
        // given
        SlideTitleBean slideTitle = new SlideTitleBean();
        when(titleXmlContent.getValue()).thenReturn(titleElement);
        slideTitle.setTitleValue(titleXmlContent);

        when(inlineBodyGeneratorSocket.generateInlineBody(titleElement)).thenReturn(titleView);

        SourceBean sourceBean = new SourceBean();
        sourceBean.setSrc("");
        slide.setSource(sourceBean);
        slide.setSlideTitle(slideTitle);

        // when
        testObj.replaceViewData(slide);

        // then
        verifyClearText();
        verify(view).setSlideTitle(titleView);
        verify(view, never()).setImage(src);
        verify(view, never()).setNarration(any(Widget.class));
    }

    @Test
    public void shouldReplaceImageSource() {
        // when
        testObj.replaceViewData(slide);

        // then
        verifyClearText();
        verify(view).setImage(src);
        verify(view, never()).setNarration(any(Widget.class));
        verify(view, never()).setNarration(any(Widget.class));
    }

    @Test
    public void shouldRenderMathAfterReplaceViewData() {
        // when
        testObj.replaceViewData(slide);

        // then
        verify(mathJaxNative, times(1)).renderMath();
    }

    private void verifyClearText() {
        verify(view).clearNarration();
        verify(view).clearTitle();
    }
}
