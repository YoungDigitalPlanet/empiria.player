package eu.ydp.empiria.player.client.controller.feedback.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.gin.factory.SingleFeedbackSoundPlayerFactory;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.Event.Type;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class SingleFeedbackSoundPlayerJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private EventsBus eventsBus;
	private SingleFeedbackSoundPlayer instance;

	private MediaWrapper<?> mediaWrapper;

	@Before
	public void before() {
		setUp(new Class<?>[] {}, new Class<?>[] {}, new Class<?>[] { EventsBus.class });
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
	public void postConstructTest() {
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
		verify(eventsBus).addHandlerToSource(arguments.capture(), Mockito.eq(mediaWrapper), Mockito.any(SingleFeedbackSoundPlayer.class));

		// verify
		List<Type> event = Arrays.asList(arguments.getValue());
		List<MediaEventTypes> registerEventsTypes = Lists.transform(event, typeToMediaType);
	//	org.fest.assertions.Assertions.assertThat(registerEventsTypes).containsOnly(MediaEventTypes.ON_STOP, MediaEventTypes.ON_PLAY);
		MatcherAssert.assertThat(registerEventsTypes, Matchers.containsInAnyOrder(MediaEventTypes.ON_STOP, MediaEventTypes.ON_PLAY, MediaEventTypes.ON_PAUSE));
	}

	@Test
	public void firePlayEventTest() {
		instance.firePlayEvent(mediaWrapper);

		ArgumentCaptor<MediaEvent> arguments = ArgumentCaptor.forClass(MediaEvent.class);
		verify(eventsBus).fireEventFromSource(arguments.capture(), Mockito.eq(mediaWrapper));
		MediaEvent event = arguments.getValue();
		assertEquals(MediaEventTypes.PLAY, event.getType());
		assertEquals(mediaWrapper, event.getMediaWrapper());

	}

	@Test
	public void fireStopEventTest() {
		instance.fireStopEvent(mediaWrapper);

		ArgumentCaptor<MediaEvent> arguments = ArgumentCaptor.forClass(MediaEvent.class);
		verify(eventsBus).fireEventFromSource(arguments.capture(), Mockito.eq(mediaWrapper));
		MediaEvent event = arguments.getValue();
		assertEquals(MediaEventTypes.STOP, event.getType());
		assertEquals(mediaWrapper, event.getMediaWrapper());
	}

	@Test
	public void playTest() {
		doReturn(true).when(instance).isPlayed();
		doNothing().when(instance).fireStopEvent(Mockito.eq(mediaWrapper));
		// test stop next play
		instance.play();

		// verify
		verify(instance).fireStopEvent(Mockito.eq(mediaWrapper));
		assertTrue(instance.playAfterStop);

		// prepare
		doReturn(false).when(instance).isPlayed();

		// test only play
		instance.play();

		// verify
		verify(instance).firePlayEvent(Mockito.eq(mediaWrapper));

	}

	@Test
	public void playIfRequiredTest() {
		doNothing().when(instance).firePlayEvent(Mockito.eq(mediaWrapper));
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
		verify(instance).firePlayEvent(Mockito.eq(mediaWrapper));

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
		verify(instance).setPlayed(Mockito.anyBoolean());
		Mockito.verifyNoMoreInteractions(instance);
	}

	@Test
	public void onMediaEventPlayTest() {
		// prepare
		doNothing().when(instance).playIfRequired();
		doNothing().when(instance).setPlayed(Mockito.anyBoolean());

		MediaEvent event = mock(MediaEvent.class);
		doReturn(MediaEventTypes.ON_PLAY).when(event).getType();

		// test
		instance.onMediaEvent(event);

		// verify
		verify(instance).setPlayed(Mockito.eq(true));
		verify(instance).onMediaEvent(Mockito.eq(event));
		Mockito.verifyNoMoreInteractions(instance);

	}

	@Test
	public void onMediaEventStopTest() {
		// prepare
		doNothing().when(instance).playIfRequired();
		doNothing().when(instance).setPlayed(Mockito.anyBoolean());

		MediaEvent event = mock(MediaEvent.class);
		doReturn(MediaEventTypes.ON_STOP).when(event).getType();

		// test
		instance.onMediaEvent(event);

		// verify
		verify(instance).setPlayed(Mockito.eq(false));
		verify(instance).onMediaEvent(Mockito.eq(event));
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

		//test
		for(MediaEventTypes type : types){
			doReturn(type).when(event).getType();
			instance.onMediaEvent(event);
		}

		//verify
		verify(instance,times(types.size())).onMediaEvent(Mockito.eq(event));
		Mockito.verifyNoMoreInteractions(instance);
	}

}
