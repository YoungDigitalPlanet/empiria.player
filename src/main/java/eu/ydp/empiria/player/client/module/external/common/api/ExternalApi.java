package eu.ydp.empiria.player.client.module.external.common.api;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsType;

@JsType
public interface ExternalApi {

    void setStateOnExternal(JavaScriptObject state);

    JavaScriptObject getStateFromExternal();

    void reset();

    void lock();

    void unlock();
}
