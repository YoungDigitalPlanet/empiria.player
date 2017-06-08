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
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes.ON_FULL_SCREEN_SHOW_CONTROLS;
import static eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes.ON_STOP;

public class ProgressBarUpdateEventHandler implements MediaEventHandler {
    Set<MediaEventTypes> fastUpdateEvents = new HashSet<MediaEventTypes>(Arrays.asList(new MediaEventTypes[]{ON_FULL_SCREEN_SHOW_CONTROLS, ON_STOP,
            MediaEventTypes.ON_DURATION_CHANGE}));
    // -1 aby przy pierwszym zdarzeniu pokazal sie timer
    private int lastTime = -1;
    private final MediaProgressBarImpl progressBar;
    private final ProgressUpdateLogic progressUpdateLogic;

    @Inject
    public ProgressBarUpdateEventHandler(@Assisted MediaProgressBarImpl progressBar, ProgressUpdateLogic progressUpdateLogic) {
        this.progressBar = progressBar;
        this.progressUpdateLogic = progressUpdateLogic;
    }

    @Override
    public void onMediaEvent(MediaEvent event) {
        if (progressBar.isMediaReady() && !progressBar.isPressed()) {
            double currentTime = progressBar.getMediaWrapper().getCurrentTime();

            if (progressUpdateLogic.isReadyToUpdate(currentTime, lastTime) || fastUpdateEvents.contains(event.getType())) {
                lastTime = (int) progressBar.getMediaWrapper().getCurrentTime();
                double steep = progressBar.getScrollWidth() / progressBar.getMediaWrapper().getDuration();
                progressBar.moveScroll((int) (steep * lastTime));
            }
        }
    }

    public void resetCurrentTime() {
        lastTime = -1;
    }
}
