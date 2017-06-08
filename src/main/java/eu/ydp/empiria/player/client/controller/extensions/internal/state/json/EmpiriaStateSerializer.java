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
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaState;

import javax.inject.Singleton;

@Singleton
public class EmpiriaStateSerializer {

    public JSONValue serialize(EmpiriaState empiriaState) {

        String typeFormat = empiriaState.getFormatType().name();
        String state = empiriaState.getState();
        String lessonIdentifier = empiriaState.getLessonIdentifier();

        JSONObject stateObject = new JSONObject();
        stateObject.put(EmpiriaState.TYPE, new JSONString(typeFormat));
        stateObject.put(EmpiriaState.STATE, new JSONString(state));
        stateObject.put(EmpiriaState.LESSON_IDENTIFIER, new JSONString(lessonIdentifier));

        return stateObject;
    }

}
