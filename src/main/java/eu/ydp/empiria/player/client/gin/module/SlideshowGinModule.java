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

import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlidePresenter;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlideshowButtonsPresenter;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlideshowPlayerPresenter;
import eu.ydp.empiria.player.client.module.slideshow.slides.SlidesSwitcher;
import eu.ydp.empiria.player.client.module.slideshow.slides.SlideshowController;
import eu.ydp.empiria.player.client.module.slideshow.sound.SlideshowSoundController;
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
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class SlideshowGinModule extends BaseGinModule {

    @Override
    protected void configure() {
        bind(SlideshowPlayerView.class).to(SlideshowPlayerViewImpl.class);
        bind(SlideView.class).to(SlideViewImpl.class);
        bind(SlideshowButtonsView.class).to(SlideshowButtonsViewImpl.class);
        bind(SlideshowPagerView.class).to(SlideshowPagerViewImpl.class);
        bind(SlideshowPagerButtonView.class).to(SlideshowPagerButtonViewImpl.class);

        bindModuleScoped(SlideshowButtonsPresenter.class, new TypeLiteral<ModuleScopedProvider<SlideshowButtonsPresenter>>() {
        });
        bindModuleScoped(SlideshowPlayerPresenter.class, new TypeLiteral<ModuleScopedProvider<SlideshowPlayerPresenter>>() {
        });
        bindModuleScoped(SlideshowPlayerView.class, new TypeLiteral<ModuleScopedProvider<SlideshowPlayerView>>() {
        });
        bindModuleScoped(SlideshowButtonsView.class, new TypeLiteral<ModuleScopedProvider<SlideshowButtonsView>>() {
        });
        bindModuleScoped(SlideshowController.class, new TypeLiteral<ModuleScopedProvider<SlideshowController>>() {
        });
        bindModuleScoped(SlidesSwitcher.class, new TypeLiteral<ModuleScopedProvider<SlidesSwitcher>>() {
        });
        bindModuleScoped(SlidePresenter.class, new TypeLiteral<ModuleScopedProvider<SlidePresenter>>() {
        });
        bindModuleScoped(SlideView.class, new TypeLiteral<ModuleScopedProvider<SlideView>>() {
        });
        bindModuleScoped(SlideshowSoundController.class, new TypeLiteral<ModuleScopedProvider<SlideshowSoundController>>() {
        });
    }
}
