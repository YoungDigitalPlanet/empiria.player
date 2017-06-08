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

package eu.ydp.empiria.player.client.media.texttrack;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.media.Audio;
import eu.ydp.empiria.player.client.media.Video;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Objekt reprezentujacy sciezke tekstowa powiazana z {@link Video} lub {@link Audio}
 */
public class TextTrack {
    private final TextTrackKind kind;
    private final SortedSet<TextTrackCue> cues = new TreeSet<TextTrackCue>();
    private TextTrackCue activeCue = null;
    protected EventsBus eventsBus;
    private final Object eventBusSource;
    private final PageScopeFactory pageScopeFactory;

    @Inject
    public TextTrack(@Assisted TextTrackKind kind, @Assisted Object eventBusSource, EventsBus eventsBus, PageScopeFactory pageScopeFactory) {
        this.kind = kind;
        this.eventBusSource = eventBusSource;
        this.eventsBus = eventsBus;
        this.pageScopeFactory = pageScopeFactory;
    }

    public TextTrackKind getKind() {
        return kind;
    }

    public void addCue(TextTrackCue textTrackCue) {
        cues.add(textTrackCue);
        textTrackCue.setTextTrack(this);
    }

    @SuppressWarnings("PMD")
    public void setCurrentTime(double time) {
        for (TextTrackCue cue : cues) {
            if (!cue.equals(activeCue)) {
                if (cue.getStartTime() <= time && cue.getEndTime() > time) {
                    MediaEvent event = new MediaEvent(MediaEventTypes.TEXT_TRACK_UPDATE, eventBusSource);
                    event.setTextTrackCue(cue);
                    eventsBus.fireAsyncEventFromSource(event, eventBusSource, pageScopeFactory.getCurrentPageScope());
                    activeCue = cue;
                }
            }
        }
    }
}
