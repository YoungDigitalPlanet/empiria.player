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

package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.media.client.Video;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;

/**
 * domyslna implementacja dla androida
 */
public class HTML5VideoAndroidImpl extends HTML5VideoImpl {
    public class MP4ErrorHandler implements MediaEventHandler {
        private final Video video;

        public MP4ErrorHandler(Video video) {
            this.video = video;
        }

        // dla androida 2.3.x, 4.x obejscie problemow gdy mp4 jest uszkodzony i
        // player sie zawiesza
        @Override
        public void onMediaEvent(MediaEvent event) {
            video.load();

        }
    }

    @Override
    public void setEventBusSourceObject(MediaWrapper<?> object) {
        super.setEventBusSourceObject(object);
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_END), getEventBusSourceObject(), new MP4ErrorHandler(video), new CurrentPageScope());
    }
}
