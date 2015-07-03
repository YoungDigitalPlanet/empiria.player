package eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js;

import com.google.gwt.core.client.JavaScriptObject;

public class TutorCommandJs extends JavaScriptObject {

    protected TutorCommandJs() {
    }

    public final native String getType()/*-{
        return this.type;
    }-*/;

    public final native String getAsset()/*-{
        return this.asset;
    }-*/;
}
