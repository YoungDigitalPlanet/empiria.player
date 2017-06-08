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

package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.StatefulExtension;

public class JsStatefulExtension extends AbstractJsExtension implements StatefulExtension {

    @Override
    public ExtensionType getType() {
        return ExtensionType.EXTENSION_CLIENT_STATEFUL;
    }

    @Override
    public void init() {

    }

    @Override
    public JSONArray getState() {
        String s = getStateJs(extensionJsObject);
        JSONValue v = new JSONString(s);
        JSONArray arr = new JSONArray();
        arr.set(0, v);
        return arr;
    }

    @Override
    public void setState(JSONArray newState) {
        if (newState.isArray() instanceof JSONArray) {
            JSONArray arr = newState.isArray();
            JSONValue v = arr.get(0);
            if (v.isString() instanceof JSONString) {
                String s = v.isString().stringValue();
                setStateJs(extensionJsObject, s);
            }
        }
    }

    private native String getStateJs(JavaScriptObject extension)/*-{
        if (typeof extension.getStateString == 'function') {
            return extension.getStateString();
        }
        return null;
    }-*/;

    private native void setStateJs(JavaScriptObject extension, String stateString)/*-{
        if (typeof extension.setStateString == 'function') {
            extension.setStateString(stateString);
        }
    }-*/;

}
