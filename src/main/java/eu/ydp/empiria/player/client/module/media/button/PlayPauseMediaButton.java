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

package eu.ydp.empiria.player.client.module.media.button;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.media.MediaStyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

public class PlayPauseMediaButton extends AbstractPlayMediaButton {

    @Inject
    public PlayPauseMediaButton(MediaStyleNameConstants styleNames) {
        super(styleNames.QP_MEDIA_PLAY_PAUSE());
    }

    @Override
    protected boolean initButtonStyleChangeHandlersCondition() {
        return getMediaAvailableOptions().isPauseSupported();
    }

    @Override
    protected MediaEvent createMediaEvent() {
        MediaEvent mediaEvent;
        if (isActive()) {
            mediaEvent = new MediaEvent(MediaEventTypes.PAUSE, getMediaWrapper());
        } else {
            mediaEvent = new MediaEvent(MediaEventTypes.PLAY, getMediaWrapper());
        }
        return mediaEvent;
    }
}
