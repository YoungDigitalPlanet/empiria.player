package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import eu.ydp.empiria.player.client.module.img.picture.player.view.PicturePlayerView;
import eu.ydp.empiria.player.client.module.img.picture.player.view.PicturePlayerViewImpl;

public class PicturePlayerGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(PicturePlayerView.class).to(PicturePlayerViewImpl.class);
    }
}
