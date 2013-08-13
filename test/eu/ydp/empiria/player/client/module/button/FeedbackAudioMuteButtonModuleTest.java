package eu.ydp.empiria.player.client.module.button;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.controller.CurrentPageProperties;
import eu.ydp.empiria.player.client.module.button.FeedbackAudioMuteButtonModule;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public class FeedbackAudioMuteButtonModuleTest {

	private CustomPushButton customPushButton;
	private EventsBus eventsBus;
	private CurrentPageProperties currentPageProperties;

	private FeedbackAudioMuteButtonModule testObj;

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	@Before
	public void before() {
		mock(CustomPushButton.class);

		Injector injector = Guice.createInjector(new CustomGuiceModule());
		testObj = injector.getInstance(FeedbackAudioMuteButtonModule.class);
		eventsBus = injector.getInstance(EventsBus.class);
		currentPageProperties = injector.getInstance(CurrentPageProperties.class);
		customPushButton = injector.getInstance(CustomPushButton.class);
	}

	private static class CustomGuiceModule implements Module {
		@Override
		public void configure(Binder binder) {
			CustomPushButton customPushButton = mock(CustomPushButton.class);
			binder.bind(CustomPushButton.class).toInstance(customPushButton);
			EventsBus eventsBus = mock(EventsBus.class);
			binder.bind(EventsBus.class).toInstance(eventsBus);
			CurrentPageProperties currentPageProperties = mock(CurrentPageProperties.class);
			binder.bind(CurrentPageProperties.class).toInstance(currentPageProperties);
		}
	}

	@Test
	public void testChangeStyleOnTestPageLoadedExpectsVisibleWhenPageContainsInteractiveModules() {
		when(currentPageProperties.hasInteractiveModules()).thenReturn(true);
		testObj.onPlayerEvent(new PlayerEvent(PlayerEventTypes.PAGE_LOADED));

		String expected = FeedbackAudioMuteButtonModule.STYLE_NAME__OFF;
		assertEquals(testObj.getStyleName(), expected);
	}

	@Test
	public void testChangeStyleOnTestPageLoadedExpectsHiddenWhenPageContainsInteractiveModules() {
		when(currentPageProperties.hasInteractiveModules()).thenReturn(false);

		testObj.onPlayerEvent(new PlayerEvent(PlayerEventTypes.PAGE_LOADED));

		String expected = FeedbackAudioMuteButtonModule.STYLE_NAME__OFF + " " + FeedbackAudioMuteButtonModule.STYLE_NAME__DISABLED;
		assertEquals(testObj.getStyleName(), expected);
	}
}
