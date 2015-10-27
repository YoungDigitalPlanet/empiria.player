package eu.ydp.empiria.player.client.module.mathjax.inline;

import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.gin.factory.MathJaxModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxPresenter;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxView;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class InlineMathJaxModuleTest {

    @Mock
    private MathJaxPresenter presenter;
    @Mock
    private PageScopeFactory pageScopeFactory;
    @Mock
    private EventsBus eventsBus;

    private InlineMathJaxModule testObj;

    @Before
    public void init() {
        MathJaxView view = mock(MathJaxView.class);
        MathJaxModuleFactory factory = mock(MathJaxModuleFactory.class);
        when(factory.getMathJaxPresenter(view)).thenReturn(presenter);

        testObj = new InlineMathJaxModule(factory, view, eventsBus, pageScopeFactory);
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

    @Test
    public void shouldTypesetOnEvent() {
        //given
        Element element = mock(Element.class, RETURNS_DEEP_STUBS);
        PlayerEvent event = mock(PlayerEvent.class);
        when(event.getType()).thenReturn(PlayerEventTypes.PICTURE_LABEL_RENDERED);

        //when
        testObj.initModule(element);
        testObj.onPlayerEvent(event);

        //then
        verify(presenter).typesetMathElement();
    }
}
