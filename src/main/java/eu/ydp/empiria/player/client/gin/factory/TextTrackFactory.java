package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.media.texttrack.TextTrack;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;

public interface TextTrackFactory {
    public TextTrack getTextTrack(TextTrackKind kind, Object eventBusSource);
}
