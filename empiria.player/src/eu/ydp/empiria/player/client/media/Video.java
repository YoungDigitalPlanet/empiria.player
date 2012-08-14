package eu.ydp.empiria.player.client.media;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.VideoElement;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.media.texttrack.TextTrack;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

public class Video extends com.google.gwt.media.client.Video implements MediaEventHandler {
	private final List<TextTrack> textTracks = new ArrayList<TextTrack>();
	protected final EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	private boolean initialized = false;
	private MediaWrapper<?> eventBusSource = null;

	protected Video(VideoElement element) {
		super(element);
	}

	private final void initHandler() {
		if (!initialized) {
			initialized = true;
			eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), eventBusSource, this, new CurrentPageScope());
		}
	}

	public TextTrack addTrack(TextTrackKind textTrackKind) {
		initHandler();
		TextTrack track = new TextTrack(textTrackKind, eventBusSource);
		textTracks.add(track);
		return track;
	}

	public static Video createIfSupported() {
		com.google.gwt.media.client.Video media = com.google.gwt.media.client.Video.createIfSupported();
		Video video = null;
		if (media != null) {
			video = new Video((VideoElement) media.getMediaElement());
		}
		return video;
	}

	public void setEventBusSourceObject(MediaWrapper<?> object) {
		eventBusSource = object;
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		if (event.getType() == MediaEventTypes.ON_TIME_UPDATE) {
			for (TextTrack track : textTracks) {
				track.setCurrentTime(eventBusSource.getCurrentTime());
			}
		}
	}

}
