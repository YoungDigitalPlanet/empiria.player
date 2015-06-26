package eu.ydp.empiria.player.client.controller.feedback.player;

import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.media.html5.HTML5AudioMediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SingleFeedbackSoundPlayerWithHTML5MediaWrapperJUnitTest {

	private SingleFeedbackSoundPlayer testObj;
	@Mock
	private EventsBus eventsBus;
	@Mock
	private HTML5AudioMediaWrapper mediaWrapper;
	@Mock
	private Provider<HTML5MediaForFeedbacksAvailableOptions> optionsProvider;

	@Mock
	private HTML5MediaForFeedbacksAvailableOptions options;

	@Before
	public void before() {
		when(optionsProvider.get()).thenReturn(options);
		testObj = new SingleFeedbackSoundPlayer(mediaWrapper, eventsBus, optionsProvider);
	}

	@Test
	public void shouldOverrideWrapperOptions() {
		verify(mediaWrapper).setMediaAvailableOptions(options);
	}
}
