package eu.ydp.empiria.player.client.module.external.common.sound;

import com.google.common.base.Optional;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.gin.factory.ExternalInteractionModuleFactory;
import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.external.common.ExternalPaths;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExternalSoundInstanceCreatorTest {

    @InjectMocks
    private ExternalSoundInstanceCreator testObj;
    @Mock
    private ExternalPaths paths;
    @Mock
    private MediaWrapperCreator mediaWrapperCreator;
    @Mock
    private ExternalInteractionModuleFactory moduleFactory;
    @Mock
    private ExternalSoundInstanceCallback callback;
    @Mock
    private MediaWrapper<Widget> audioWrapper;
    @Mock
    private ExternalSoundInstance soundInstance;
    @Captor
    private ArgumentCaptor<CallbackReceiver<MediaWrapper<Widget>>> argumentCaptor;
    private Optional<OnEndCallback> onEndCallback = Optional.absent();
    private Optional<OnPauseCallback> onPauseCallback = Optional.absent();

    @Before
    public void init() {
        when(paths.getExternalFilePath("ok.mp3")).thenReturn("external/ok.mp3");
        when(moduleFactory.getExternalSoundInstance(audioWrapper, onEndCallback, onPauseCallback)).thenReturn(soundInstance);
    }

    @Test
    public void shouldCreateSound() {
        // given
        String src = "ok.mp3";

        // when
        testObj.createSound(src, callback, onEndCallback, onPauseCallback);
        verify(mediaWrapperCreator).createExternalMediaWrapper(eq("external/ok.mp3"), argumentCaptor.capture());
        CallbackReceiver<MediaWrapper<Widget>> callbackReceiver = argumentCaptor.getValue();
        callbackReceiver.setCallbackReturnObject(audioWrapper);

        // then
        verify(callback).onSoundCreated(soundInstance, src);
    }
}
