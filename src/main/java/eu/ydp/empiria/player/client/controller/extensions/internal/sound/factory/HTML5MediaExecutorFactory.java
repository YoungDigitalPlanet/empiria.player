package eu.ydp.empiria.player.client.controller.extensions.internal.sound.factory;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.AbstractHTML5MediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.HTML5AudioMediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.HTML5VideoMediaExecutor;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.media.html5.AbstractHTML5MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.Media;

public class HTML5MediaExecutorFactory {

	@Inject
	private HTML5MediaWrapperFactory mediaWrapperFactory;

	@Inject
	private Provider<HTML5VideoMediaExecutor> videoExecutorProvider;

	@Inject
	private Provider<HTML5AudioMediaExecutor> audioExecutorProvider;

	public AbstractHTML5MediaExecutor createMediaExecutor(Media media, MediaType mediaType) {
		AbstractHTML5MediaExecutor executor = null;
		if (media != null) {
			AbstractHTML5MediaWrapper html5MediaWrapper = mediaWrapperFactory.createMediaWrapper(media, mediaType);
			executor = getMediaExecutor(mediaType);
			executor.setMediaWrapper(html5MediaWrapper);
			media.setEventBusSourceObject(executor.getMediaWrapper());
		}
		return executor;
	}

	private AbstractHTML5MediaExecutor getMediaExecutor(MediaType mediaType) {
		return (mediaType == MediaType.VIDEO) ? videoExecutorProvider.get() : audioExecutorProvider.get();
	}

}
