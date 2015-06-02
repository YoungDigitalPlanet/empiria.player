package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.gin.factory.DictionaryModuleFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;

@RunWith(MockitoJUnitRunner.class)
public class ExplanationDescriptionSoundControllerTest {

	private ExplanationDescriptionSoundController testObj;
	private static final String FILE_NAME = "test.mp3";

	@Mock
	private ExplanationView explanationView;

	@Mock
	private DictionaryModuleFactory dictionaryModuleFactory;

	@Mock
	private DescriptionSoundController descriptionSoundController;

	@Mock
	private MediaEvent mediaEvent;

	@Mock
	private MediaWrapper<Widget> mediaWrapper;

	@Captor
	private ArgumentCaptor<CallbackReceiver<MediaWrapper<Widget>>> callbackReceiverCaptor;

	@Captor
	private ArgumentCaptor<AbstractMediaEventHandler> abstractMediaHandlerCaptor;

	@Before
	public void setUp() {
		when(dictionaryModuleFactory.getDescriptionSoundController(explanationView)).thenReturn(descriptionSoundController);
		testObj = spy(new ExplanationDescriptionSoundController(explanationView, dictionaryModuleFactory));
	}

	@Test
	public void shouldPlayWhenIsNotPlayingAndPlayOrStopMethodIsCalled() {
		// given
		when(descriptionSoundController.isPlaying()).thenReturn(false);
		when(descriptionSoundController.isMediaEventNotOnPlay(mediaEvent)).thenReturn(true);

		// when
		testObj.playOrStopExplanationSound(FILE_NAME);

		// then
		verify(descriptionSoundController).playDescriptionSound(eq(FILE_NAME), callbackReceiverCaptor.capture());
		CallbackReceiver<MediaWrapper<Widget>> value = callbackReceiverCaptor.getValue();
		value.setCallbackReturnObject(mediaWrapper);
		verify(descriptionSoundController).playFromMediaWrapper(abstractMediaHandlerCaptor.capture(), eq(mediaWrapper));
		verify(explanationView).setExplanationPlayButtonStyle();
		abstractMediaHandlerCaptor.getValue().onMediaEvent(mediaEvent);
		verify(explanationView).setExplanationStopButtonStyle();
		verify(descriptionSoundController).stopPlaying();
	}

	@Test
	public void shouldNotStopIfMediaWrapperIsNotPlayingAndPlayOrStopMethodIsCalled() {
		// given
		when(descriptionSoundController.isPlaying()).thenReturn(false);
		when(descriptionSoundController.isMediaEventNotOnPlay(mediaEvent)).thenReturn(false);


		// when
		testObj.playOrStopExplanationSound(FILE_NAME);

		// then
		verify(descriptionSoundController).playDescriptionSound(eq(FILE_NAME), callbackReceiverCaptor.capture());
		CallbackReceiver<MediaWrapper<Widget>> value = callbackReceiverCaptor.getValue();
		value.setCallbackReturnObject(mediaWrapper);
		verify(descriptionSoundController).playFromMediaWrapper(abstractMediaHandlerCaptor.capture(), eq(mediaWrapper));
		verify(explanationView).setExplanationPlayButtonStyle();
		abstractMediaHandlerCaptor.getValue().onMediaEvent(mediaEvent);
		verify(explanationView, never()).setExplanationStopButtonStyle();
		verify(descriptionSoundController, never()).stopPlaying();
	}

	@Test
	public void shouldStopPlayingWhenPlayingAndPlayOrStopMethodIsCalled() {
		// given
		when(descriptionSoundController.isPlaying()).thenReturn(true);

		// when
		testObj.playOrStopExplanationSound(FILE_NAME);

		// then
		verify(testObj).stop();
	}
}
