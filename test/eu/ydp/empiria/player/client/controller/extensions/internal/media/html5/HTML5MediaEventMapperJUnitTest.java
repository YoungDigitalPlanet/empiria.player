package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.gwt.media.client.MediaBase;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SoundExecutorListener;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEvent;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

@SuppressWarnings("PMD")
public class HTML5MediaEventMapperJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private HTML5MediaEventMapper instance;
	private EventsBus eventsBus;

	@Before
	public void before() {
		GuiceModuleConfiguration conf = new GuiceModuleConfiguration();
		conf.addAllClassToSpy(EventsBus.class);
		setUp(conf);
		instance = injector.getInstance(HTML5MediaEventMapper.class);
		eventsBus = injector.getInstance(EventsBus.class);
	}

	private Map<HTML5MediaEventsType, MediaEventTypes> creatEventsPairMap() {
		Map<HTML5MediaEventsType, MediaEventTypes> pairMap = ImmutableMap.<HTML5MediaEventsType, MediaEventTypes> builder()
				.put(HTML5MediaEventsType.canplay, MediaEventTypes.CAN_PLAY).put(HTML5MediaEventsType.suspend, MediaEventTypes.SUSPEND)
				.put(HTML5MediaEventsType.durationchange, MediaEventTypes.ON_DURATION_CHANGE).put(HTML5MediaEventsType.ended, MediaEventTypes.ON_END)
				.put(HTML5MediaEventsType.error, MediaEventTypes.ON_ERROR).put(HTML5MediaEventsType.pause, MediaEventTypes.ON_PAUSE)
				.put(HTML5MediaEventsType.timeupdate, MediaEventTypes.ON_TIME_UPDATE).put(HTML5MediaEventsType.volumechange, MediaEventTypes.ON_VOLUME_CHANGE)
				.put(HTML5MediaEventsType.play, MediaEventTypes.ON_PLAY).build();
		return pairMap;
	}

	@Test
	public void testOnEvent() {

		MediaWrapper<MediaBase> mediaWrapper = mock(MediaWrapper.class);
		Map<HTML5MediaEventsType, MediaEventTypes> pairMap = creatEventsPairMap();
		SoundExecutorListener soundExecutorListener = mock(SoundExecutorListener.class);
		Set<HTML5MediaEventsType> asyncEvents = Sets.newHashSet(HTML5MediaEventsType.durationchange, HTML5MediaEventsType.timeupdate);

		for (Map.Entry<HTML5MediaEventsType, MediaEventTypes> typePair : pairMap.entrySet()) {
			ArgumentCaptor<MediaEvent> eventCaptor = ArgumentCaptor.forClass(MediaEvent.class);
			HTML5MediaEvent event = mock(HTML5MediaEvent.class);
			doReturn(typePair.getKey()).when(event).getType();
			instance.mapAndFireEvent(event, soundExecutorListener, mediaWrapper);
			if (asyncEvents.contains(typePair.getKey())) {
				verify(eventsBus).fireAsyncEventFromSource(eventCaptor.capture(), Matchers.eq(mediaWrapper));
			} else {
				verify(eventsBus).fireEventFromSource(eventCaptor.capture(), Matchers.eq(mediaWrapper));

			}
			verifyNoMoreInteractions(eventsBus);
			assertEquals(typePair.getValue(), eventCaptor.getValue().getType());
			Mockito.reset(eventsBus);
		}
	}

	@Test
	public void testOnEventSoundExecutorListener() {

		MediaWrapper<MediaBase> mediaWrapper = mock(MediaWrapper.class);
		Map<HTML5MediaEventsType, MediaEventTypes> pairMap = creatEventsPairMap();
		SoundExecutorListener soundExecutorListener = mock(SoundExecutorListener.class);
		Set<HTML5MediaEventsType> listenerEvents = Sets.newHashSet(HTML5MediaEventsType.ended, HTML5MediaEventsType.play);

		for (Map.Entry<HTML5MediaEventsType, MediaEventTypes> typePair : pairMap.entrySet()) {
			HTML5MediaEvent event = mock(HTML5MediaEvent.class);
			doReturn(typePair.getKey()).when(event).getType();
			instance.mapAndFireEvent(event, soundExecutorListener, mediaWrapper);
			if (listenerEvents.contains(typePair.getKey())) {
				if (typePair.getKey() == HTML5MediaEventsType.play) {
					verify(soundExecutorListener).onPlay();
				} else {
					verify(soundExecutorListener).onSoundFinished();
				}
			}
			verifyNoMoreInteractions(soundExecutorListener);
			Mockito.reset(soundExecutorListener);
		}
	}

}
