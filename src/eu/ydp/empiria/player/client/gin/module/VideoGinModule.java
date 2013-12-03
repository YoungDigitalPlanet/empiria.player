package eu.ydp.empiria.player.client.gin.module;

import javax.inject.Singleton;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;

import eu.ydp.empiria.player.client.gin.factory.VideoModuleFactory;
import eu.ydp.empiria.player.client.module.video.hack.PageChangePauseHandlerAdder;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayerNative;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.empiria.player.client.module.video.view.VideoViewImpl;
import eu.ydp.empiria.player.client.module.video.wrappers.SourceElementWrapper;
import eu.ydp.empiria.player.client.module.video.wrappers.SourceElementWrapperProvider;
import eu.ydp.empiria.player.client.module.video.wrappers.VideoElementWrapper;
import eu.ydp.empiria.player.client.module.video.wrappers.VideoElementWrapperProvider;

public class VideoGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(VideoView.class).to(VideoViewImpl.class);
		bind(SourceElementWrapper.class).toProvider(SourceElementWrapperProvider.class);
		bind(VideoElementWrapper.class).toProvider(VideoElementWrapperProvider.class);
		bind(VideoPlayerNative.class).to(VideoPlayerNativeImpl.class);
		bind(PageChangePauseHandlerAdder.class).in(Singleton.class);
		install(new GinFactoryModuleBuilder().build(VideoModuleFactory.class));
	}
}
