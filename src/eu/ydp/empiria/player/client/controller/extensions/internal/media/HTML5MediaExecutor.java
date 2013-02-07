package eu.ydp.empiria.player.client.controller.extensions.internal.media;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.media.client.MediaBase;

import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SoundExecutorListener;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEvent;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventHandler;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType;
import eu.ydp.empiria.player.client.media.Video;
import eu.ydp.empiria.player.client.media.texttrack.TextTrack;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackCue;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.HTML5MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class HTML5MediaExecutor implements HTML5MediaEventHandler, MediaExecutor<MediaBase> {

	private MediaBase media;
	private MediaWrapper<MediaBase> mediaDescriptor;
	protected SoundExecutorListener listener;
	private BaseMediaConfiguration baseMediaConfiguration;
	protected EventsBus eventsBus = PlayerGinjectorFactory.getPlayerGinjector().getEventsBus();
	private final Set<HandlerRegistration> allEventsRegistration = new HashSet<HandlerRegistration>();

	@Override
	public void init() {
		if (media != null) {
			// bindujemy evnty
			for(HandlerRegistration registration : allEventsRegistration){
				registration.removeHandler();
			}
			allEventsRegistration.clear();
			for (HTML5MediaEventsType event : HTML5MediaEventsType.values()) {
				allEventsRegistration.add(media.addBitlessDomHandler(this, HTML5MediaEvent.getType(event)));
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

				if (baseMediaConfiguration.getNarrationText().trim().length() > 0) {
					TextTrack textTrack = ((Video) media).addTrack(TextTrackKind.SUBTITLES);
					//FIXME do poprawy gdy narrationScript bedzie zawieral informacje o czasie wyswietlania
					//zamiast Double.MAX_VALUE tu powinna sie znalezc wartosc czasowa okreslajaca
					//kiedy napis znika poniewaz w tej chwili narrationScript nie posiada takiej informacji
					//przypisuje najwieksza dostepna wartosc
					textTrack.addCue(new TextTrackCue(Document.get().createUniqueId(), 0, Double.MAX_VALUE, baseMediaConfiguration.getNarrationText()));
				}
			}
			for (Map.Entry<String, String> entry : baseMediaConfiguration.getSources().entrySet()) {
				media.addSource(entry.getKey(), entry.getValue());
			}
			media.setPreload(MediaElement.PRELOAD_METADATA);
			media.setControls(!baseMediaConfiguration.isTemplate());
		}
	}

	@Override
	public void setMediaWrapper(MediaWrapper<MediaBase> descriptor) {
		this.mediaDescriptor = descriptor;
		media = descriptor.getMediaObject();
		if(mediaDescriptor instanceof HTML5MediaWrapper){
			((HTML5MediaWrapper) mediaDescriptor).setMediaExecutor(this);
		}
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
	public MediaWrapper<MediaBase> getMediaWrapper() {
		return mediaDescriptor;
	}

	@Override
	public void play(String src) {
		media.play();
	}
	
	public void setMedia(MediaBase media) {
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

}
