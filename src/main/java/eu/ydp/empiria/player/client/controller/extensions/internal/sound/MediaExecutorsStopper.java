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

import eu.ydp.empiria.player.client.module.media.MediaWrapper;

import java.util.Collection;

public class MediaExecutorsStopper {

    public void forceStop(MediaWrapper<?> currentMediaWrapper, Collection<MediaExecutor<?>> executors) {
        for (MediaExecutor<?> exec : executors) {
            maybeStopOrPauseExecutor(currentMediaWrapper, exec);
        }
    }

    private void maybeStopOrPauseExecutor(MediaWrapper<?> currentMediaWrapper, MediaExecutor<?> exec) {
        MediaWrapper<?> otherMediaWrapper = exec.getMediaWrapper();

        boolean otherMediaWrapperDefined = (otherMediaWrapper != null);
        boolean currentMediaWrapperDefined = (currentMediaWrapper != null);

        if (otherMediaWrapperDefined) {

            stopOrPauseIfDifferent(currentMediaWrapper, exec);

        } else if (currentMediaWrapperDefined) {

            stop(exec);

        }
    }

    private void stopOrPauseIfDifferent(MediaWrapper<?> currentMediaWrapper, MediaExecutor<?> exec) {
        MediaWrapper<?> otherMediaWrapper = exec.getMediaWrapper();

        boolean currentIsDifferentThanOther = !otherMediaWrapper.equals(currentMediaWrapper);

        if (currentIsDifferentThanOther) {
            boolean pauseSupported = otherMediaWrapper.getMediaAvailableOptions().isPauseSupported();
            stopOrPause(exec, pauseSupported);
        }
    }

    private void stopOrPause(MediaExecutor<?> exec, boolean pauseSupported) {
        if (pauseSupported) {
            exec.pause();
        } else {
            exec.stop();
        }
    }

    private void stop(MediaExecutor<?> exec) {
        exec.stop();
    }

}
