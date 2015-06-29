package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import static eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType.*;
import static eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes.*;
import static junitparams.JUnitParamsRunner.*;
import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SoundExecutorListener;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

@RunWith(JUnitParamsRunner.class)
public class HTML5MediaEventMapperJUnitTest {

	@InjectMocks
	private HTML5MediaEventMapper testObj;

	@Mock
	private EventsBus eventsBus;
	@Mock
	private SoundExecutorListener soundExecutorListener;
	@Mock
	private MediaWrapper<Widget> mediaWrapper;

	@Captor
	private ArgumentCaptor<MediaEvent> mediaEventCaptor;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@Parameters
	public void shouldFire_ifSyncEvent(HTML5MediaEventsType html5EventType, MediaEventTypes mediaEventType) {
		// when
		testObj.mapAndFireEvent(html5EventType, soundExecutorListener, mediaWrapper);

		// then
		verify(eventsBus).fireEventFromSource(mediaEventCaptor.capture(), eq(mediaWrapper));
		MediaEvent calledEvent = mediaEventCaptor.getValue();
		assertThat(calledEvent.getMediaWrapper()).isEqualTo((MediaWrapper) mediaWrapper);
		assertThat(calledEvent.getType()).isEqualTo(mediaEventType);
	}

	public Object[] parametersForShouldFire_ifSyncEvent() {
		return $($(canplay, CAN_PLAY), $(suspend, SUSPEND), $(ended, ON_END), $(error, ON_ERROR), $(pause, ON_PAUSE), $(volumechange, ON_VOLUME_CHANGE),
				$(play, ON_PLAY), $(volumechange, ON_VOLUME_CHANGE));
	}

	@Test
	@Parameters
	public void shouldFire_ifAsyncEvent(HTML5MediaEventsType html5EventType, MediaEventTypes mediaEventType) {
		// when
		testObj.mapAndFireEvent(html5EventType, soundExecutorListener, mediaWrapper);

		// then
		verify(eventsBus).fireAsyncEventFromSource(mediaEventCaptor.capture(), eq(mediaWrapper));
		MediaEvent calledEvent = mediaEventCaptor.getValue();
		assertThat(calledEvent.getMediaWrapper()).isEqualTo((MediaWrapper) mediaWrapper);
		assertThat(calledEvent.getType()).isEqualTo(mediaEventType);
	}

	public Object[] parametersForShouldFire_ifAsyncEvent() {
		return $($(durationchange, ON_DURATION_CHANGE), $(timeupdate, ON_TIME_UPDATE));
	}

	@Test
	@Parameters
	public void shouldNotFire(HTML5MediaEventsType html5EventType) {
		// when
		testObj.mapAndFireEvent(html5EventType, soundExecutorListener, mediaWrapper);

		// then
		verifyZeroInteractions(eventsBus);
	}

	public Object[] parametersForShouldNotFire() {
		return $(loadedmetadata, waiting, abort, loadeddata, seeking, seeked, loadstart, emptied, ratechange, stalled);
	}

	@Test
	public void shouldCallListener_ifEnded() {
		// given
		HTML5MediaEventsType html5EventType = ended;

		// when
		testObj.mapAndFireEvent(html5EventType, soundExecutorListener, mediaWrapper);

		// then
		verify(soundExecutorListener).onSoundFinished();
	}

	@Test
	public void shouldCallListener_ifPlay() {
		// given
		HTML5MediaEventsType html5EventType = play;

		// when
		testObj.mapAndFireEvent(html5EventType, soundExecutorListener, mediaWrapper);

		// then
		verify(soundExecutorListener).onPlay();
	}

	@Test
	/**
	 * This should not throw any exception when listener is null (no interaction should be called on him)
	 */
	public void shouldNotCallListener_ifListenerIsNull() {
		// given
		HTML5MediaEventsType html5EventType = play;
		soundExecutorListener = null;

		// when
		testObj.mapAndFireEvent(html5EventType, soundExecutorListener, mediaWrapper);
	}

	@Test
	@Parameters
	public void shouldNotCallListener_ifNorEndedNorPlay(HTML5MediaEventsType html5EventType) {
		// when
		testObj.mapAndFireEvent(html5EventType, soundExecutorListener, mediaWrapper);

		// then
		verifyZeroInteractions(soundExecutorListener);
	}

	public Object[] parametersForShouldNotCallListener_ifNorEndedNorPlay() {
		List<HTML5MediaEventsType> events = FluentIterable.from(Lists.newArrayList(HTML5MediaEventsType.values()))
															.filter(new Predicate<HTML5MediaEventsType>() {

																@Override
																public boolean apply(HTML5MediaEventsType type) {
																	return type != play && type != ended;
																}

															})
															.toList();

		return $(events.toArray());
	}

}
