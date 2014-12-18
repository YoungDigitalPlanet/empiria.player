package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.slideshow.slides.*;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideshowJAXBParser;
import eu.ydp.empiria.player.client.module.slideshow.view.buttons.*;
import eu.ydp.empiria.player.client.module.slideshow.view.player.*;
import eu.ydp.empiria.player.client.module.slideshow.view.slide.*;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListModuleStructure;

public class SlideshowGinModule extends AbstractGinModule {

	public static class SlideshowJAXBParserProvider implements Provider<SlideshowJAXBParser> {
		@Override
		public SlideshowJAXBParser get() {
			return GWT.create(SlideshowJAXBParser.class);
		};
	}

	@Override
	protected void configure() {
		bind(SlideshowPlayerView.class).to(SlideshowPlayerViewImpl.class);
		bind(SlideView.class).to(SlideViewImpl.class);
		bind(SlidesController.class).to(SlidesControllerImpl.class);
		bind(SlideshowButtonsView.class).to(SlideshowButtonsViewImpl.class);
		bind(SourceListModuleStructure.class);
		bind(SlideshowJAXBParser.class).toProvider(SlideshowJAXBParserProvider.class);
	}
}
