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

package eu.ydp.empiria.player.client.controller.variables.storage.item;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;
import eu.ydp.empiria.player.client.Player;
import eu.ydp.empiria.player.client.controller.variables.VariablePossessorBase;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;

import java.util.HashMap;
import java.util.Map;

public class ItemOutcomeStorageImpl extends VariablePossessorBase<Outcome> {

    public ItemOutcomeStorageImpl() {
        variables = new HashMap<>();
    }

    public void importFromMap(Map<String, Outcome> newValues) {
        variables.putAll(newValues);
    }

    public void putVariable(String key, Outcome variable) {
        variables.put(key, variable);
    }

    public Map<String, Outcome> getVariablesCopy() {
        return new HashMap<>(variables);
    }

    public boolean hasOutcome(String key) {
        return variables.containsKey(key);
    }

    public JSONValue toJSON() {
        JSONArray stateArr = new JSONArray();
        int i = 0;
        for (Outcome value : variables.values()) {

            if (value.isNotEmpty()) {
                stateArr.set(i, value.toJSON());
                i++;
            }

        }
        return stateArr;
    }

    @SuppressWarnings("unchecked")
    public void fromJSON(JSONValue json) {
        JSONArray jsonArr = json.isArray();

        if (jsonArr != null) {
            for (int i = 0; i < jsonArr.size(); i++) {
                if (!(jsonArr.get(0) instanceof JSONNull) && !(jsonArr.get(i).isArray().get(0) instanceof JSONNull)) {
                    String type = jsonArr.get(i).isArray().get(0).isString().stringValue();
                    if (type.equals(Outcome.OLD_OUTCOME) || type.equals(Outcome.OUTCOME)) {
                        Outcome o = new Outcome();
                        o.fromJSON(jsonArr.get(i));
                        variables.put(o.identifier, o);
                    }
                }
            }
        }
    }
}
