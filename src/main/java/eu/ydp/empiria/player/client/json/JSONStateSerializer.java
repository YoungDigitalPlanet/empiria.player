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

package eu.ydp.empiria.player.client.json;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class JSONStateSerializer {

    public String extractString(JSONArray array) {
        return extractValue(array).isString().stringValue();
    }

    public JSONArray createWithString(String value) {
        JSONString jsonString = new JSONString(value);
        return createWithValue(jsonString);
    }

    private JSONValue extractValue(JSONArray array) {
        return array.get(0);
    }

    private JSONArray createWithValue(JSONValue value) {
        JSONArray state = new JSONArray();
        state.set(0, value);
        return state;
    }
}
