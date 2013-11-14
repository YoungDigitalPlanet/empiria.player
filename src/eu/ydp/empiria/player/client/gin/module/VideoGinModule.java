package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.dom.client.SourceElement;
import com.google.gwt.inject.client.AbstractGinModule;

import eu.ydp.empiria.player.client.module.video.view.SourceElementProvider;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayerImpl;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayerNative;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.empiria.player.client.module.video.view.VideoViewImpl;

public class VideoGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(VideoView.class).to(VideoViewImpl.class);
		bind(SourceElement.class).toProvider(SourceElementProvider.class);
		bind(VideoPlayerNative.class).to(VideoPlayerNativeImpl.class);
		bind(VideoPlayer.class).to(VideoPlayerImpl.class);
	}
}
