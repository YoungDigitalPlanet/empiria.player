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

package eu.ydp.empiria.player.client.module.colorfill.fill.stack;

import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;

public class JsStack extends JavaScriptObject {

    public static JsStack newJsStack() {
        JsStack jsStack = new OverlayTypesParser().get();
        jsStack.init();
        return jsStack;
    }

    private final native void init()/*-{
        this.stack = [];
    }-*/;

    protected JsStack() {
        //
    }

    public final native void push(JsPoint point)/*-{
        this.stack.push(point);
    }-*/;

    public final native JsPoint pop()/*-{
        return this.stack.pop();
    }-*/;

    public final native boolean isEmpty()/*-{
        return this.stack.length == 0;
    }-*/;

}
