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

package eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js;

import com.google.gwt.core.client.JavaScriptObject;

public abstract class TutorJs extends JavaScriptObject {

    protected TutorJs() {
    }

    public final native String getName()/*-{
        return this.name;
    }-*/;

    public final native int getFps()/*-{
        return this.fps;
    }-*/;

    public final native int getWidth()/*-{
        return this.width;
    }-*/;

    public final native int getHeight()/*-{
        return this.height;
    }-*/;

    public final native boolean isInteractive()/*-{
        return !!this.interactive;
    }-*/;

    public final native String getAvatar()/*-{
        return this.avatar;
    }-*/;
}
