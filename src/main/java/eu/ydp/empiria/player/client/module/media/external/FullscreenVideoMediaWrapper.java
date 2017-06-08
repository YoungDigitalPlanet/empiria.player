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

package eu.ydp.empiria.player.client.module.media.external;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.ExternalFullscreenVideoImpl;
import eu.ydp.empiria.player.client.util.UniqueIdGenerator;

public class FullscreenVideoMediaWrapper implements MediaWrapper<Widget> {

    private static final int DURATION_MILLIPERCENT_MAX = 100 * 1000;
    @Inject
    private UniqueIdGenerator idGnerator;
    @Inject
    private ExternalFullscreenVideoMediaAvailableOptions availableOptions;
    @Inject
    private ExternalFullscreenVideoImpl mediaObject;

    private String id;
    private double time;

    @Override
    public MediaAvailableOptions getMediaAvailableOptions() {
        return availableOptions;
    }

    @Override
    public Widget getMediaObject() {
        return mediaObject.asWidget();
    }

    public void setPoster(String url) {
        mediaObject.setPoster(url);
    }

    @Override
    public String getMediaUniqId() {
        if (id == null) {
            id = idGnerator.createUniqueId();
        }
        return id;
    }

    @Override
    public double getCurrentTime() {
        return time;
    }

    @Override
    public double getDuration() {
        return DURATION_MILLIPERCENT_MAX;
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

    public void setCurrentTime(double time) {
        this.time = time;
    }
}
