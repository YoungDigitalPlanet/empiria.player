package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.slideshow.SlideshowMediaHandler;
import eu.ydp.empiria.player.client.module.slideshow.SlideshowPlayerModule;
import eu.ydp.empiria.player.client.module.slideshow.SlideshowPresenter;

public interface SlideshowPlayerModuleFactory {
	SlideshowMediaHandler getSlideshowMediaHandler(SlideshowPlayerModule slideshowPlayerModule, SlideshowPresenter slideshowPresenter);
}
