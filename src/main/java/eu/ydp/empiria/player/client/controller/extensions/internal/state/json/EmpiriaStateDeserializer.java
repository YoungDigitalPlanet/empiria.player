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

package eu.ydp.empiria.player.client.controller.extensions.internal.state.json;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaState;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaStateType;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Singleton;

@Singleton
public class EmpiriaStateDeserializer {

    public EmpiriaState deserialize(JSONValue stateJson) {

        JSONObject stateObject = stateJson.isObject();

        if (isNewStateObject(stateJson)) {
            EmpiriaStateType type = getStateType(stateObject);
            String state = stateObject.get(EmpiriaState.STATE).isString().stringValue();

            return new EmpiriaState(type, state, getSavedLessonIdentifier(stateObject));
        }

        return new EmpiriaState(EmpiriaStateType.OLD, stateJson.toString(), StringUtils.EMPTY);
    }

    private boolean isNewStateObject(JSONValue stateJson) {
        return stateJson.isObject() != null && stateJson.isObject().containsKey(EmpiriaState.TYPE);
    }

    private EmpiriaStateType getStateType(JSONObject jsonObject) {
        String typeValue = jsonObject.get(EmpiriaState.TYPE).isString().stringValue();

        for (EmpiriaStateType stateType : EmpiriaStateType.values()) {
            if (stateType.name().equals(typeValue)) {
                return stateType;
            }
        }

        return EmpiriaStateType.UNKNOWN;
    }

    private String getSavedLessonIdentifier(JSONObject jsonObject) {
        boolean hasIdentifier = jsonObject.containsKey(EmpiriaState.LESSON_IDENTIFIER);
        if(hasIdentifier) {
            return jsonObject.get(EmpiriaState.LESSON_IDENTIFIER).isString().stringValue();
        }

        return StringUtils.EMPTY;
    }
}
