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

package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.wrapper;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.ExternalMediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.UniqueIdGenerator;

public class ExternalMediaWrapper implements MediaWrapper<Widget> {

    @Inject
    private UniqueIdGenerator idGenerator;

    private String uniqId;
    private double durationSeconds;
    private double currentTimeSeconds;

    @Override
    public MediaAvailableOptions getMediaAvailableOptions() {
        return new ExternalMediaAvailableOptions();
    }

    @Override
    public Widget getMediaObject() {
        return new SimplePanel();
    }

    @Override
    public String getMediaUniqId() {
        if (uniqId == null) {
            uniqId = idGenerator.createUniqueId();
        }
        return uniqId;
    }

    @Override
    public double getCurrentTime() {
        return currentTimeSeconds;
    }

    @Override
    public double getDuration() {
        return durationSeconds;
    }

    @Override
    public boolean isMuted() {
        return false;
    }

    @Override
    public double getVolume() {
        return 0;
    }

    @Override
    public boolean canPlay() {
        return true;
    }

    public void setDuration(double durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public void setCurrentTime(double currentTimeSeconds) {
        this.currentTimeSeconds = currentTimeSeconds;
    }

}
