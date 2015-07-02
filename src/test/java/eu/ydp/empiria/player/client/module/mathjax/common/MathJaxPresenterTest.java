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