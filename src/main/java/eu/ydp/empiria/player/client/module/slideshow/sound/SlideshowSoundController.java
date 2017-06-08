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

package eu.ydp.empiria.player.client.module.slideshow.sound;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import eu.ydp.empiria.player.client.module.slideshow.SlideEndHandler;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

import java.util.Collection;

public class SlideshowSoundController implements MediaEventHandler {

    private final MediaWrapperController mediaWrapperController;
    private final SlideshowSounds slideshowSounds;
    private SlideEndHandler slideEndHandler;

    @Inject
    public SlideshowSoundController(EventsBus eventsBus, MediaWrapperController mediaWrapperController, SlideshowSounds slideshowSounds) {
        this.mediaWrapperController = mediaWrapperController;
        this.slideshowSounds = slideshowSounds;

        eventsBus.addHandler(MediaEvent.getType(MediaEventTypes.ON_END), this);
    }

    public void initSound(String audiopath) {
        slideshowSounds.initSound(audiopath);
    }

    public void playSound(String audiopath, SlideEndHandler slideEndhandler) {
        this.slideEndHandler = slideEndhandler;
        MediaWrapper<Widget> currentSound = slideshowSounds.getSound(audiopath);
        mediaWrapperController.play(currentSound);
    }

    public void pauseSound(String audiopath) {
        MediaWrapper<Widget> currentSound = slideshowSounds.getSound(audiopath);
        mediaWrapperController.pause(currentSound);
    }

    public void stopAllSounds() {
        Collection<MediaWrapper<Widget>> sounds = slideshowSounds.getAllSounds();
        for (MediaWrapper<Widget> sound : sounds) {
            mediaWrapperController.stop(sound);
        }
    }

    @Override
    public void onMediaEvent(MediaEvent event) {
        MediaWrapper<Widget> onEndEventSource = (MediaWrapper<Widget>) event.getMediaWrapper();

        if (slideshowSounds.containsWrapper(onEndEventSource)) {
            slideEndHandler.onEnd();
        }
    }
}
