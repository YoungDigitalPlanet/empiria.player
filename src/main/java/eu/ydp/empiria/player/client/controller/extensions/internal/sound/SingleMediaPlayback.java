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

package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

public class SingleMediaPlayback {

    private Optional<String> currentMediaId = Optional.absent();

    public void setCurrentMediaId(String newMediaId) {
        currentMediaId = Optional.of(newMediaId);
    }

    public String getCurrentMediaId() {
        return currentMediaId.get();
    }

    public boolean isPlaybackPresent() {
        return currentMediaId.isPresent();
    }

    public void clearCurrentMediaIdIfEqual(String refId) {
        if (currentMediaId.isPresent() && Objects.equal(refId, currentMediaId.get())) {
            currentMediaId = Optional.absent();
        }
    }

    public boolean isCurrentPlayback(String id) {
        return currentMediaId.isPresent() && currentMediaId.get().equals(id);
    }

}
