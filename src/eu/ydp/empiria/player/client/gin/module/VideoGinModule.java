package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;

import eu.ydp.empiria.player.client.gin.factory.VideoModuleFactory;
import eu.ydp.empiria.player.client.module.video.view.SourceElementWrapperProvider;
import eu.ydp.empiria.player.client.module.video.view.SourceElementWrapper;
import eu.ydp.empiria.player.client.module.video.view.VideoElementWrapperProvider;
import eu.ydp.empiria.player.client.module.video.view.VideoElementWrapper;
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
		bind(SourceElementWrapper.class).toProvider(SourceElementWrapperProvider.class);
		bind(VideoElementWrapper.class).toProvider(VideoElementWrapperProvider.class);
		bind(VideoPlayerNative.class).to(VideoPlayerNativeImpl.class);
		install(new GinFactoryModuleBuilder().implement(VideoPlayer.class, VideoPlayerImpl.class).build(VideoModuleFactory.class));
	}
}
