package eu.ydp.empiria.player.client.media.texttrack;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.media.Audio;
import eu.ydp.empiria.player.client.media.Video;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Objekt reprezentujacy sciezke tekstowa powiazana z {@link Video} lub {@link Audio}
 *
 * @author plelakowski
 */
public class TextTrack {
    private final TextTrackKind kind;
    private final SortedSet<TextTrackCue> cues = new TreeSet<TextTrackCue>();
    private TextTrackCue activeCue = null;
    protected EventsBus eventsBus;
    private final Object eventBusSource;
    private final PageScopeFactory pageScopeFactory;

    @Inject
    public TextTrack(@Assisted TextTrackKind kind, @Assisted Object eventBusSource, EventsBus eventsBus, PageScopeFactory pageScopeFactory) {
        this.kind = kind;
        this.eventBusSource = eventBusSource;
        this.eventsBus = eventsBus;
        this.pageScopeFactory = pageScopeFactory;
    }

    public TextTrackKind getKind() {
        return kind;
    }

    public void addCue(TextTrackCue textTrackCue) {
        cues.add(textTrackCue);
        textTrackCue.setTextTrack(this);
    }

    @SuppressWarnings("PMD")
    public void setCurrentTime(double time) {
        for (TextTrackCue cue : cues) {
            if (!cue.equals(activeCue)) {
                if (cue.getStartTime() <= time && cue.getEndTime() > time) {
                    MediaEvent event = new MediaEvent(MediaEventTypes.TEXT_TRACK_UPDATE, eventBusSource);
                    event.setTextTrackCue(cue);
                    eventsBus.fireAsyncEventFromSource(event, eventBusSource, pageScopeFactory.getCurrentPageScope());
                    activeCue = cue;
                }
            }
        }
    }
}
