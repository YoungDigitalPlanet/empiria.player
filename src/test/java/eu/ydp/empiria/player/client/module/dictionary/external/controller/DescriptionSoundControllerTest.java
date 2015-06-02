package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DescriptionSoundControllerTest{

    private static final String FILE_NAME = "test.mp3";

    @InjectMocks
    public DescriptionSoundController testObject;
    @Mock
    private Entry entry;
    @Mock
    private DictionaryMediaWrapperCreator dictionaryMediaWrapperCreator;
    @Mock
    private CallbackReceiver<MediaWrapper<Widget>> callbackReceiver;
    @Mock
    private AbstractMediaEventHandler abstractMediaHandler;
    @Mock
    private EventsBus eventsBus;
    @Mock
    private MediaWrapper<Widget> mediaWrapper;
    @Mock
    private CurrentPageScope currentPageScope;
    @Mock
    private Provider<CurrentPageScope> currentPageScopeProvider;
    @Mock
    private MediaWrapperController mediaWrapperController;
    @Captor
    private ArgumentCaptor<CallbackReceiver<MediaWrapper<Widget>>> callbackReceiverCaptor;

    @Before
    public void setUp() {
        when(entry.getEntrySound()).thenReturn(FILE_NAME);
    }

    @Test
    public void shouldCreateMediaWrapper_whenFilenameIsNotNull() {
        // when
        testObject.createMediaWrapper(entry.getEntrySound(), callbackReceiver);

        // then
        verify(dictionaryMediaWrapperCreator).create(eq(FILE_NAME), eq(callbackReceiver));
    }

    @Test
    public void shouldNotCreateMediaWrapper_whenFilenameIsNull() {
        // given
        when(entry.getEntrySound()).thenReturn(null);

        // when
        testObject.createMediaWrapper(entry.getEntrySound(), callbackReceiver);

        // then
        verify(dictionaryMediaWrapperCreator, never()).create(eq(FILE_NAME), eq(callbackReceiver));
    }

    @Test
    public void shouldNotCreateMediaWrapper_whenFilenameIsEmpty() {
        // given
        when(entry.getEntrySound()).thenReturn("");

        // when
        testObject.createMediaWrapper(entry.getEntrySound(), callbackReceiver);

        // then
        verify(dictionaryMediaWrapperCreator, never()).create(eq(FILE_NAME), eq(callbackReceiver));
    }

    @Test
    public void shouldAddAllMediaHandlersAndPlay_whenPlayFromMediaWrapperIsCalled() {
        // given
        when(currentPageScopeProvider.get()).thenReturn(currentPageScope);

        // when
        testObject.playFromMediaWrapper(abstractMediaHandler, mediaWrapper);

        // then
        verify(eventsBus).addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PAUSE), mediaWrapper, abstractMediaHandler, currentPageScopeProvider.get());
        verify(eventsBus).addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_END), mediaWrapper, abstractMediaHandler, currentPageScopeProvider.get());
        verify(eventsBus).addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_STOP), mediaWrapper, abstractMediaHandler, currentPageScopeProvider.get());
        verify(mediaWrapperController).stopAndPlay(mediaWrapper);
    }

    @Test
    public void shouldStopMediaWrapper_whenStopMediaWrapperIsCalled(){
        // then
        testObject.playFromMediaWrapper(abstractMediaHandler,mediaWrapper);
        testObject.stopMediaWrapper();

        // then
        verify(mediaWrapperController).stop(mediaWrapper);
    }

}
