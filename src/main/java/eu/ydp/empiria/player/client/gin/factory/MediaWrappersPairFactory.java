package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrappersPair;

public interface MediaWrappersPairFactory {
	public MediaWrappersPair getMediaWrappersPair(@Assisted("default") MediaWrapper<?> defaultMediaWrapper,
			@Assisted("fullscreen") MediaWrapper<?> fullScreanMediaWrapper);
}
