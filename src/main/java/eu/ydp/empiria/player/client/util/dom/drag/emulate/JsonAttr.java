package eu.ydp.empiria.player.client.util.dom.drag.emulate;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class JsonAttr extends JavaScriptObject {
    protected JsonAttr() {
        //
    }

    public final native void setAttrValue(String attrName, String value)/*-{
        this[attrName] = value;
    }-*/;

    public final native String getAttrValue(String attrName)/*-{
        return this[attrName];
    }-*/;

    public final String toJSON() {
        return new JSONObject(this).toString();
    }

}
