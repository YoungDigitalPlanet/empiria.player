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
