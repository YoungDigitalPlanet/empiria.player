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

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

import static eu.ydp.empiria.player.client.controller.workmode.PlayerWorkMode.FULL;

@Singleton
public class PlayerWorkModeService {

    private final List<WorkModeClientType> moduleList = new ArrayList<>();
    private PlayerWorkMode currentWorkMode = FULL;
    private PlayerWorkMode previousWorkMode = FULL;

    public void registerModule(WorkModeClientType module) {
        moduleList.add(module);
        notifyModule(module);
    }

    public void tryToUpdateWorkMode(PlayerWorkMode newWorkMode) {
        if (currentWorkMode.canChangeModeTo(newWorkMode)) {
            previousWorkMode = currentWorkMode;
            currentWorkMode = newWorkMode;
            notifyModules();
        }
    }

    private void notifyModules() {
        for (WorkModeClientType module : moduleList) {
            notifyModule(module);
        }
    }

    private void notifyModule(WorkModeClientType module) {
        disablePreviousModule(module);
        enableCurrentModule(module);
    }

    private void disablePreviousModule(WorkModeClientType module) {
        previousWorkMode.getWorkModeSwitcher()
                .disable(module);
    }

    private void enableCurrentModule(WorkModeClientType module) {
        currentWorkMode.getWorkModeSwitcher()
                .enable(module);
    }

    public PlayerWorkMode getCurrentWorkMode() {
        return currentWorkMode;
    }
}
