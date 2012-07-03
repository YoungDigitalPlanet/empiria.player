package eu.ydp.empiria.player.client.controller.extensions.internal.media;

import java.util.Map;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.media.client.Video;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SoundExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SoundExecutorListener;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEvent;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventHandler;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class HTML5MediaExecutor implements HTML5MediaEventHandler, SoundExecutor<MediaBase> {

	private MediaBase media;
	private MediaWrapper<MediaBase> mediaDescriptor;
	protected SoundExecutorListener listener;
	private BaseMediaConfiguration baseMediaConfiguration;
	protected EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	@Override
	public void init() {
		if (media != null) {
			// bindujemy evnty
			for (HTML5MediaEventsType event : HTML5MediaEventsType.values()) {
				media.addBitlessDomHandler(this, HTML5MediaEvent.getType(event));
			}
			if (baseMediaConfiguration != null && media instanceof Video) {
				if (baseMediaConfiguration.getPoster() != null && baseMediaConfiguration.getPoster().trim().length() > 0) {
					((Video) media).setPoster(baseMediaConfiguration.getPoster());
				}
				if (baseMediaConfiguration.getWidth() > 0) {
					((Video) media).setWidth(baseMediaConfiguration.getWidth() + "px");
				}
				if (baseMediaConfiguration.getHeight() > 0) {
					((Video) media).setHeight(baseMediaConfiguration.getHeight() + "px");
				}
			}
			for (Map.Entry<String, String> entry : baseMediaConfiguration.getSources().entrySet()) {
				media.addSource(entry.getKey(), entry.getValue());
			}
			media.setPreload(MediaElement.PRELOAD_METADATA);
		}
	}

	@Override
	public void setMediaWrapper(MediaWrapper<MediaBase> descriptor) {
		this.mediaDescriptor = descriptor;
		media = descriptor.getMediaObject();
	}

	@Override
	public void setBaseMediaConfiguration(BaseMediaConfiguration baseMediaConfiguration) {
		this.baseMediaConfiguration = baseMediaConfiguration;
	}

	@Override
	public void onEvent(HTML5MediaEvent event) {
		switch (event.getType()) {
		case durationchange:
			eventsBus.fireAsyncEventFromSource(new MediaEvent(MediaEventTypes.ON_DURATION_CHANGE, mediaDescriptor), mediaDescriptor);
			break;
		case ended:
			eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_END, mediaDescriptor), mediaDescriptor);
			if (listener != null) {
				listener.onSoundFinished();
			}
			break;
		case error:
			eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_ERROR, mediaDescriptor), mediaDescriptor);
			break;
		case pause:
			eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_PAUSE, mediaDescriptor), mediaDescriptor);
			break;
		case timeupdate:
			eventsBus.fireAsyncEventFromSource(new MediaEvent(MediaEventTypes.ON_TIME_UPDATE, mediaDescriptor), mediaDescriptor);
			break;
		case volumechange:
			eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_VOLUME_CHANGE, mediaDescriptor), mediaDescriptor);
			break;
		case play:
			eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_PLAY, mediaDescriptor), mediaDescriptor);
			if (listener != null) {
				listener.onPlay();
			}
			break;
		}
	}

	@Override
	public MediaWrapper<MediaBase> getMediaWrapper() {
		return mediaDescriptor;
	}

	@Override
	public void play(String src) {
		media.play();
	}

	@Override
	public void play() {
		media.play();
	}

	@Override
	public void stop() {
		media.pause();
		media.setCurrentTime(0);
	}

	@Override
	public void pause() {
		media.pause();
	}

	@Override
	public void setMuted(boolean muted) {
		media.setMuted(muted);

	}

	@Override
	public void setVolume(double volume) {
		media.setVolume(volume);
	}

	@Override
	public void setCurrentTime(double time) {
		media.setCurrentTime(time);

	}

	@Override
	public void setSoundFinishedListener(SoundExecutorListener listener) {
		this.listener = listener;
	}

}
