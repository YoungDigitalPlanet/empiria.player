package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.gin.factory.VideoModuleFactory;
import eu.ydp.empiria.player.client.module.video.presenter.VideoPlayerBuilder;
import eu.ydp.empiria.player.client.module.video.presenter.VideoPlayerReattacher;
import eu.ydp.empiria.player.client.module.video.presenter.VideoPresenter;
import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.structure.VideoBeanProvider;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayerNative;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.empiria.player.client.module.video.view.VideoViewImpl;
import eu.ydp.empiria.player.client.module.video.wrappers.SourceElementWrapper;
import eu.ydp.empiria.player.client.module.video.wrappers.SourceElementWrapperProvider;
import eu.ydp.empiria.player.client.module.video.wrappers.VideoElementWrapper;
import eu.ydp.empiria.player.client.module.video.wrappers.VideoElementWrapperProvider;
import eu.ydp.empiria.player.client.module.video.wrappers.poster.BundleDefaultPosterUriProvider;
import eu.ydp.empiria.player.client.module.video.wrappers.poster.DefaultPosterUriProvider;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class VideoGinModule extends EmpiriaModule {

    @Override
    protected void configure() {
        bind(VideoView.class).to(VideoViewImpl.class);
        bind(SourceElementWrapper.class).toProvider(SourceElementWrapperProvider.class);
        bind(VideoElementWrapper.class).toProvider(VideoElementWrapperProvider.class);
        bind(VideoPlayerNative.class).to(VideoPlayerNativeImpl.class);
        bind(DefaultPosterUriProvider.class).to(BundleDefaultPosterUriProvider.class);
        bind(VideoBean.class).toProvider(VideoBeanProvider.class);

        bindModuleScoped(VideoBean.class, new TypeLiteral<ModuleScopedProvider<VideoBean>>() {
        });
        bindModuleScoped(VideoPresenter.class, new TypeLiteral<ModuleScopedProvider<VideoPresenter>>() {
        });
        bindModuleScoped(VideoView.class, new TypeLiteral<ModuleScopedProvider<VideoView>>() {
        });
        bindModuleScoped(VideoPlayerBuilder.class, new TypeLiteral<ModuleScopedProvider<VideoPlayerBuilder>>() {
        });
        bindModuleScoped(VideoPlayerReattacher.class, new TypeLiteral<ModuleScopedProvider<VideoPlayerReattacher>>() {
        });

        install(new GinFactoryModuleBuilder().build(VideoModuleFactory.class));
    }
}
