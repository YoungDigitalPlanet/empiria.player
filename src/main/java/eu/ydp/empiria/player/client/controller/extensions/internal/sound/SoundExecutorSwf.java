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

package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import eu.ydp.empiria.gwtflashmedia.client.FlashSoundFactory;
import eu.ydp.empiria.gwtflashmedia.client.event.*;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

public class SoundExecutorSwf extends ExecutorSwf {

    @Override
    public void stop() {
        pause = false;
        if (flashMedia != null)
            flashMedia.stop();
        onSoundStop();

        eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_STOP, getMediaWrapper()), getMediaWrapper());
    }

    private void onSoundStop() {
        playing = false;
        if (soundExecutorListener != null)
            soundExecutorListener.onSoundFinished();
    }

    @Override
    public void init() {
        flashMedia = FlashSoundFactory.createSound(source);
        ((HasFlashMediaHandlers) flashMedia).addFlashMediaPlayHandler(new FlashMediaPlayHandler() {

            @Override
            public void onFlashSoundPlay(FlashMediaPlayEvent event) {
                if (soundExecutorListener != null)
                    soundExecutorListener.onPlay();
                playing = true;

            }
        });

        ((HasFlashMediaHandlers) flashMedia).addFlashMediaCompleteHandler(new FlashMediaCompleteHandler() {
            @Override
            public void onFlashSoundComplete(FlashMediaCompleteEvent event) {
                onSoundStop();
            }
        });

        super.init();
    }

}
