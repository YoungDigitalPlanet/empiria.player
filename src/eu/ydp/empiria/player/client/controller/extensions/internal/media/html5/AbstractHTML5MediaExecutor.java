package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.media.client.MediaBase;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SoundExecutorListener;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEvent;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventHandler;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.AbstractHTML5MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public abstract class AbstractHTML5MediaExecutor<H extends MediaBase> implements HTML5MediaEventHandler, MediaExecutor<MediaBase> {

	private H media;
	private MediaWrapper<MediaBase> mediaDescriptor;
	protected SoundExecutorListener listener;
	private BaseMediaConfiguration baseMediaConfiguration;

	@Inject
	protected EventsBus eventsBus;

	private final Set<HandlerRegistration> allEventsRegistration = new HashSet<HandlerRegistration>();

	@Override
	public void init() {
		if (media != null) {
			// bindujemy evnty
			removeMediaEventHandlers();
			registerMediaEventHandlers();
			configureMediaBase();
			initExecutor();
		}
	}

	private void configureMediaBase() {
		for (Map.Entry<String, String> entry : baseMediaConfiguration.getSources().entrySet()) {
			media.addSource(entry.getKey(), entry.getValue());
		}
		media.setPreload(MediaElement.PRELOAD_METADATA);
		media.setControls(!baseMediaConfiguration.isTemplate());
	}

	private void registerMediaEventHandlers() {
		for (HTML5MediaEventsType event : HTML5MediaEventsType.values()) {
			allEventsRegistration.add(media.addBitlessDomHandler(this, HTML5MediaEvent.getType(event)));
		}
	}

	private void removeMediaEventHandlers() {
		for(HandlerRegistration registration : allEventsRegistration){
			registration.removeHandler();
		}
		allEventsRegistration.clear();
	}

	@Override
	public void setMediaWrapper(MediaWrapper<MediaBase> descriptor) {
		this.mediaDescriptor = descriptor;
		media = (H) descriptor.getMediaObject();
		if(mediaDescriptor instanceof AbstractHTML5MediaWrapper){
			((AbstractHTML5MediaWrapper) mediaDescriptor).setMediaExecutor(this);
		}
	}

	@Override
	public MediaWrapper<MediaBase> getMediaWrapper() {
		return mediaDescriptor;
	}

	protected H getMedia() {
		return media;
	}

	@Override
	public void setBaseMediaConfiguration(BaseMediaConfiguration baseMediaConfiguration) {// NOPMD
		this.baseMediaConfiguration = baseMediaConfiguration;
	}

	@Override
	public BaseMediaConfiguration getBaseMediaConfiguration() {
		return baseMediaConfiguration;
	}

	@Override
	public void onEvent(HTML5MediaEvent event) {// NOPMD
		switch (event.getType()) {
			case canplay:
				eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.CAN_PLAY, mediaDescriptor), mediaDescriptor);
				break;
			case suspend:
				eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.SUSPEND, mediaDescriptor), mediaDescriptor);
				break;
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
			default:
				break;
		}
	}


	@Override
	public void play(String src) {
		media.play();
	}

	public void setMedia(H media) {
		this.media = media;
	}

	@Override
	public void play() {
		media.play();
	}

	@Override
	public void stop() {
		try {
			media.pause();
			media.setCurrentTime(0);
		} catch (Exception e) {// NOPMD
			// chrome podczas przeladowania strony lekcji
			// generowal bledy
		}
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
		if ( !Double.isNaN(media.getDuration()) ) {
			media.setCurrentTime(time);
		}
	}

	@Override
	public void setSoundFinishedListener(SoundExecutorListener listener) {
		this.listener = listener;
	}

	public abstract void initExecutor();

}
