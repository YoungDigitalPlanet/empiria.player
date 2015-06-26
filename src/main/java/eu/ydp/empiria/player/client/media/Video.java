package eu.ydp.empiria.player.client.media;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.dom.client.VideoElement;

import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.gin.factory.TextTrackFactory;
import eu.ydp.empiria.player.client.media.texttrack.TextTrack;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

public class Video extends com.google.gwt.media.client.Video implements MediaEventHandler {
	private final List<TextTrack> textTracks = new ArrayList<TextTrack>();
	protected final EventsBus eventsBus = PlayerGinjectorFactory.getPlayerGinjector().getEventsBus();
	protected final TextTrackFactory textTrackFactory = PlayerGinjectorFactory.getPlayerGinjector().getTextTrackFactory();
	private boolean initialized = false;
	private MediaWrapper<?> eventBusSource = null;
	private boolean forcePreload = false;

	protected Video(VideoElement element) {
		super(element);
	}

	private final void initHandler() {
		if (!initialized) {
			initialized = true;
			eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), eventBusSource, this, new CurrentPageScope());
		}
	}

	@Override
	protected void onUnload() {
		pause();
	}

	@Override
	public void setPreload(String preload) {
		super.setPreload(preload);
		if (UserAgentChecker.isMobileUserAgent() && (MediaElement.PRELOAD_AUTO.equals(preload) || MediaElement.PRELOAD_METADATA.equals(preload))) {
			forcePreload = true;
		}
	}

	public TextTrack addTrack(TextTrackKind textTrackKind) {
		initHandler();
		TextTrack track = textTrackFactory.getTextTrack(textTrackKind, eventBusSource);
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

	public MediaWrapper<?> getEventBusSourceObject() {
		return eventBusSource;
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
