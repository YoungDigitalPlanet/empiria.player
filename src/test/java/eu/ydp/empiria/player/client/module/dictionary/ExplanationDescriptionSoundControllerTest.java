package eu.ydp.empiria.player.client.module.dictionary;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.MocksCollector;
import eu.ydp.empiria.player.client.module.dictionary.external.DictionaryMimeSourceProvider;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.DictionaryMediaWrapperCreator;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.ExplanationDescriptionSoundController;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;
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
	private DictionaryMimeSourceProvider mimeSourceProvider;

	@Mock
	private EventsBus eventsBus;

	@Mock
	private MediaWrapperController mediaWrapperController;

	@Mock
	private DictionaryMediaWrapperCreator mediaWrapperCreator;

	@Mock
	private Provider<CurrentPageScope> currentPageScropProvider;

	@Mock
	private MediaWrapper<Widget> mediaWrapper;

	@Mock
	private Entry entryWithValidFileName;

	private final MocksCollector mocksCollector = new MocksCollector();

	@Captor
	private ArgumentCaptor<PlayerEvent> playerEventCaptor;

	@Captor
	private ArgumentCaptor<MediaEvent> mediaEventCaptor;

	@Captor
	private ArgumentCaptor<CallbackReceiver<MediaWrapper<Widget>>> callbackReceiverCaptor;

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
		testObj.playOrStopExplanationSound(entryWithValidFileName);

		// then
		verify(mediaWrapperCreator).create(eq(FILE_NAME), callbackReceiverCaptor.capture());
		callbackReceiverCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		InOrder inOrder = inOrder(mocksCollector.getMocks());
		inOrder.verify(explanationView).setExplanationPlayButtonStyle();
		verifyInOrderSourceHandlerAdding(inOrder, MediaEventTypes.ON_PAUSE, currentPageScope);
		verifyInOrderSourceHandlerAdding(inOrder, MediaEventTypes.ON_END, currentPageScope);
		verifyInOrderSourceHandlerAdding(inOrder, MediaEventTypes.ON_STOP, currentPageScope);
		verifyInOrderSourceHandlerAdding(inOrder, MediaEventTypes.ON_PLAY, currentPageScope);

		inOrder.verify(mediaWrapperController).stopAndPlay(mediaWrapper);
	}

	@Test
	public void shouldSetStopButtonCssWhenEventIsNotPlay() {
		// given
		MediaEvent mediaEvent = mock(MediaEvent.class);
		when(mediaEvent.getType()).thenReturn(MediaEventTypes.ON_PAUSE);

		CurrentPageScope currentPageScope = mock(CurrentPageScope.class);
		when(currentPageScropProvider.get()).thenReturn(currentPageScope);

		testObj.playOrStopExplanationSound(entryWithValidFileName);
		verify(mediaWrapperCreator).create(eq(FILE_NAME), callbackReceiverCaptor.capture());
		callbackReceiverCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		verifySourceHandlerAdding(MediaEventTypes.ON_PLAY, currentPageScope);

		// when
		abstractMediaHandlerCaptor.getValue().onMediaEvent(mediaEvent);

		// then
		verify(explanationView).setExplanationStopButtonStyle();

	}

	@Test
	public void shouldDoNothingWhenEventIsPlay() {
		// given
		MediaEvent mediaEvent = mock(MediaEvent.class);
		when(mediaEvent.getType()).thenReturn(MediaEventTypes.ON_PLAY);

		CurrentPageScope currentPageScope = mock(CurrentPageScope.class);
		when(currentPageScropProvider.get()).thenReturn(currentPageScope);

		testObj.playOrStopExplanationSound(entryWithValidFileName);
		verify(mediaWrapperCreator).create(eq(FILE_NAME), callbackReceiverCaptor.capture());
		callbackReceiverCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		verifySourceHandlerAdding(MediaEventTypes.ON_PLAY, currentPageScope);

		// when
		abstractMediaHandlerCaptor.getValue().onMediaEvent(mediaEvent);

		// then
		verify(explanationView).setExplanationPlayButtonStyle();
		verifyNoMoreInteractions(explanationView);
	}

	@Test
	public void shouldNotPlayWhenFileNameIsNull() {
		// given
		Entry entry = mock(Entry.class);

		// when
		testObj.playOrStopExplanationSound(entry);

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
		testObj.playOrStopExplanationSound(entry);

		// then
		verify(entry).getEntryExampleSound();
		verifyZeroInteractions(mocksCollector.getMocks());
	}

	@Test
	public void shouldStopPlaying() {
		// given
		testObj.playOrStopExplanationSound(entryWithValidFileName);

		// when
		verify(mediaWrapperCreator).create(eq(FILE_NAME), callbackReceiverCaptor.capture());
		callbackReceiverCaptor.getValue().setCallbackReturnObject(mediaWrapper);

		testObj.playOrStopExplanationSound(entryWithValidFileName);

		// then
		verify(explanationView).setExplanationStopButtonStyle();
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
