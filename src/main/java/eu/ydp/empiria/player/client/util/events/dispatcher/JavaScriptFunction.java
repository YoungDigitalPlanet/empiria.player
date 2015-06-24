package eu.ydp.empiria.player.client.util.events.dispatcher;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsType;

@JsType
public interface JavaScriptFunction {
	void callback(JavaScriptObject jso);
}
