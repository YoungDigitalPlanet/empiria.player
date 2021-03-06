/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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

public class VideoGinModule extends BaseGinModule {

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
