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

package eu.ydp.empiria.player.client.util.dom.drag;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class NativeDragDataObject extends JavaScriptObject implements DragDataObject {
    protected NativeDragDataObject() {
        //
    }

    @Override
    public final native String getItemId()/*-{
        return this.itemId;
    }-*/;

    @Override
    public final native void setItemId(String itemId)/*-{
        this.itemId = itemId;
    }-*/;

    @Override
    public final native String getSourceId() /*-{
        return this.sourceId;
    }-*/;

    @Override
    public final native void setSourceId(String sourceId) /*-{
        this.sourceId = sourceId;
    }-*/;

    @Override
    public final String toJSON() {
        return new JSONObject(this).toString();
    }

}
