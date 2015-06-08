package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.gin.factory.DictionaryModuleFactory;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

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
        testObj = spy(new ExplanationDescriptionSoundController(explanationView, descriptionSoundController));
    }

    @Test
    public void shouldPlay_whenIsNotPlayingAndPlayOrStopMethodIsCalled() {
        // given
        when(descriptionSoundController.isPlaying()).thenReturn(false);

        // when
        testObj.playOrStopExplanationSound(FILE_NAME);

        // then
        verify(descriptionSoundController).createMediaWrapper(eq(FILE_NAME), callbackReceiverCaptor.capture());
        CallbackReceiver<MediaWrapper<Widget>> value = callbackReceiverCaptor.getValue();
        value.setCallbackReturnObject(mediaWrapper);
        verify(descriptionSoundController).playFromMediaWrapper(abstractMediaHandlerCaptor.capture(), eq(mediaWrapper));
        verify(explanationView).setExplanationPlayButtonStyle();
        abstractMediaHandlerCaptor.getValue().onMediaEvent(mediaEvent);
        verify(explanationView).setExplanationStopButtonStyle();
        verify(descriptionSoundController).stopPlaying();
    }

    @Test
    public void shouldStopPlaying_whenPlayingAndPlayOrStopMethodIsCalled() {
        // given
        when(descriptionSoundController.isPlaying()).thenReturn(true);

        // when
        testObj.playOrStopExplanationSound(FILE_NAME);

        // then
        verify(testObj).stop();
    }
}
