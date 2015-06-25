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
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class InlineMathJaxModuleTest {

	@Mock
	private MathJaxPresenter presenter;

	private InlineMathJaxModule testObj;

	@Before
	public void init(){
		MathJaxView view = mock(MathJaxView.class);
		MathJaxModuleFactory factory = mock(MathJaxModuleFactory.class);
		when(factory.getMathJaxPresenter(view)).thenReturn(presenter);

		testObj = new InlineMathJaxModule(factory, view);
	}

	@Test
	public void shouldSetMathScriptOnPresenter(){
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