package eu.ydp.empiria.player.client.controller.extensions.internal.sound.factory;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.MediaWrapperFactory;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.media.html5.AbstractHTML5MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.Media;

public class HTML5MediaWrapperFactory {
	@Inject
	MediaWrapperFactory wrapperFactory;

	public AbstractHTML5MediaWrapper createMediaWrapper(Media media, MediaType mediaType) {
		AbstractHTML5MediaWrapper mediaWrapper;
		if (mediaType == MediaType.VIDEO) {
			mediaWrapper = wrapperFactory.getHtml5VideoMediaWrapper(media);
		} else  {
			mediaWrapper = wrapperFactory.getHtml5AudioMediaWrapper(media);
		}
		return mediaWrapper;
	}
}
