package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.slideshow.SlideshowTemplateInterpreter;
import eu.ydp.empiria.player.client.module.slideshow.view.buttons.SlideshowButtonsView;
import eu.ydp.empiria.player.client.module.slideshow.view.buttons.SlideshowButtonsViewImpl;
import eu.ydp.empiria.player.client.module.slideshow.view.pager.SlideshowPagerView;
import eu.ydp.empiria.player.client.module.slideshow.view.pager.SlideshowPagerViewImpl;
import eu.ydp.empiria.player.client.module.slideshow.view.pager.button.SlideshowPagerButtonView;
import eu.ydp.empiria.player.client.module.slideshow.view.pager.button.SlideshowPagerButtonViewImpl;
import eu.ydp.empiria.player.client.module.slideshow.view.player.SlideshowPlayerView;
import eu.ydp.empiria.player.client.module.slideshow.view.player.SlideshowPlayerViewImpl;
import eu.ydp.empiria.player.client.module.slideshow.view.slide.SlideView;
import eu.ydp.empiria.player.client.module.slideshow.view.slide.SlideViewImpl;
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
