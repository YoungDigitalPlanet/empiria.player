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

package eu.ydp.empiria.player.client.controller.events.interaction;

import java.util.HashMap;
import java.util.Map;

public class MediaInteractionSoundEvent extends InteractionEvent {

    protected String url;
    protected MediaInteractionSoundEventCallback callback;

    public MediaInteractionSoundEvent(String path, MediaInteractionSoundEventCallback callback) {
        this.url = path;
        this.callback = callback;
    }

    public String getPath() {
        return url;
    }

    public MediaInteractionSoundEventCallback getCallback() {
        return callback;
    }

    @Override
    public InteractionEventType getType() {
        return InteractionEventType.MEDIA_SOUND_PLAY;
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("url", url);
        p.put("callback", callback);
        return p;
    }
}
