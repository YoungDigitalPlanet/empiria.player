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

package eu.ydp.empiria.player.client.module.slideshow.presenter;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.slideshow.slides.SlideshowController;
import eu.ydp.empiria.player.client.module.slideshow.view.buttons.SlideshowButtonsView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class SlideshowButtonsPresenter {

    private final SlideshowButtonsView buttonsView;
    private SlideshowController controller;

    @Inject
    public SlideshowButtonsPresenter(@ModuleScoped SlideshowButtonsView buttonsView) {
        this.buttonsView = buttonsView;
        buttonsView.setPresenter(this);
    }

    public void setSlideshowController(SlideshowController controller) {
        this.controller = controller;
    }

    public void setEnabledNextButton(boolean enabled) {
        buttonsView.setEnabledNextButton(enabled);
    }

    public void setEnabledPreviousButton(boolean enabled) {
        buttonsView.setEnabledPreviousButton(enabled);
    }

    public void setPlayButtonDown(boolean isDown) {
        buttonsView.setPlayButtonDown(isDown);
    }

    public void onNextClick() {
        controller.showNextSlide();
    }

    public void onPreviousClick() {
        controller.showPreviousSlide();
    }

    public void onPlayPauseClick() {
        if (buttonsView.isPlayButtonDown()) {
            controller.playSlideshow();
        } else {
            controller.pauseSlideshow();
        }
    }

    public void onStopClick() {
        controller.stopSlideshow();
    }
}
