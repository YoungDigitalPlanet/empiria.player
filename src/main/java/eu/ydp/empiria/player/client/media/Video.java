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

package eu.ydp.empiria.player.client.media;

import com.google.gwt.dom.client.VideoElement;
import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.gin.factory.TextTrackFactory;
import eu.ydp.empiria.player.client.media.texttrack.TextTrack;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;

import java.util.ArrayList;
import java.util.List;

public class Video extends com.google.gwt.media.client.Video implements MediaEventHandler {
    private final List<TextTrack> textTracks = new ArrayList<TextTrack>();
    protected final EventsBus eventsBus = PlayerGinjectorFactory.getPlayerGinjector().getEventsBus();
    protected final TextTrackFactory textTrackFactory = PlayerGinjectorFactory.getPlayerGinjector().getTextTrackFactory();
    private boolean initialized = false;
    private MediaWrapper<?> eventBusSource = null;

    protected Video(VideoElement element) {
        super(element);
    }

    private final void initHandler() {
        if (!initialized) {
            initialized = true;
            eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), eventBusSource, this, new CurrentPageScope());
        }
    }

    @Override
    protected void onUnload() {
        pause();
    }

    public TextTrack addTrack(TextTrackKind textTrackKind) {
        initHandler();
        TextTrack track = textTrackFactory.getTextTrack(textTrackKind, eventBusSource);
        textTracks.add(track);
        return track;
    }

    public static Video createIfSupported() {
        com.google.gwt.media.client.Video media = com.google.gwt.media.client.Video.createIfSupported();
        Video video = null;
        if (media != null) {
            video = new Video((VideoElement) media.getMediaElement());
        }
        return video;
    }

    public void setEventBusSourceObject(MediaWrapper<?> object) {
        eventBusSource = object;
    }

    @Override
    public void onMediaEvent(MediaEvent event) {
        if (event.getType() == MediaEventTypes.ON_TIME_UPDATE) {
            for (TextTrack track : textTracks) {
                track.setCurrentTime(eventBusSource.getCurrentTime());
            }
        }
    }

}
