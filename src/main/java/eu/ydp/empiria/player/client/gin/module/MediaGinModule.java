package eu.ydp.empiria.player.client.gin.module;


import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.ExternalFullscreenVideoConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.FullscreenVideoConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.ExternalMediaEngine;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.JsMediaConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.MediaConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.MediaConnectorListener;
import eu.ydp.empiria.player.client.gin.factory.*;
import eu.ydp.empiria.player.client.media.texttrack.VideoTextTrackElementPresenter;
import eu.ydp.empiria.player.client.media.texttrack.VideoTextTrackElementView;
import eu.ydp.empiria.player.client.module.img.explorable.view.ExplorableImgContentView;
import eu.ydp.empiria.player.client.module.img.explorable.view.ExplorableImgContentViewImpl;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactory;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactoryImpl;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenView;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenViewImpl;

public class MediaGinModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(VideoFullScreenView.class).to(VideoFullScreenViewImpl.class);
        bind(FullscreenVideoConnector.class).to(ExternalFullscreenVideoConnector.class);
        bind(MediaControllerFactory.class).to(MediaControllerFactoryImpl.class);
        bind(MediaConnector.class).to(JsMediaConnector.class);
        bind(MediaConnectorListener.class).to(ExternalMediaEngine.class);
        bind(VideoTextTrackElementPresenter.class).to(VideoTextTrackElementView.class);
        bind(ExplorableImgContentView.class).to(ExplorableImgContentViewImpl.class);

        install(new GinFactoryModuleBuilder().build(VideoTextTrackElementFactory.class));
        install(new GinFactoryModuleBuilder().build(MediaWrapperFactory.class));
        install(new GinFactoryModuleBuilder().build(MediaWrappersPairFactory.class));
        install(new GinFactoryModuleBuilder().build(TextTrackFactory.class));
        install(new GinFactoryModuleBuilder().build(TemplateParserFactory.class));
        install(new GinFactoryModuleBuilder().build(MediaFactory.class));
    }
}
