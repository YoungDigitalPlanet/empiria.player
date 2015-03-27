package eu.ydp.empiria.player.client.module.speechscore.presenter;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import eu.ydp.empiria.player.client.module.speechscore.view.SpeechScoreLinkView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SpeechScorePresenterTest {

	@InjectMocks
	private SpeechScorePresenter testObj;

	@Mock
	private SpeechScoreLinkView view;
	@Mock
	private SpeechScoreProtocolProvider protocolProvider;

	@Test
	public void shouldBuildLinkOnView() {
		// given
		final String LINK_TEXT = "Link text";

		Node node = mock(Node.class);
		when(node.getNodeValue()).thenReturn(LINK_TEXT);

		Element element = mock(Element.class);
		when(element.getFirstChild()).thenReturn(node);
		when(element.getAttribute("url")).thenReturn("url");

		when(protocolProvider.get()).thenReturn("protocol:");

		// when
		testObj.init(element);

		// then
		verify(view).buildLink(LINK_TEXT, "protocol:url");
	}
}