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

package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ResizeTimer extends Timer {

    private final ResizeContinuousUpdater resizeContinuousUpdater;

    @Inject
    public ResizeTimer(ResizeContinuousUpdater resizeContinuousUpdater) {
        this.resizeContinuousUpdater = resizeContinuousUpdater;
    }

    public void cancelAndReset() {
        super.cancel();
        resizeContinuousUpdater.reset();
    }

    @Override
    public void run() {
        int rescheduleTime = resizeContinuousUpdater.runContinuousResizeUpdateAndReturnRescheduleTime();

        if (rescheduleTime > 0) {
            schedule(rescheduleTime);
        }
    }

}
