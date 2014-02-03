package eu.ydp.empiria.player.client.module.media.button;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.junit.GWTMockUtilities;

import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class PlayPauseMediaButtonJunitTest {

	private PlayPauseMediaButton button;

	@Before
	public void setUp() throws Exception {
		button = mock(PlayPauseMediaButton.class, Mockito.CALLS_REAL_METHODS);
	}

	@Test
	public void pauseExpected() {
		when(button.isActive()).thenReturn(true);

		MediaEvent firedEvent = button.createMediaEvent();

		assertThat(firedEvent.getType(), equalTo(MediaEventTypes.PAUSE));
	}

	@Test
	public void playExpected() {
		when(button.isActive()).thenReturn(false);

		MediaEvent firedEvent = button.createMediaEvent();

		assertThat(firedEvent.getType(), equalTo(MediaEventTypes.PLAY));
	}

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void restore() {
		GWTMockUtilities.restore();
	}
}
