package eu.ydp.empiria.player.client.module.button;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

public class FeedbackAudioMuteButtonModuleTest {

	private FeedbackAudioMuteButtonModule button;

	@Before
	public void initTest() {
		button = org.mockito.Mockito.mock(FeedbackAudioMuteButtonModule.class, Mockito.CALLS_REAL_METHODS);
		doNothing().when(button).updateStyleName();
	}

	@Test
	public void testChangeStyleOnTestPageLoadedExpectsVisibleWhenPageContainsInteractiveModules() {
		when(button.hasItemInteractiveModules()).thenReturn(true);
		
		button.onPlayerEvent(new PlayerEvent(PlayerEventTypes.PAGE_LOADED));

		String expected = FeedbackAudioMuteButtonModule.STYLE_NAME__OFF;
		assertThat(button.getStyleName(), equalTo(expected));
	}

	@Test
	public void testChangeStyleOnTestPageLoadedExpectsHiddenWhenPageContainsInteractiveModules() {
		when(button.hasItemInteractiveModules()).thenReturn(false);
		
		button.onPlayerEvent(new PlayerEvent(PlayerEventTypes.PAGE_LOADED));

		String expected = FeedbackAudioMuteButtonModule.STYLE_NAME__OFF + " " + FeedbackAudioMuteButtonModule.STYLE_NAME__DISABLED;
		assertThat(button.getStyleName(), equalTo(expected));
	}
}
