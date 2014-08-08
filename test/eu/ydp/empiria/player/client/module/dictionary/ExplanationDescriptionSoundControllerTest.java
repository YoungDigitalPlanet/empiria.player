package eu.ydp.empiria.player.client.module.dictionary;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.MocksCollector;
import eu.ydp.empiria.player.client.controller.feedback.player.HideNativeMediaControlsManager;
import eu.ydp.empiria.player.client.module.dictionary.external.MediaWrapperController;
import eu.ydp.empiria.player.client.module.dictionary.external.MimeSourceProvider;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.ExplanationDescriptionSoundController;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

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

	@Mock
	private MediaWrapper<Widget> mediaWrapper;

	@Mock
	private Entry entryWithValidFileName;

	@Mock
	private HideNativeMediaControlsManager hideNativeMediaControlsManager;

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
		when(entryWithValidFileName.getEntryExampleSound()).thenReturn(FILE_NAME);
	}

	@Test
	public void shouldPlayDescription() {
		CurrentPageScope currentPageScope = mock(CurrentPageScope.class);
		when(currentPageScropProvider.get()).thenReturn(currentPageScope);

		// when
		testObj.playOrStopDescriptionSound(entryWithValidFileName);

		// then
		verify(mediaWrapperCreator).create(eq(FILE_NAME), callbackReceiverCaptor.capture());
		callbackReceiverCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		InOrder inOrder = inOrder(mocksCollector.getMocks());
		inOrder.verify(explanationView).setPlayingButtonStyle();
		verifyInOrderSourceHandlerAdding(inOrder, MediaEventTypes.ON_PAUSE, currentPageScope);
		verifyInOrderSourceHandlerAdding(inOrder, MediaEventTypes.ON_END, currentPageScope);
		verifyInOrderSourceHandlerAdding(inOrder, MediaEventTypes.ON_STOP, currentPageScope);
		verifyInOrderSourceHandlerAdding(inOrder, MediaEventTypes.ON_PLAY, currentPageScope);

		inOrder.verify(hideNativeMediaControlsManager).addToDocumentAndHideControls(mediaWrapper);
		inOrder.verify(mediaWrapperController).play(mediaWrapper);
	}

	@Test
	public void shouldSetStopButtonCssWhenEventIsNotPlay() {
		// given
		MediaEvent mediaEvent = mock(MediaEvent.class);
		when(mediaEvent.getType()).thenReturn(MediaEventTypes.ON_PAUSE);

		CurrentPageScope currentPageScope = mock(CurrentPageScope.class);
		when(currentPageScropProvider.get()).thenReturn(currentPageScope);

		testObj.playOrStopDescriptionSound(entryWithValidFileName);
		verify(mediaWrapperCreator).create(eq(FILE_NAME), callbackReceiverCaptor.capture());
		callbackReceiverCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		verifySourceHandlerAdding(MediaEventTypes.ON_PLAY, currentPageScope);

		// when
		abstractMediaHandlerCaptor.getValue().onMediaEvent(mediaEvent);

		// then
		verify(explanationView).setStopButtonStyle();

	}

	@Test
	public void shouldDoNothingWhenEventIsPlay() {
		// given
		MediaEvent mediaEvent = mock(MediaEvent.class);
		when(mediaEvent.getType()).thenReturn(MediaEventTypes.ON_PLAY);

		CurrentPageScope currentPageScope = mock(CurrentPageScope.class);
		when(currentPageScropProvider.get()).thenReturn(currentPageScope);

		testObj.playOrStopDescriptionSound(entryWithValidFileName);
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
		Entry entry = mock(Entry.class);

		// when
		testObj.playOrStopDescriptionSound(entry);

		// then
		verify(entry).getEntryExampleSound();
		verifyZeroInteractions(mocksCollector.getMocks());
	}

	@Test
	public void shouldNotPlayWhenFileNameIsEmptyString() {
		// given
		Entry entry = mock(Entry.class);
		when(entry.getEntryExampleSound()).thenReturn("");

		// when
		testObj.playOrStopDescriptionSound(entry);

		// then
		verify(entry).getEntryExampleSound();
		verifyZeroInteractions(mocksCollector.getMocks());
	}

	@Test
	public void shouldStopPlaying() {
		// given
		testObj.playOrStopDescriptionSound(entryWithValidFileName);

		// when
		verify(mediaWrapperCreator).create(eq(FILE_NAME), callbackReceiverCaptor.capture());
		callbackReceiverCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		testObj.playOrStopDescriptionSound(entryWithValidFileName);

		// then
		verify(explanationView).setStopButtonStyle();
		verify(mediaWrapperController).stop(mediaWrapper);
	}

	private void verifySourceHandlerAdding(MediaEventTypes type, CurrentPageScope currentPageScope) {
		verify(eventsBus).addHandlerToSource(eq(MediaEvent.getType(type)), eq(mediaWrapper), abstractMediaHandlerCaptor.capture(), eq(currentPageScope));
	}

	private void verifyInOrderSourceHandlerAdding(InOrder inOrder, MediaEventTypes type, CurrentPageScope currentPageScope) {
		inOrder.verify(eventsBus)
				.addHandlerToSource(eq(MediaEvent.getType(type)), eq(mediaWrapper), abstractMediaHandlerCaptor.capture(), eq(currentPageScope));
	}
}
