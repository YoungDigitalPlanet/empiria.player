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

package eu.ydp.empiria.player.client.util.events.internal.media;

import eu.ydp.empiria.player.client.media.texttrack.TextTrackCue;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.internal.EventTypes;
import eu.ydp.empiria.player.client.util.events.internal.EventType;

public class MediaEvent extends AbstractEvent<MediaEventHandler, MediaEventTypes> {
    public static EventTypes<MediaEventHandler, MediaEventTypes> types = new EventTypes<MediaEventHandler, MediaEventTypes>();

    private double currentTime;
    private double volume;

    private TextTrackCue textTrackCue;

    public MediaEvent(MediaEventTypes type) {
        this(type, null);
    }

    public MediaEvent(MediaEventTypes type, Object source) {
        super(type, source);
    }

    public MediaWrapper<?> getMediaWrapper() {
        return (super.getSource() instanceof MediaWrapper<?>) ? (MediaWrapper<?>) super.getSource() : null;// NOPMD
    }

    public void setCurrentTime(Double position) {
        this.currentTime = position;
    }

    public Double getCurrentTime() {
        return currentTime;
    }

    public void setTextTrackCue(TextTrackCue textTrackCue) {
        this.textTrackCue = textTrackCue;
    }

    public TextTrackCue getTextTrackCue() {
        return textTrackCue;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double value) {
        this.volume = value;
    }

    @Override
    protected EventTypes<MediaEventHandler, MediaEventTypes> getTypes() {
        return types;
    }

    @Override
    public void dispatch(MediaEventHandler handler) {
        handler.onMediaEvent(this);
    }

    public static EventType<MediaEventHandler, MediaEventTypes> getType(MediaEventTypes type) {
        return types.getType(type);
    }

    public static EventType<MediaEventHandler, MediaEventTypes>[] getTypes(MediaEventTypes... type) {
        return types.getTypes(type);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MediaEvent [type=");
        builder.append(getType());
        builder.append("]");
        return builder.toString();
    }

}
