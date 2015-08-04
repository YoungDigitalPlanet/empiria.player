package eu.ydp.empiria.player.client.gin.module;


import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import eu.ydp.empiria.player.client.gin.factory.MediaFactory;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenView;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenViewImpl;

public class MediaGinModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(VideoFullScreenView.class).to(VideoFullScreenViewImpl.class);
        install(new GinFactoryModuleBuilder().build(MediaFactory.class));
    }
}
