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

package eu.ydp.empiria.player.client.module.conversion;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.connection.structure.StateController;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.json.YJsonObject;
import eu.ydp.gwtutil.client.json.YJsonValue;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import eu.ydp.gwtutil.client.state.converter.IStateConvertionStrategy;

public class StateToStateAndStructureConverter implements IStateConvertionStrategy {

    private final IJSONService jsonService;

    @Inject
    public StateToStateAndStructureConverter(IJSONService jsonService) {
        this.jsonService = jsonService;
    }

    @Override
    public int getStartVersion() {
        return 0;
    }

    @Override
    public YJsonValue convert(YJsonValue jsonState) {

        if (!isVersionForConvert(jsonState)) {
            return jsonState;
        }

        YJsonObject resultObject = jsonService.createObject();

        resultObject.put(StateController.STRUCTURE, jsonService.createArray());
        resultObject.put(StateController.STATE, jsonState);

        YJsonArray result = jsonService.createArray();
        result.set(0, resultObject);
        return result;
    }

    private boolean isVersionForConvert(YJsonValue jsonState) {
        return jsonState.isArray().size() == 0 || jsonState.isArray().get(0).isObject() == null;
    }
}
