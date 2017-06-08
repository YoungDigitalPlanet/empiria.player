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

import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

import javax.inject.Singleton;

@Singleton
public class JsonParserWrapper {

    public JSONValue parse(String value) {
        return JSONParser.parseLenient(value);
    }

}
