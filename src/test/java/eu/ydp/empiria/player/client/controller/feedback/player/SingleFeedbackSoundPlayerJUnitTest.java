package eu.ydp.empiria.player.client.controller.feedback.player;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.gin.factory.SingleFeedbackSoundPlayerFactory;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.gwtutil.client.event.EventImpl.Type;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SingleFeedbackSoundPlayerJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private EventsBus eventsBus;
	private SingleFeedbackSoundPlayer instance;

	private MediaWrapper<?> mediaWrapper;

	@Before
	public void before() {
		setUp(new Class<?>[]{}, new Class<?>[]{}, new Class<?>[]{EventsBus.class});
		eventsBus = injector.getInstance(EventsBus.class);
		mediaWrapper = mock(MediaWrapper.class);
		instance = getInstance(mediaWrapper);
	}

	private SingleFeedbackSoundPlayer getInstance(MediaWrapper<?> wrapper) {
		SingleFeedbackSoundPlayerFactory factory = injector.getInstance(SingleFeedbackSoundPlayerFactory.class);
		return spy(factory.getSingleFeedbackSoundPlayer(wrapper));
	}

	@SuppressWarnings("all")
	@Test
	public void constructorTest() {
		// prepare
		Function<Type, MediaEventTypes> typeToMediaType = new Function<Type, MediaEventTypes>() {
			@Override
			public MediaEventTypes apply(Type type) {
				return (MediaEventTypes) type.getType();
			}
		};

		instance.firePlayEvent(mediaWrapper);

		ArgumentCaptor<Type[]> arguments = ArgumentCaptor.forClass(Type[].class);
		// test
		verify(eventsBus).addHandlerToSource(arguments.capture(), org.mockito.Matchers.eq(mediaWrapper), org.mockito.Matchers.any(SingleFeedbackSoundPlayer.class));

		// verify
		List<Type> event = Arrays.asList(arguments.getValue());
		List<MediaEventTypes> registerEventsTypes = Lists.transform(event, typeToMediaType);
		// org.fest.assertions.Assertions.assertThat(registerEventsTypes).containsOnly(MediaEventTypes.ON_STOP, MediaEventTypes.ON_PLAY);
		MatcherAssert.assertThat(registerEventsTypes, Matchers.containsInAnyOrder(MediaEventTypes.ON_STOP, MediaEventTypes.ON_PLAY, MediaEventTypes.ON_PAUSE));
	}

	@Test
	public void firePlayEventTest() {
		instance.firePlayEvent(mediaWrapper);

		ArgumentCaptor<MediaEvent> arguments = ArgumentCaptor.forClass(MediaEvent.class);
		verify(eventsBus).fireEventFromSource(arguments.capture(), org.mockito.Matchers.eq(mediaWrapper));
		MediaEvent event = arguments.getValue();
		assertEquals(MediaEventTypes.PLAY, event.getType());
		assertEquals(mediaWrapper, event.getMediaWrapper());

	}

	@Test
	public void fireStopEventTest() {
		instance.fireStopEvent(mediaWrapper);

		ArgumentCaptor<MediaEvent> arguments = ArgumentCaptor.forClass(MediaEvent.class);
		verify(eventsBus).fireEventFromSource(arguments.capture(), org.mockito.Matchers.eq(mediaWrapper));
		MediaEvent event = arguments.getValue();
		assertEquals(MediaEventTypes.STOP, event.getType());
		assertEquals(mediaWrapper, event.getMediaWrapper());
	}

	@Test
	public void playTest() {
		doReturn(true).when(instance).isPlayed();
		doNothing().when(instance).fireStopEvent(org.mockito.Matchers.eq(mediaWrapper));
		// test stop next play
		instance.play();

		// verify
		verify(instance).fireStopEvent(org.mockito.Matchers.eq(mediaWrapper));
		assertTrue(instance.playAfterStop);

		// prepare
		doReturn(false).when(instance).isPlayed();

		// test only play
		instance.play();

		// verify
		verify(instance).firePlayEvent(org.mockito.Matchers.eq(mediaWrapper));

	}

	@Test
	public void playIfRequiredTest() {
		doNothing().when(instance).firePlayEvent(org.mockito.Matchers.eq(mediaWrapper));
		// test no interaction
		instance.playIfRequired();

		// verify
		verify(instance).playIfRequired();
		Mockito.verifyNoMoreInteractions(instance);

		// prepare
		instance.playAfterStop = true;
		instance.playIfRequired();

		// verify
		assertFalse(instance.playAfterStop);
		verify(instance).firePlayEvent(org.mockito.Matchers.eq(mediaWrapper));

	}

	@Test
	public void isPlayedTest() {
		instance.setPlayed(true);
		assertTrue(instance.isPlayed());

		instance.setPlayed(false);
		assertFalse(instance.isPlayed());

	}

	@Test
	public void setPlayedTest() {
		instance.setPlayed(true);
		assertTrue(instance.isPlayed());
		verify(instance).isPlayed();
		verify(instance).setPlayed(org.mockito.Matchers.anyBoolean());
		Mockito.verifyNoMoreInteractions(instance);
	}

	@Test
	public void onMediaEventPlayTest() {
		// prepare
		doNothing().when(instance).playIfRequired();
		doNothing().when(instance).setPlayed(org.mockito.Matchers.anyBoolean());

		MediaEvent event = mock(MediaEvent.class);
		doReturn(MediaEventTypes.ON_PLAY).when(event).getType();

		// test
		instance.onMediaEvent(event);

		// verify
		verify(instance).setPlayed(org.mockito.Matchers.eq(true));
		verify(instance).onMediaEvent(org.mockito.Matchers.eq(event));
		Mockito.verifyNoMoreInteractions(instance);

	}

	@Test
	public void onMediaEventStopTest() {
		// prepare
		doNothing().when(instance).playIfRequired();
		doNothing().when(instance).setPlayed(org.mockito.Matchers.anyBoolean());

		MediaEvent event = mock(MediaEvent.class);
		doReturn(MediaEventTypes.ON_STOP).when(event).getType();

		// test
		instance.onMediaEvent(event);

		// verify
		verify(instance).setPlayed(org.mockito.Matchers.eq(false));
		verify(instance).onMediaEvent(org.mockito.Matchers.eq(event));
		verify(instance).playIfRequired();
		Mockito.verifyNoMoreInteractions(instance);

	}

	@Test
	public void onMediaEventOtherEventsTest() {
		// prepare
		MediaEvent event = mock(MediaEvent.class);
		doReturn(MediaEventTypes.ON_STOP).when(event).getType();

		List<MediaEventTypes> types = new ArrayList<MediaEventTypes>(Arrays.asList(MediaEventTypes.values()));
		types.remove(MediaEventTypes.ON_STOP);
		types.remove(MediaEventTypes.ON_PAUSE);
		types.remove(MediaEventTypes.ON_PLAY);

		// test
		for (MediaEventTypes type : types) {
			doReturn(type).when(event).getType();
			instance.onMediaEvent(event);
		}

		// verify
		verify(instance, times(types.size())).onMediaEvent(org.mockito.Matchers.eq(event));
		Mockito.verifyNoMoreInteractions(instance);
	}

}
