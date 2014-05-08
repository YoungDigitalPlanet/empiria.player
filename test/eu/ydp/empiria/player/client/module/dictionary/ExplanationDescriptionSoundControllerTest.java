package eu.ydp.empiria.player.client.module.dictionary;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.MocksCollector;
import eu.ydp.empiria.player.client.module.dictionary.external.MediaWrapperController;
import eu.ydp.empiria.player.client.module.dictionary.external.MimeSourceProvider;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.ExplanationDescriptionSoundController;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.event.EventImpl.Type;

@RunWith(MockitoJUnitRunner.class)
public class ExplanationDescriptionSoundControllerTest {

	private static final String FILE_NAME = "test.mp3";

	@InjectMocks
	private ExplanationDescriptionSoundController testObj;

	@Mock
	private ExplanationView explanationView;

	@Mock
	private MimeSourceProvider mimeSourceProvider;

	@Mock
	private EventsBus eventsBus;

	@Mock
	private MediaWrapperController mediaWrapperController;

	@Mock
	private MediaWrapperCreator mediaWrapperCreator;

	@Mock
	private Provider<CurrentPageScope> currentPageScropProvider;

	private MediaWrapper<Widget> mediaWrapper;

	private final MocksCollector mocksCollector = new MocksCollector();

	@Captor
	private ArgumentCaptor<PlayerEvent> playerEventCaptor;

	@Captor
	private ArgumentCaptor<MediaEvent> mediaEventCaptor;

	@Captor
	private ArgumentCaptor<CallbackRecevier<MediaWrapper<Widget>>> callbackReceiverCaptor;

	@Captor
	private ArgumentCaptor<AbstractMediaEventHandler> abstractMediaHandlerCaptor;

	@Captor
	private ArgumentCaptor<Type<MediaEventHandler, MediaEventTypes>> mediaTypeCaptor;

	@Before
	public void setUp() {
		mediaWrapper = mock(MediaWrapper.class);
	}

	@Test
	public void shouldPlayDescription() {
		CurrentPageScope currentPageScope = mock(CurrentPageScope.class);
		when(currentPageScropProvider.get()).thenReturn(currentPageScope);

		// when
		testObj.playOrStopDescriptionSound(FILE_NAME);

		// then
		verify(mediaWrapperCreator).create(eq(FILE_NAME), callbackReceiverCaptor.capture());
		callbackReceiverCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		verify(explanationView).setPlayingButtonStyle();
		verifySourceHandlerAdding(MediaEventTypes.ON_PAUSE, currentPageScope);
		verifySourceHandlerAdding(MediaEventTypes.ON_END, currentPageScope);
		verifySourceHandlerAdding(MediaEventTypes.ON_STOP, currentPageScope);
		verifySourceHandlerAdding(MediaEventTypes.ON_PLAY, currentPageScope);

		verify(mediaWrapperController).addMediaWrapperControls(mediaWrapper);
		verify(mediaWrapperController).play(mediaWrapper);
	}

	@Test
	public void shouldSetStopButtonCssWhenEventIsNotPlay() {
		// given
		MediaEvent mediaEvent = mock(MediaEvent.class);
		when(mediaEvent.getType()).thenReturn(MediaEventTypes.ON_PAUSE);

		CurrentPageScope currentPageScope = mock(CurrentPageScope.class);
		when(currentPageScropProvider.get()).thenReturn(currentPageScope);

		testObj.playOrStopDescriptionSound(FILE_NAME);
		verify(mediaWrapperCreator).create(eq(FILE_NAME), callbackReceiverCaptor.capture());
		callbackReceiverCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		verifySourceHandlerAdding(MediaEventTypes.ON_PLAY, currentPageScope);

		// when
		abstractMediaHandlerCaptor.getValue().onMediaEvent(mediaEvent);

		// then
		verify(explanationView).setStopButtonStyle();

	}

	@Test
	public void shouldDoNothignWhenEventIsPlay() {
		// given
		MediaEvent mediaEvent = mock(MediaEvent.class);
		when(mediaEvent.getType()).thenReturn(MediaEventTypes.ON_PLAY);

		CurrentPageScope currentPageScope = mock(CurrentPageScope.class);
		when(currentPageScropProvider.get()).thenReturn(currentPageScope);

		testObj.playOrStopDescriptionSound(FILE_NAME);
		verify(mediaWrapperCreator).create(eq(FILE_NAME), callbackReceiverCaptor.capture());
		callbackReceiverCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		verifySourceHandlerAdding(MediaEventTypes.ON_PLAY, currentPageScope);

		// when
		abstractMediaHandlerCaptor.getValue().onMediaEvent(mediaEvent);

		// then
		verify(explanationView).setPlayingButtonStyle();
		verifyNoMoreInteractions(explanationView);
	}

	@Test
	public void shouldNotPlayWhenFileNameIsNull() {
		// given
		String fileName = null;

		// when
		testObj.playOrStopDescriptionSound(fileName);

		// then
		verifyZeroInteractions(mocksCollector.getMocks());
	}

	@Test
	public void shouldNotPlayWhenFileNameIsEmptyString() {
		// given
		String fileName = "";

		// when
		testObj.playOrStopDescriptionSound(fileName);

		// then
		verifyZeroInteractions(mocksCollector.getMocks());
	}

	@Test
	public void shouldStopPlaying() {
		// given
		testObj.playOrStopDescriptionSound(FILE_NAME);

		// when
		verify(mediaWrapperCreator).create(eq(FILE_NAME), callbackReceiverCaptor.capture());
		callbackReceiverCaptor.getValue().setCallbackReturnObject(mediaWrapper);
		// testObj.stop();
		testObj.playOrStopDescriptionSound(FILE_NAME);

		// then
		verify(explanationView).setStopButtonStyle();
		verify(mediaWrapperController).stop(mediaWrapper);
	}

	private void verifySourceHandlerAdding(MediaEventTypes type, CurrentPageScope currentPageScope) {
		verify(eventsBus).addHandlerToSource(eq(MediaEvent.getType(type)), eq(mediaWrapper), abstractMediaHandlerCaptor.capture(), eq(currentPageScope));
	}
}
