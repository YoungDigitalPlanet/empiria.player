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

import com.google.gwt.json.client.JSONArray;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.json.JSONStateSerializer;
import eu.ydp.empiria.player.client.module.core.flow.Stateful;

public class PlayerWorkModeState implements Stateful {

    @Inject
    private PlayerWorkModeService playerWorkModeService;
    @Inject
    private JSONStateSerializer jsonStateSerializer;

    @Override
    public JSONArray getState() {
        PlayerWorkMode currentWorkMode = playerWorkModeService.getCurrentWorkMode();
        return jsonStateSerializer.createWithString(currentWorkMode.toString());
    }

    @Override
    public void setState(JSONArray array) {
        String state = jsonStateSerializer.extractString(array);
        playerWorkModeService.tryToUpdateWorkMode(PlayerWorkMode.valueOf(state));
    }
}
