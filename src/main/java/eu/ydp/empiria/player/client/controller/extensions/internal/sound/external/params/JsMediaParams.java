package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.params;

import com.google.gwt.core.client.JavaScriptObject;

public final class JsMediaParams extends JavaScriptObject implements MediaParams {

    protected JsMediaParams() {
    }

    @Override
    public native int getDurationMillis() /*-{
        return this.duration;
    }-*/;

}
