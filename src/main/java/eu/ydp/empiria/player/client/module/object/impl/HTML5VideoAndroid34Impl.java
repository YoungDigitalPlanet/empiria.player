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

import com.google.gwt.media.client.MediaBase;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;

public class HTML5VideoAndroid34Impl extends HTML5VideoAndroidImpl {
    private boolean videoReady = false;
    // zatrzymujemy 300ms przed koncem na androidach wystepuje error dla niektorych filmow
    private static final int STOP_TIME = 300;

    protected void stopVideo() {
        MediaBase video = (MediaBase) ((MediaWrapper<?>) getEventBusSourceObject()).getMediaObject();
        video.pause();
        video.setCurrentTime(0);
    }

    protected boolean isAudioShouldBeStopped() {
        MediaWrapper<?> mediaWrapper = getEventBusSourceObject();
        double currentTime = mediaWrapper.getCurrentTime() * 1000;
        double duration = mediaWrapper.getDuration() * 1000;
        boolean stop = false;
        if (currentTime >= 1000 && currentTime > duration - STOP_TIME) {
            stop = true;
        }
        return stop;
    }

    protected boolean isVideoReady() {
        return videoReady;
    }

    protected void setVideoReady(boolean videoReady) {
        this.videoReady = videoReady;
    }

    protected void addHandlerToVideoOnDurationChange() {
        // czekamy na informacje na temat dlugosci utworu
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE), getEventBusSourceObject(), new MediaEventHandler() {

            @Override
            public void onMediaEvent(MediaEvent event) {
                setVideoReady(true);
            }
        }, new CurrentPageScope());
    }

    protected void addHandlerToVideoOnTimeUpdate() {
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), getEventBusSourceObject(), new MediaEventHandler() {
            // galaxytabie ponizej nie zalapie
            @Override
            public void onMediaEvent(MediaEvent event) {
                // 300ms przed koncem filmu
                if (isVideoReady() && isAudioShouldBeStopped()) {
                    stopVideo();
                }
            }
        }, new CurrentPageScope());
    }

    @Override
    public void setEventBusSourceObject(MediaWrapper<?> object) {
        super.setEventBusSourceObject(object);
        addHandlerToVideoOnTimeUpdate();
        addHandlerToVideoOnDurationChange();

    }

}
