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

package eu.ydp.empiria.player.client.controller.extensions.internal.stickies;

import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.gwtutil.client.geom.Point;

public class StickieProperties extends JavaScriptObject implements IStickieProperties {

    private static final int OUT_OF_THE_SCREEN_COORDINATE = -2000;

    protected StickieProperties() {
    }

    public static StickieProperties newInstance() {
        StickieProperties sp = JavaScriptObject.createObject().cast();
        sp.setColorIndex(-1);
        sp.setMinimized(false);
        sp.setStickieTitle("");
        sp.setStickieContent("");
        sp.setPosition(OUT_OF_THE_SCREEN_COORDINATE, OUT_OF_THE_SCREEN_COORDINATE);
        return sp;
    }

    @Override
    public final native int getColorIndex() /*-{
        return this.colorIndex;
    }-*/;

    @Override
    public final native void setColorIndex(int colorIndex) /*-{
        this.colorIndex = colorIndex;
    }-*/;

    @Override
    public final native String getStickieTitle() /*-{
        return this.stickieTitle;
    }-*/;

    @Override
    public final native void setStickieTitle(String stickieTitle) /*-{
        this.stickieTitle = stickieTitle;
    }-*/;

    @Override
    public final native String getStickieContent() /*-{
        return this.stickieContent;
    }-*/;

    @Override
    public final native void setStickieContent(String bookmarkContent) /*-{
        this.stickieContent = bookmarkContent;
    }-*/;

    @Override
    public final native int getX() /*-{
        return this.x;
    }-*/;

    @Override
    public final native void setX(int x) /*-{
        this.x = x;
    }-*/;

    @Override
    public final native int getY() /*-{
        return this.y;
    }-*/;

    @Override
    public final native void setY(int y) /*-{
        this.y = y;
    }-*/;

    @Override
    public final native boolean isMinimized() /*-{
        return this.minimized;
    }-*/;

    @Override
    public final native void setMinimized(boolean minimized) /*-{
        this.minimized = minimized;
    }-*/;

    public final native void setTimestamp(double ts)/*-{
        this.timestamp = ts;
    }-*/;

    public final native double getTimestamp()/*-{
        return this.timestamp;
    }-*/;

    @Override
    public final native void updateTimestamp() /*-{
        this.timestamp = new Date().getTime();
    }-*/;

    public final native void setPosition(int x, int y) /*-{
        this.x = x;
        this.y = y;
    }-*/;

    @Override
    public final Point<Integer> getPosition() {
        return new Point<Integer>(getX(), getY());
    }

    @Override
    public final void setPosition(Point<Integer> newPosition) {
        setX(newPosition.getX());
        setY(newPosition.getY());
    }

}
