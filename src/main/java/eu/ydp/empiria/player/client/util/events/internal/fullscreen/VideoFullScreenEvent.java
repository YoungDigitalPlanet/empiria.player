package eu.ydp.empiria.player.client.util.events.internal.fullscreen;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public interface VideoFullScreenEvent extends FullScreenEvent {
	public MediaWrapper<?> getMediaWrapper();
}
