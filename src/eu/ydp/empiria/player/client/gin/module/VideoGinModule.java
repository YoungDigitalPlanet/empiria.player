package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;

import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.empiria.player.client.module.video.view.VideoViewImpl;

public class VideoGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(VideoView.class).to(VideoViewImpl.class);
	}
}
