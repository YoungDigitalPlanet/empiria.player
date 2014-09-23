package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.feedback.player.HideNativeMediaControlsManager;
import eu.ydp.empiria.player.client.module.dictionary.external.DictionaryMimeSourceProvider;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExplanationEntrySoundControllerTest {

	@InjectMocks
	private ExplanationEntrySoundController testObj;

	@Mock
	private DictionaryMimeSourceProvider dictionaryMimeSourceProvider;
	@Mock
	private EventsBus eventsBus;
	@Mock
	private MediaWrapperController mediaWrapperController;
	@Mock
	private MediaWrapper<Widget> mediaWrapper;
	@Mock
	private Entry entry;
	@Mock
	private HideNativeMediaControlsManager hideNativeMediaControlsManager;

	@Captor
	private ArgumentCaptor<PlayerEvent> playerEventCaptor;

	private CallbackRecevier<MediaWrapper<Widget>> callbackRecevier;

	private static final String FILE_NAME = "test.mp3";

	@Before
	public void setUp() {
		when(entry.getEntrySound()).thenReturn(FILE_NAME);
	}

	@Test
	public void shouldFireCreateMediaWrapperPlayerEvent() {
		// given
		Map<String, String> sourcesWithTypes = Maps.newHashMap();
		when(dictionaryMimeSourceProvider.getSourcesWithTypes(FILE_NAME)).thenReturn(sourcesWithTypes);

		// when
		testObj.playEntrySound(entry);

		// then
		verify(eventsBus).fireEvent(playerEventCaptor.capture());
		PlayerEvent resultEvent = playerEventCaptor.getValue();
		assertEquals(PlayerEventTypes.CREATE_MEDIA_WRAPPER, resultEvent.getType());
	}

	@Test
	public void shouldPlayMediaWrapperOnCallback() {
		// given
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				PlayerEvent plEvent = (PlayerEvent) invocation.getArguments()[0];
				callbackRecevier = (CallbackRecevier<MediaWrapper<Widget>>) plEvent.getSource();
				return null;
			}
		}).when(eventsBus).fireEvent(any(PlayerEvent.class));

		testObj.playEntrySound(entry);
		callbackRecevier.setCallbackReturnObject(mediaWrapper);

		// then
		InOrder inOrder = inOrder(hideNativeMediaControlsManager, mediaWrapperController);
		inOrder.verify(hideNativeMediaControlsManager).addToDocumentAndHideControls(mediaWrapper);
		inOrder.verify(mediaWrapperController).stopAndPlay(mediaWrapper);
	}
}
