package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;

public class ResultJsonReport extends JavaScriptObject {

    protected ResultJsonReport() {

    }

    public static ResultJsonReport create(String json) {
        return new OverlayTypesParser().get(json);
    }

    public static ResultJsonReport create() {
        return new OverlayTypesParser().get();
    }

    public final native void setTodo(int value) /*-{
        this.todo = value;
    }-*/;

    public final native void setDone(int value)/*-{
        this.done = value;
    }-*/;

    public final native void setResult(int value)/*-{
        this.result = value;
    }-*/;

    public final native void setErrors(int value)/*-{
        this.errors = value;
    }-*/;

    public final String getJSONString() {
        return new JSONObject(this).toString();
    }
}
