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

package eu.ydp.empiria.player.client.controller.workmode;

import java.util.EnumSet;

public enum PlayerWorkMode {
    FULL(new EmptyWorkModeSwitcher()) {
        @Override
        EnumSet<PlayerWorkMode> getAvailableTransitions() {
            return EnumSet.complementOf(EnumSet.of(TEST_SUBMITTED));
        }
    },
    PREVIEW(new PreviewWorkModeSwitcher()) {
        @Override
        EnumSet<PlayerWorkMode> getAvailableTransitions() {
            return EnumSet.noneOf(PlayerWorkMode.class);
        }
    },
    TEST(new TestWorkModeSwitcher()) {
        @Override
        EnumSet<PlayerWorkMode> getAvailableTransitions() {
            return EnumSet.of(PREVIEW, TEST_SUBMITTED);
        }
    },
    TEST_SUBMITTED(new TestSubmittedWorkModeSwitcher()) {
        @Override
        EnumSet<PlayerWorkMode> getAvailableTransitions() {
            return EnumSet.of(PREVIEW, TEST);
        }
    };

    private final WorkModeSwitcher workModeSwitcher;

    private PlayerWorkMode(WorkModeSwitcher workModeSwitcher) {
        this.workModeSwitcher = workModeSwitcher;
    }

    public WorkModeSwitcher getWorkModeSwitcher() {
        return workModeSwitcher;
    }

    public boolean canChangeModeTo(PlayerWorkMode newWorkMode) {
        return getAvailableTransitions().contains(newWorkMode);
    }

    abstract EnumSet<PlayerWorkMode> getAvailableTransitions();
}
