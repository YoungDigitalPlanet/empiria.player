package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.params.MediaParams;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.params.MediaStatus;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class ExternalMediaProcessorResponseTest extends ExternalMediaProcessorTestBase {

	@Test
	public void onReady() {
		// given
		final int DURATION = 91919;
		final double DURATION_SECONDS = 91.919;
		MediaWrapper<Widget> wrapper = container.createMediaWrapper();
		MediaParams params = mock(MediaParams.class);
		stub(params.getDurationMillis()).toReturn(DURATION);
		MediaEventHandler handler = mock(MediaEventHandler.class);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE), wrapper, handler);

		// when
		listener.onReady(wrapper.getMediaUniqId(), params);

		// then
		ArgumentCaptor<MediaEvent> ac = ArgumentCaptor.forClass(MediaEvent.class);
		verify(handler).onMediaEvent(ac.capture());
		assertThat(ac.getValue().getMediaWrapper().getDuration(), equalTo(DURATION_SECONDS));
	}

	@Test
	public void onPlay() {
		// given
		MediaWrapper<Widget> wrapper = container.createMediaWrapper();
		MediaEventHandler handler = mock(MediaEventHandler.class);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PLAY), wrapper, handler);

		// when
		listener.onPlay(wrapper.getMediaUniqId());

		// then
		ArgumentCaptor<MediaEvent> ac = ArgumentCaptor.forClass(MediaEvent.class);
		verify(handler).onMediaEvent(ac.capture());
		assertThat(ac.getValue().getType(), equalTo(MediaEventTypes.ON_PLAY));
	}

	@Test
	public void onStop() {
		// given
		MediaWrapper<Widget> wrapper = container.createMediaWrapper();
		MediaEventHandler handler = mock(MediaEventHandler.class);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PAUSE), wrapper, handler);

		// when
		listener.onPause(wrapper.getMediaUniqId());

		// then
		ArgumentCaptor<MediaEvent> ac = ArgumentCaptor.forClass(MediaEvent.class);
		verify(handler).onMediaEvent(ac.capture());
		assertThat(ac.getValue().getType(), equalTo(MediaEventTypes.ON_PAUSE));
	}

	@Test
	public void onEnd() {
		// given
		MediaWrapper<Widget> wrapper = container.createMediaWrapper();
		MediaEventHandler handler = mock(MediaEventHandler.class);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_END), wrapper, handler);

		// when
		listener.onEnd(wrapper.getMediaUniqId());

		// then
		ArgumentCaptor<MediaEvent> ac = ArgumentCaptor.forClass(MediaEvent.class);
		verify(handler).onMediaEvent(ac.capture());
		assertThat(ac.getValue().getType(), equalTo(MediaEventTypes.ON_END));
	}

	@Test
	public void onSeek() {
		// given
		final int TIME = 91919;
		MediaStatus status = mock(MediaStatus.class);
		stub(status.getCurrentTimeMillis()).toReturn(TIME);
		MediaWrapper<Widget> wrapper = container.createMediaWrapper();
		MediaEventHandler handler = mock(MediaEventHandler.class);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), wrapper, handler);

		// when
		listener.onTimeUpdate(wrapper.getMediaUniqId(), status);

		// then
		ArgumentCaptor<MediaEvent> ac = ArgumentCaptor.forClass(MediaEvent.class);
		verify(handler).onMediaEvent(ac.capture());
		assertThat(ac.getValue().getType(), equalTo(MediaEventTypes.ON_TIME_UPDATE));
	}

	@Test
	public void onTimeUpdate() {
		// given
		final double TIME_SECONDS = 91919;
		final int TIME_MILLIS = 91919000;
		MediaStatus status = mock(MediaStatus.class);
		stub(status.getCurrentTimeMillis()).toReturn(TIME_MILLIS);
		MediaWrapper<Widget> wrapper = container.createMediaWrapper();
		MediaEventHandler handler = mock(MediaEventHandler.class);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), wrapper, handler);

		// when
		listener.onTimeUpdate(wrapper.getMediaUniqId(), status);

		// then
		ArgumentCaptor<MediaEvent> ac = ArgumentCaptor.forClass(MediaEvent.class);
		verify(handler).onMediaEvent(ac.capture());
		assertThat(ac.getValue().getType(), equalTo(MediaEventTypes.ON_TIME_UPDATE));
		assertThat(ac.getValue().getMediaWrapper().getCurrentTime(), equalTo(TIME_SECONDS));
	}
}
