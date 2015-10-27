package eu.ydp.empiria.player.client.module.external.common.sound;

import com.google.common.base.Optional;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExternalSoundInstanceTest {

    private ExternalSoundInstance testObj;
    @Mock
    private MediaWrapperController mediaWrapperController;
    @Mock
    private MediaWrapper<Widget> mediaWrapper;
    @Captor
    private ArgumentCaptor<MediaEventHandler> mediaEventHandlerArgumentCaptor;
    @Mock
    private OnEndCallback onEndCallback;
    @Mock
    private OnPauseCallback onPauseCallback;

    private Optional<OnEndCallback> onEndCallbackOptional;

    private Optional<OnPauseCallback> onPauseCallbackOptional;

    @Before
    public void before() {
        onEndCallbackOptional = Optional.of(onEndCallback);
        onPauseCallbackOptional = Optional.of(onPauseCallback);
        testObj = new ExternalSoundInstance(mediaWrapper, onEndCallbackOptional, onPauseCallbackOptional, mediaWrapperController);
    }

    @Test
    public void shouldRegisterOnEndCallback() {
        // when
        verify(mediaWrapperController).addHandler(eq(MediaEventTypes.ON_END), eq(mediaWrapper), mediaEventHandlerArgumentCaptor.capture());
        MediaEventHandler mediaEventHandler = mediaEventHandlerArgumentCaptor.getValue();
        mediaEventHandler.onMediaEvent(null);

        // then
        verify(onEndCallback).onEnd();
    }

    @Test
    public void shouldNotRegisterAbsentOnEndCallback() {
        // given
        onEndCallbackOptional = Optional.absent();
        mediaWrapperController = mock(MediaWrapperController.class);

        // when
        testObj = new ExternalSoundInstance(mediaWrapper, onEndCallbackOptional, onPauseCallbackOptional, mediaWrapperController);

        // then
        verify(mediaWrapperController, never()).addHandler(eq(MediaEventTypes.ON_END), eq(mediaWrapper), isA(MediaEventHandler.class));
    }


    @Test
    public void shouldRegisterOnPauseCallback() {
        // when
        verify(mediaWrapperController).addHandler(eq(MediaEventTypes.ON_PAUSE), eq(mediaWrapper), mediaEventHandlerArgumentCaptor.capture());
        MediaEventHandler mediaEventHandler = mediaEventHandlerArgumentCaptor.getValue();
        mediaEventHandler.onMediaEvent(null);

        // then
        verify(onPauseCallback).onPause();
    }

    @Test
    public void shouldNotRegisterAbsentOnPauseCallback() {
        // given
        onPauseCallbackOptional = Optional.absent();
        mediaWrapperController = mock(MediaWrapperController.class);

        // when
        testObj = new ExternalSoundInstance(mediaWrapper, onEndCallbackOptional, onPauseCallbackOptional, mediaWrapperController);

        // then
        verify(mediaWrapperController, never()).addHandler(eq(MediaEventTypes.ON_PAUSE), eq(mediaWrapper), isA(MediaEventHandler.class));
    }

    @Test
    public void shouldDelegatePlay() {
        // when
        testObj.play();

        // then
        verify(mediaWrapperController).stopAndPlay(mediaWrapper);
    }

    @Test
    public void shouldDelegatePlayLooped() {
        // when
        testObj.playLooped();

        // then
        verify(mediaWrapperController).playLooped(mediaWrapper);
    }

    @Test
    public void shouldDelegateStop() {
        // when
        testObj.stop();

        // then
        verify(mediaWrapperController).stop(mediaWrapper);
    }

    @Test
    public void shouldDelegatePause() {
        // when
        testObj.pause();

        // then
        verify(mediaWrapperController).pause(mediaWrapper);
    }

    @Test
    public void shouldDelegateResume() {
        // when
        testObj.resume();

        // then
        verify(mediaWrapperController).resume(mediaWrapper);
    }
}
