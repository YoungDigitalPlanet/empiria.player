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

package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;

public class HintJsonReport extends JavaScriptObject {

    protected HintJsonReport() {

    }

    public static HintJsonReport create() {
        return new OverlayTypesParser().get();
    }

    public static HintJsonReport create(String json) {
        return new OverlayTypesParser().get(json);
    }

    public final native void setChecks(int value)/*-{
        this.checks = value;
    }-*/;

    public final native void setMistakes(int value)/*-{
        this.mistakes = value;
    }-*/;

    public final native void setShowAnswers(int value)/*-{
        this.showAnswers = value;
    }-*/;

    public final native void setReset(int value)/*-{
        this.reset = value;
    }-*/;

    public final String getJSONString() {
        return new JSONObject(this).toString();
    }

}
