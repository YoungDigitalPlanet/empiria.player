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

package eu.ydp.empiria.player.client.module.media;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

import static eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes.*;

public class MediaWrapperController {
    @Inject
    private EventsBus eventsBus;

    public void play(MediaWrapper<Widget> mediaWrapper) {
        fireEventFromSource(PLAY, mediaWrapper);
    }

    public void playLooped(MediaWrapper<Widget> mediaWrapper) {
        fireEventFromSource(PLAY_LOOPED, mediaWrapper);
    }

    public void stop(MediaWrapper<Widget> mediaWrapper) {
        fireEventFromSource(STOP, mediaWrapper);
    }

    public void pause(MediaWrapper<Widget> mediaWrapper) {
        fireEventFromSource(PAUSE, mediaWrapper);
    }

    public void resume(MediaWrapper<Widget> mediaWrapper) {
        fireEventFromSource(RESUME, mediaWrapper);
    }

    public void stopAndPlay(MediaWrapper<Widget> mediaWrapper) {
        stop(mediaWrapper);
        play(mediaWrapper);
    }

    public void pauseAndPlay(MediaWrapper<Widget> mediaWrapper) {
        pause(mediaWrapper);
        play(mediaWrapper);
    }

    public void pauseAndPlayLooped(MediaWrapper<Widget> mediaWrapper) {
        pause(mediaWrapper);
        playLooped(mediaWrapper);
    }

    public void setCurrentTime(MediaWrapper<Widget> mediaWrapper, Double time) {
        MediaEvent event = new MediaEvent(MediaEventTypes.SET_CURRENT_TIME, mediaWrapper);
        event.setCurrentTime(time);

        eventsBus.fireEventFromSource(event, mediaWrapper);
    }

    public double getCurrentTime(MediaWrapper<Widget> mediaWrapper) {
        return mediaWrapper.getCurrentTime();
    }

    public void addHandler(MediaEventTypes type, MediaWrapper<Widget> wrapper, MediaEventHandler handler) {
        eventsBus.addHandlerToSource(MediaEvent.getType(type), wrapper, handler);
    }

    private void fireEventFromSource(MediaEventTypes eventType, MediaWrapper<Widget> mediaWrapper) {
        eventsBus.fireEventFromSource(new MediaEvent(eventType, mediaWrapper), mediaWrapper);
    }
}
