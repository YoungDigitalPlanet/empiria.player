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

public class ItemJsonReport extends JavaScriptObject {

    protected ItemJsonReport() {

    }

    public static ItemJsonReport create() {
        return new OverlayTypesParser().get();
    }

    public static ItemJsonReport create(String json) {
        return new OverlayTypesParser().get(json);
    }

    public final native void setIndex(int index)/*-{
        this.index = index;
    }-*/;

    public final native void setTitle(String title)/*-{
        this.title = title;
    }-*/;

    public final native void setResult(ResultJsonReport result)/*-{
        this.result = result;
    }-*/;

    public final native void setHints(HintJsonReport hints)/*-{
        this.hints = hints;
    }-*/;

    public final String getJSONString() {
        return new JSONObject(this).toString();
    }

}
