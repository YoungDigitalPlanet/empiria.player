package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;

import eu.ydp.empiria.player.client.module.slideshow.SlideshowPlayerModulePresenter;
import eu.ydp.empiria.player.client.module.slideshow.SlideshowPresenter;

public class SlideshowGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(SlideshowPresenter.class).to(SlideshowPlayerModulePresenter.class);
	}

}
