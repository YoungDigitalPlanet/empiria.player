package eu.ydp.empiria.player.client.module.videojs.gin;

import com.google.gwt.inject.client.AbstractGinModule;

import eu.ydp.empiria.player.client.module.videojs.view.VideoJsView;
import eu.ydp.empiria.player.client.module.videojs.view.VideoJsViewImpl;

public class VideoJsGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(VideoJsView.class).to(VideoJsViewImpl.class);
	}
}
