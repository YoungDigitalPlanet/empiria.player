package eu.ydp.empiria.player.client.module.video.presenter;

import eu.ydp.empiria.player.client.module.video.view.VideoPlayerNative;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AutoPauseOnPageChangeHandlerTest {

    private AutoPauseOnPageChangeHandler testObj;

    @Mock
    private VideoPlayerNative nativePlayer;

    @Before
    public void setUp() {
        this.testObj = new AutoPauseOnPageChangeHandler(nativePlayer);
    }

    @Test
    public void test() {
        // given
        PlayerEvent mockEvent = mock(PlayerEvent.class);

        // when
        testObj.onPlayerEvent(mockEvent);

        // then
        verify(nativePlayer).pause();
    }

}
