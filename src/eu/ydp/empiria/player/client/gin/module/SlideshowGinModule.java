package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import eu.ydp.empiria.player.client.module.slideshow.presenter.*;
import eu.ydp.empiria.player.client.module.slideshow.view.buttons.*;
import eu.ydp.empiria.player.client.module.slideshow.view.player.*;
import eu.ydp.empiria.player.client.module.slideshow.view.slide.*;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListModuleStructure;

public class SlideshowGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(SlideshowPlayerView.class).to(SlideshowPlayerViewImpl.class);
		bind(SlideView.class).to(SlideViewImpl.class);
		// bind(SlideshowController.class).to(SlideshowController.class);
		bind(SlideshowButtonsView.class).to(SlideshowButtonsViewImpl.class);
		bind(ButtonsPresenter.class).to(SlideshowButtonsPresenter.class);
		bind(SourceListModuleStructure.class);
	}
}
