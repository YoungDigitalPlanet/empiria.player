package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.LightBoxProvider;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.lightbox2.LightBox2;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.magnific.popup.MagnificPopup;
import eu.ydp.empiria.player.client.module.img.picture.player.presenter.PicturePlayerFullscreenController;
import eu.ydp.empiria.player.client.module.img.picture.player.view.PicturePlayerView;
import eu.ydp.empiria.player.client.module.img.picture.player.view.PicturePlayerViewImpl;

public class PicturePlayerModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(PicturePlayerView.class).to(PicturePlayerViewImpl.class);
        bind(LightBoxProvider.class).in(Singleton.class);
        bind(LightBox2.class).in(Singleton.class);
        bind(MagnificPopup.class).in(Singleton.class);
        bind(PicturePlayerFullscreenController.class).in(Singleton.class);
    }
}
