package eu.ydp.empiria.player.client.module.external.common.api;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class ExternalApiNullObject implements ExternalApi {

    @Override
    public void setStateOnExternal(JavaScriptObject state) {
    }

    @Override
    public JavaScriptObject getStateFromExternal() {
        return new JSONObject().getJavaScriptObject();
    }

    @Override
    public void reset() {
    }

    @Override
    public void lock() {
    }

    @Override
    public void unlock() {
    }
}
