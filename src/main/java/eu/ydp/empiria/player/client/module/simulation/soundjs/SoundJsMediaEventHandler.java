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

package eu.ydp.empiria.player.client.module.simulation.soundjs;

import com.google.gwt.media.client.MediaBase;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.gwtutil.client.debug.log.Logger;

public class SoundJsMediaEventHandler implements MediaEventHandler {

    private SoundJsNative soundJsNative;

    @Inject
    private Logger logger;

    public void setSoundJsNative(SoundJsNative soundJsNative) {
        this.soundJsNative = soundJsNative;
    }

    @Override
    public void onMediaEvent(MediaEvent event) {
        Object mediaObject = event.getMediaWrapper().getMediaObject();

        if (!(mediaObject instanceof MediaBase)) {
            logger.error("MediaObject does not extend a MediaBase");
            return;
        }

        MediaBase mediaBase = (MediaBase) mediaObject;
        String src = mediaBase.getCurrentSrc();

        switch (event.getType()) {
            case ON_END:
                soundJsNative.onComplete(src);
                break;
            default:
                break;
        }
    }

}
