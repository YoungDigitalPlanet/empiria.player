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

package eu.ydp.empiria.player.client.module.external.common.sound;

import com.google.common.base.Optional;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

@JsType
public class ExternalSoundInstance {

    private final MediaWrapperController mediaWrapperController;
    private final MediaWrapper<Widget> audioWrapper;

    @Inject
    public ExternalSoundInstance(@Assisted MediaWrapper<Widget> audioWrapper, @Assisted final Optional<OnEndCallback> onEndCallback, @Assisted Optional<OnPauseCallback> onPauseCallback,
                                 MediaWrapperController mediaWrapperController) {
        this.audioWrapper = audioWrapper;
        this.mediaWrapperController = mediaWrapperController;
        registerOnEndCallback(audioWrapper, onEndCallback);
        registerOnPauseCallback(audioWrapper, onPauseCallback);
    }

    private void registerOnEndCallback(MediaWrapper<Widget> audioWrapper, final Optional<OnEndCallback> onEndCallback) {
        if (onEndCallback.isPresent()) {
            mediaWrapperController.addHandler(MediaEventTypes.ON_END, audioWrapper, new MediaEventHandler() {
                @Override
                public void onMediaEvent(MediaEvent event) {
                    onEndCallback.get().onEnd();
                }
            });
        }
    }

    private void registerOnPauseCallback(MediaWrapper<Widget> audioWrapper, final Optional<OnPauseCallback> onPauseCallback) {
        if (onPauseCallback.isPresent()) {
            mediaWrapperController.addHandler(MediaEventTypes.ON_PAUSE, audioWrapper, new MediaEventHandler() {
                @Override
                public void onMediaEvent(MediaEvent event) {
                    onPauseCallback.get().onPause();
                }
            });
        }
    }

    public void play() {
        mediaWrapperController.stopAndPlay(audioWrapper);
    }

    public void playLooped() {
        mediaWrapperController.playLooped(audioWrapper);
    }

    public void stop() {
        mediaWrapperController.stop(audioWrapper);
    }

    public void pause() {
        mediaWrapperController.pause(audioWrapper);
    }

    public void resume() {
        mediaWrapperController.resume(audioWrapper);
    }
}
