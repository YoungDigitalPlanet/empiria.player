package eu.ydp.empiria.player.client.module.button;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;

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

		button.onDeliveryEvent(new DeliveryEvent(DeliveryEventType.TEST_PAGE_LOADED));

		String expected = FeedbackAudioMuteButtonModule.STYLE_NAME__OFF;
		assertThat(button.getStyleName(), equalTo(expected));
	}

	@Test
	public void testChangeStyleOnTestPageLoadedExpectsHiddenWhenPageContainsInteractiveModules() {
		when(button.hasItemInteractiveModules()).thenReturn(false);

		button.onDeliveryEvent(new DeliveryEvent(DeliveryEventType.TEST_PAGE_LOADED));

		String expected = FeedbackAudioMuteButtonModule.STYLE_NAME__OFF + " " + FeedbackAudioMuteButtonModule.STYLE_NAME__DISABLED;
		assertThat(button.getStyleName(), equalTo(expected));
	}
}
