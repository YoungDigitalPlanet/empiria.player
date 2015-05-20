package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.lightbox.FullScreenModeProvider;
import eu.ydp.empiria.player.client.module.img.picture.player.presenter.PicturePlayerFullscreenController;
import eu.ydp.empiria.player.client.module.img.picture.player.view.*;

public class PicturePlayerModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(PicturePlayerView.class).to(PicturePlayerViewImpl.class);
		bind(FullScreenModeProvider.class).in(Singleton.class);
		bind(PicturePlayerFullscreenController.class).in(Singleton.class);
	}
}
