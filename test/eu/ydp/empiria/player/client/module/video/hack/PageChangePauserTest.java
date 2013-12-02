package eu.ydp.empiria.player.client.module.video.hack;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.video.view.VideoPlayerNative;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

@RunWith(MockitoJUnitRunner.class)
public class PageChangePauserTest {

	@InjectMocks
	private PageChangePauser testObj;

	@Mock
	private EventsBus eventsBus;

	@Test
	public void testRegistration() {
		// given
		VideoPlayerNative videoPlayerNative = mock(VideoPlayerNative.class);

		// when
		testObj.registerPauseOnPageChange(videoPlayerNative);

		// then
		verify(eventsBus).addHandler(eq(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE)), any(VideoPlayerPageChangeHandler.class));
	}
}
