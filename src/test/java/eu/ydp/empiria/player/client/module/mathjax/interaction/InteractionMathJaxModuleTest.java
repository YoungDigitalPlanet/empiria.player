package eu.ydp.empiria.player.client.module.mathjax.interaction;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.MathJaxModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxPresenter;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxView;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class InteractionMathJaxModuleTest {

    private InteractionMathJaxModule testObj;
    @Mock
    private MathJaxPresenter presenter;
    @Mock
    private BodyGeneratorSocket bodyGenerator;
    @Mock
    private ModuleSocket moduleSocket;
    @Mock
    private RootPanel rootPanel;
    @Mock
    private PageScopeFactory pageScopeFactory;
    @Mock
    private EventsBus eventsBus;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Element element;

    @Before
    public void init() {
        MathJaxModuleFactory factory = mock(MathJaxModuleFactory.class);
        MathJaxView view = mock(MathJaxView.class);
        when(factory.getMathJaxPresenter(view)).thenReturn(presenter);

        RootPanelDelegate rootPanelDelegate = mock(RootPanelDelegate.class);
        when(rootPanelDelegate.getRootPanel()).thenReturn(rootPanel);

        testObj = new InteractionMathJaxModule(factory, view, rootPanelDelegate, eventsBus, pageScopeFactory);
    }


    @Test
    public void shouldInitPresenter_andGenerateGaps() {
        // given;
        String script = "script";

        NodeList gaps = mock(NodeList.class);
        when(gaps.getLength()).thenReturn(1);

        Node gap = mock(Node.class);
        when(gaps.item(0)).thenReturn(gap);

        when(element.getElementsByTagName("gap")).thenReturn(gaps);
        when(element.getChildNodes().toString()).thenReturn(script);

        // when
        testObj.initModule(element, moduleSocket, bodyGenerator);

        // then
        verify(rootPanel).add(isA(FlowPanel.class));
        verify(bodyGenerator).processNode(eq(gap), isA(FlowPanel.class));
        verify(presenter).setMmlScript(script);
    }

    @Test
    public void rerenderModule_onEvent() {
        //given
        String script = "script";
        PlayerEvent event = mock(PlayerEvent.class);
        when(event.getType()).thenReturn(PlayerEventTypes.SOURCE_LIST_CLIENTS_SET_SIZE_COMPLETED);
        String elementId = "id";
        when(element.getAttribute("id")).thenReturn(elementId);
        when(element.getChildNodes().toString()).thenReturn(script);

        //when
        testObj.initModule(element, moduleSocket, bodyGenerator);
        testObj.markToRerender();
        testObj.onPlayerEvent(event);

        //then
        verify(presenter).rerenderMathElement(elementId);
    }

    @Test
    public void shouldClearFeedbacksFromMmlScript() {
        String script = "<script><gap>" +
                "<feedbacks><script></script></feedbacks>" +
                "</gap></script>";
        String cleanScript = "<script><gap>" +
                "</gap></script>";

        NodeList gaps = mock(NodeList.class);
        when(gaps.getLength()).thenReturn(1);

        Node gap = mock(Node.class);
        when(gaps.item(0)).thenReturn(gap);

        when(element.getElementsByTagName("gap")).thenReturn(gaps);
        when(element.getChildNodes().toString()).thenReturn(script);

        // when
        testObj.initModule(element, moduleSocket, bodyGenerator);

        // then
        verify(presenter).setMmlScript(cleanScript);
    }
}
