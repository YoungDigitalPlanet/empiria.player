package eu.ydp.empiria.player.client.util.events.pagechange;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public abstract class Event {
	private final String type;

	protected Event(String type) {
		this.type = type;
	}

	public abstract JSONObject getPayload();

	public JavaScriptObject getJSObject() {
		JSONObject jsonWithTypeAndPayload = new JSONObject();
		jsonWithTypeAndPayload.put("type", new JSONString(type));
		jsonWithTypeAndPayload.put("payload", new JSONObject(this.getPayload().getJavaScriptObject()));
		return jsonWithTypeAndPayload.getJavaScriptObject();
	}
}

