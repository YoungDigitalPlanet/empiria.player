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

package eu.ydp.empiria.player.client.controller.extensions.internal.media.event;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.AbstractMediaProcessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.gwtutil.client.debug.log.Logger;

public class DefaultMediaEventController implements MediaEventController {

    @Inject
    private Logger logger;

    @Override
    public void onMediaEvent(MediaEvent event, MediaExecutor<?> executor, AbstractMediaProcessor processor) {
        switch (((MediaEventTypes) event.getAssociatedType().getType())) {
            case CHANGE_VOLUME:
                executor.setVolume(event.getVolume());
                break;
            case STOP:
                executor.stop();
                break;
            case PAUSE:
                executor.pause();
                break;
            case RESUME:
                executor.resume();
                break;
            case SET_CURRENT_TIME:
                executor.setCurrentTime(event.getCurrentTime());
                break;
            case PLAY:
                executor.play();
                break;
            case PLAY_LOOPED:
                executor.playLooped();
                break;
            case MUTE:
                executor.setMuted(!(executor.getMediaWrapper().isMuted()));
                break;
            case ENDED:
            case ON_END:
                executor.stop();
                break;
            case ON_ERROR:
                logger.info("Media Error");
                break;
            default:
                break;
        }

    }
}
