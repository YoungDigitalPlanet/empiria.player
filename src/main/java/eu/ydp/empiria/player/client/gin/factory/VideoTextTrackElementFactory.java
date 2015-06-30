package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;
import eu.ydp.empiria.player.client.media.texttrack.VideoTextTrackElement;

public interface VideoTextTrackElementFactory {
    public VideoTextTrackElement getVideoTextTrackElement(TextTrackKind kind);
}
