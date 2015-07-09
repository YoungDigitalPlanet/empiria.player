package eu.ydp.empiria.player.client.module.mathjax.interaction;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.MathJaxModuleFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxPresenter;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxView;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

	@Before
	public void init() {
		MathJaxModuleFactory factory = mock(MathJaxModuleFactory.class);
		MathJaxView view = mock(MathJaxView.class);
		when(factory.getMathJaxPresenter(view)).thenReturn(presenter);

		RootPanelDelegate rootPanelDelegate = mock(RootPanelDelegate.class);
		when(rootPanelDelegate.getRootPanel()).thenReturn(rootPanel);

		testObj = new InteractionMathJaxModule(factory, view, rootPanelDelegate);
	}

	@Test
	public void shouldInitPresenter_andGenerateGaps() {
		// given;
		String script = "script";

		NodeList gaps = mock(NodeList.class);
		when(gaps.getLength()).thenReturn(1);

		Node gap = mock(Node.class);
		when(gaps.item(0)).thenReturn(gap);

		Element element = mock(Element.class, RETURNS_DEEP_STUBS);
		when(element.getElementsByTagName("gap")).thenReturn(gaps);
		when(element.getChildNodes().toString()).thenReturn(script);

		// when
		testObj.initModule(element, moduleSocket, bodyGenerator);

		// then
		verify(rootPanel).add(isA(FlowPanel.class));
		verify(bodyGenerator).processNode(eq(gap), isA(FlowPanel.class));
		verify(presenter).setMmlScript(script);
	}
}