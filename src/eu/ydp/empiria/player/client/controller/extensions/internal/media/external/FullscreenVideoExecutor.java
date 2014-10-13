package eu.ydp.empiria.player.client.controller.extensions.internal.media.external;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SoundExecutorListener;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.external.FullscreenVideoMediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class FullscreenVideoExecutor implements MediaExecutor<Widget>, FullscreenVideoConnectorListener {

	@Inject
	private ExternalFullscreenVideoConnector connector;
	@Inject
	private EventsBus eventsBus;
	@Inject
	private FullscreenVideoMediaWrapper mediaWrapper;

	private BaseMediaConfiguration baseMediaConfiguration;

	@Override
	public void init() {
		connector.addConnectorListener(mediaWrapper.getMediaUniqId(), this);
		mediaWrapper.setPoster(baseMediaConfiguration.getPoster());
	}

	@Override
	public MediaWrapper<Widget> getMediaWrapper() {
		return mediaWrapper;
	}

	@Override
	public void setMediaWrapper(MediaWrapper<Widget> descriptor) {
		this.mediaWrapper = (FullscreenVideoMediaWrapper) descriptor;
	}

	@Override
	public void onFullscreenClosed(String id, double currentTimeMillipercent) {
		if (mediaWrapper.getMediaUniqId().equals(id)) {
			setCurrentTime(currentTimeMillipercent);
		}
	}

	@Override
	public void setBaseMediaConfiguration(BaseMediaConfiguration baseMediaConfiguration) {
		this.baseMediaConfiguration = baseMediaConfiguration;
	}

	@Override
	public BaseMediaConfiguration getBaseMediaConfiguration() {
		return baseMediaConfiguration;
	}

	@Override
	@Deprecated
	public void play(String src) {
	}

	@Override
	public void play() {
		connector.openFullscreen(mediaWrapper.getMediaUniqId(), baseMediaConfiguration.getSources().keySet(), mediaWrapper.getCurrentTime());
	}

	@Override
	public void playLooped() {
	}

	@Override
	public void stop() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void setMuted(boolean mute) {
	}

	@Override
	public void setVolume(double volume) {
	}

	@Override
	public void setCurrentTime(double timePercent) {
		mediaWrapper.setCurrentTime(timePercent);
		MediaEvent me = new MediaEvent(MediaEventTypes.ON_TIME_UPDATE, mediaWrapper);
		me.setCurrentTime(timePercent);
		eventsBus.fireEventFromSource(me, mediaWrapper);
	}

	@Override
	public void setSoundFinishedListener(SoundExecutorListener listener) {
		// do nothing - not applicable
	}
}
