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

package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.params.MediaStatus;
import eu.ydp.gwtutil.client.timer.Timer;

public class ExternalMediaUpdateTimerEmulator {

    private static final int DELAY_MILLIS = 250;

    @Inject
    private Timer timer;
    @Inject
    private Provider<ExternalMediaUpdateTimerEmulatorState> stateProvider;

    private EmulatedTimeUpdateListener listener;
    private ExternalMediaUpdateTimerEmulatorState state;

    private Runnable timerAction = new Runnable() {

        @Override
        public void run() {
            update();
        }
    };

    public void init(EmulatedTimeUpdateListener listener) {
        this.listener = listener;
        timer.init(timerAction);
    }

    public void run(int initialTimeMillis) {
        createAndInitState(initialTimeMillis);
        runTimer();
    }

    public void stop() {
        timer.cancel();
    }

    private void createAndInitState(int initialTimeMillis) {
        state = stateProvider.get();
        state.init(initialTimeMillis);
    }

    private void runTimer() {
        timer.scheduleRepeating(DELAY_MILLIS);
    }

    private void update() {
        final int currentMediaTimeMillis = state.findCurrentMediaTimeMillis();
        MediaStatus status = createMediaStatus(currentMediaTimeMillis);
        listener.emulatedTimeUpdate(status);
    }

    private MediaStatus createMediaStatus(final int currentMediaTimeMillis) {
        MediaStatus status = new MediaStatus() {

            @Override
            public int getCurrentTimeMillis() {
                return currentMediaTimeMillis;
            }
        };
        return status;
    }
}
