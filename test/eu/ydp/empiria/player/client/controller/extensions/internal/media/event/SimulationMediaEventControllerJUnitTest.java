package eu.ydp.empiria.player.client.controller.extensions.internal.media.event;

import static junitparams.JUnitParamsRunner.*;
import static org.mockito.Mockito.*;

import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.AbstractMediaProcessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

@RunWith(JUnitParamsRunner.class)
public class SimulationMediaEventControllerJUnitTest {

	@InjectMocks
	private SimulationMediaEventController testObj;

	@Mock
	private DefaultMediaEventControllerWithoutOnPlay defaultMediaEventControllerWithoutOnPlay;
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private MediaEvent event;
	@Mock
	private MediaExecutor<?> executor;
	@Mock
	private AbstractMediaProcessor processor;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldProcess_onPlay() {
		// when
		when(event.getAssociatedType().getType()).thenReturn(MediaEventTypes.ON_PLAY);
		testObj.onMediaEvent(event, executor, processor);

		// then
		verifyZeroInteractions(defaultMediaEventControllerWithoutOnPlay);
	}

	@Test
	@Parameters
	public void shouldDelegateOtherEventsToDefaultController(MediaEventTypes type) {
		// when
		when(event.getAssociatedType().getType()).thenReturn(type);
		testObj.onMediaEvent(event, executor, processor);

		// then
		verify(defaultMediaEventControllerWithoutOnPlay).onMediaEvent(event, executor, processor);
	}

	public Object[] parametersForShouldDelegateOtherEventsToDefaultController() {
		List<MediaEventTypes> eventsTypes = Lists.newArrayList(MediaEventTypes.values());
		List<MediaEventTypes> eventsToTest = FluentIterable.from(eventsTypes).filter(new Predicate<MediaEventTypes>() {

			@Override
			public boolean apply(MediaEventTypes type) {
				return type != MediaEventTypes.ON_PLAY;
			}

		}).toList();

		return $(eventsToTest.toArray());
	}
}
