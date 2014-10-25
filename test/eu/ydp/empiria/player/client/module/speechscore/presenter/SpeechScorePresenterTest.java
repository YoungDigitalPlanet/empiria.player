package eu.ydp.empiria.player.client.module.speechscore.presenter;

import eu.ydp.empiria.player.client.controller.assets.URLOpenService;
import eu.ydp.empiria.player.client.module.speechscore.view.SpeechScoreLinkView;
import eu.ydp.gwtutil.client.event.factory.Command;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpeechScorePresenterTest {

	@InjectMocks
	private SpeechScorePresenter testObj;

	@Mock
	private SpeechScoreLinkView view;
	@Mock
	private URLOpenService urlOpenService;
	@Mock
	private SpeechScoreProtocolProvider protocolProvider;

	@Captor
	private ArgumentCaptor<Command> commandCaptor;

	@Test
	public void shouldRunSpeechScoreWhenViewHandlerExecute() {
		//given
		when(protocolProvider.get()).thenReturn("protocol:");
		when(view.getUrl()).thenReturn("url");

		testObj.bindUi();
		verify(view).addHandler(commandCaptor.capture());

		//when
		commandCaptor.getValue().execute(null);

		//then
		verify(urlOpenService).open("protocol:url");
	}
}