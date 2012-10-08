package eu.ydp.empiria.player.client.media.texttrack;

import java.util.SortedSet;
import java.util.TreeSet;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.media.Audio;
import eu.ydp.empiria.player.client.media.Video;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

/**
 * Objekt reprezentujacy sciezke tekstowa powiazana z {@link Video} lub {@link Audio}
 * @author plelakowski
 *
 */
public class TextTrack {
	private final TextTrackKind kind;
	private final SortedSet<TextTrackCue> cues = new TreeSet<TextTrackCue>();
	private TextTrackCue activeCue = null;
	protected final EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	private final Object eventBusSource;
	/**
	 * @param kind typ sciezki
	 * @param eventBusSource
	 */
	public TextTrack(TextTrackKind kind, Object eventBusSource) {
		this.kind = kind;
		this.eventBusSource = eventBusSource;
	}

	public TextTrackKind getKind() {
		return kind;
	}

	public void addCue(TextTrackCue textTrackCue){
		cues.add(textTrackCue);
		textTrackCue.setTextTrack(this);
	}

	@SuppressWarnings("PMD")
	public void setCurrentTime(double time){
		for(TextTrackCue cue : cues){
			if(!cue.equals(activeCue)){
				if(cue.getStartTime() <= time && cue.getEndTime() > time){
					MediaEvent event = new MediaEvent(MediaEventTypes.TEXT_TRACK_UPDATE, eventBusSource);
					event.setTextTrackCue(cue);
					eventsBus.fireAsyncEventFromSource(event, eventBusSource, new CurrentPageScope());
					activeCue = cue;
				}
			}
		}
	}
}
