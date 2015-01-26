package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.slideshow.SlideshowTemplateInterpreter;
import eu.ydp.empiria.player.client.module.slideshow.view.buttons.*;
import eu.ydp.empiria.player.client.module.slideshow.view.pager.*;
import eu.ydp.empiria.player.client.module.slideshow.view.pager.button.*;
import eu.ydp.empiria.player.client.module.slideshow.view.player.*;
import eu.ydp.empiria.player.client.module.slideshow.view.slide.*;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListModuleStructure;

public class SlideshowGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(SlideshowPlayerView.class).to(SlideshowPlayerViewImpl.class);
		bind(SlideView.class).to(SlideViewImpl.class);
		bind(SlideshowButtonsView.class).to(SlideshowButtonsViewImpl.class);
		bind(SourceListModuleStructure.class);
		bind(SlideshowPagerView.class).to(SlideshowPagerViewImpl.class);
		bind(SlideshowPagerButtonView.class).to(SlideshowPagerButtonViewImpl.class);
		bind(SlideshowTemplateInterpreter.class).in(Singleton.class);
	}
}
