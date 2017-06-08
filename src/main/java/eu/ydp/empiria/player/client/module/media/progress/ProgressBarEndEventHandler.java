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

package eu.ydp.empiria.player.client.module.media.progress;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;

import static eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes.PAUSE;

public class ProgressBarEndEventHandler implements MediaEventHandler {

    private final MediaProgressBarImpl progressBar;
    private final EventsBus eventsBus;

    @Inject
    public ProgressBarEndEventHandler(@Assisted MediaProgressBarImpl progressBar, EventsBus eventsBus) {
        this.progressBar = progressBar;
        this.eventsBus = eventsBus;

    }

    @Override
    public void onMediaEvent(MediaEvent event) {
        double steep = progressBar.getScrollWidth() / progressBar.getMediaWrapper().getDuration();
        progressBar.moveScroll((int) (steep * progressBar.getMediaWrapper().getCurrentTime()));
        eventsBus.fireEventFromSource(new MediaEvent(PAUSE, progressBar.getMediaWrapper()), progressBar.getMediaWrapper());
    }

}
