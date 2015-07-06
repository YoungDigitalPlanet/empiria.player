package eu.ydp.empiria.player.client.util.events.external;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsType;

@JsType
public interface ExternalCallback {
	void callback(JavaScriptObject jso);
}
