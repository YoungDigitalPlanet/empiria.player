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

package eu.ydp.empiria.player.client.module.slideshow.slides;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.slideshow.SlideEndHandler;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlidePresenter;
import eu.ydp.empiria.player.client.module.slideshow.sound.SlideshowSoundController;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.List;

public class SlidesSwitcher {

    private List<SlideBean> slides;
    private int currSlideIndex;
    private final SlidePresenter presenter;
    private final SlideshowSoundController slideshowSoundController;
    private SlideEndHandler slideEnd;

    @Inject
    public SlidesSwitcher(@ModuleScoped SlidePresenter presenter, @ModuleScoped SlideshowSoundController slideshowSoundController) {
        this.slideshowSoundController = slideshowSoundController;
        this.presenter = presenter;
    }

    public void init(List<SlideBean> slides, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        this.slides = slides;
        presenter.setInlineBodyGenerator(inlineBodyGeneratorSocket);
    }

    public void showNextSlide() {
        if (canSwitchToNextSlide()) {
            currSlideIndex++;
        }

        showCurrentSlide();
    }

    public void showPreviousSlide() {
        if (canSwitchToPreviousSlide()) {
            currSlideIndex--;
        }

        showCurrentSlide();
    }

    public boolean canSwitchToNextSlide() {
        int nextSlide = currSlideIndex + 1;
        return nextSlide < slides.size();
    }

    public boolean canSwitchToPreviousSlide() {
        return currSlideIndex > 0;
    }

    public void pauseSlide() {
        if (hasCurrentSlideAudio()) {
            String audiopath = getCurrentAudioSource();
            slideshowSoundController.pauseSound(audiopath);
        }
    }

    public void stopAndPlaySlide() {
        slideshowSoundController.stopAllSounds();
        playSlide();
    }

    public void playSlide() {
        if (hasCurrentSlideAudio()) {
            String audiopath = getCurrentAudioSource();
            slideshowSoundController.playSound(audiopath, slideEnd);
        }
    }

    public void showSlide(int indexToShow) {
        if (indexToShow < slides.size()) {
            currSlideIndex = indexToShow;
            showCurrentSlide();
        }
    }

    public int getCurrentSlideIndex() {
        return currSlideIndex;
    }

    public void reset() {
        slideshowSoundController.stopAllSounds();
        currSlideIndex = 0;
        showCurrentSlide();
    }

    private void showCurrentSlide() {
        if (currSlideIndex < slides.size()) {
            SlideBean currentSlide = slides.get(currSlideIndex);
            presenter.replaceViewData(currentSlide);
        }
    }

    public void setSlideEnd(SlideEndHandler slideEnd) {
        this.slideEnd = slideEnd;
    }

    private boolean hasCurrentSlideAudio() {
        SlideBean currentSlide = slides.get(currSlideIndex);
        return currentSlide.hasSound();
    }

    private String getCurrentAudioSource() {
        SlideBean currentSlide = slides.get(currSlideIndex);
        return currentSlide.getSound().getSrc();
    }
}
