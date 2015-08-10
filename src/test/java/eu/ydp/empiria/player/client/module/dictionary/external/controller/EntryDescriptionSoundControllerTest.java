package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.gin.factory.DictionaryModuleFactory;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
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
public class EntryDescriptionSoundControllerTest {

    private EntryDescriptionSoundController testObj;

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

    @Mock
    private Entry entryWithValidFileName;

    @Mock
    private CallbackReceiver<MediaWrapper<Widget>> callbackReceiver;

    @Captor
    private ArgumentCaptor<CallbackReceiver<MediaWrapper<Widget>>> callbackReceiverCaptor;

    @Captor
    private ArgumentCaptor<MediaEventHandler> abstractMediaHandlerCaptor;

    @Before
    public void setUp() {
        testObj = spy(new EntryDescriptionSoundController(explanationView, descriptionSoundController));
    }

    @Test
    public void shouldPlay_whenIsNotPlayingAndPlayOrStopMethodIsCalled() {
        // given
        when(descriptionSoundController.isPlaying()).thenReturn(false);

        // when
        testObj.playOrStopEntrySound(entryWithValidFileName.getEntrySound());

        // then
        verify(descriptionSoundController).createMediaWrapper(eq(entryWithValidFileName.getEntrySound()), callbackReceiverCaptor.capture());
        CallbackReceiver<MediaWrapper<Widget>> value = callbackReceiverCaptor.getValue();
        value.setCallbackReturnObject(mediaWrapper);
        verify(descriptionSoundController).playFromMediaWrapper(abstractMediaHandlerCaptor.capture(), eq(mediaWrapper));
        verify(explanationView).setEntryPlayButtonStyle();
        abstractMediaHandlerCaptor.getValue().onMediaEvent(mediaEvent);
        verify(explanationView).setEntryStopButtonStyle();
        verify(descriptionSoundController).stopPlaying();
    }

    @Test
    public void shouldStopPlaying_whenPlayingAndPlayOrStopMethodIsCalled() {
        // given
        when(descriptionSoundController.isPlaying()).thenReturn(true);

        // when
        testObj.playOrStopEntrySound(entryWithValidFileName.getEntrySound());

        // then
        verify(testObj).stop();
    }

}
