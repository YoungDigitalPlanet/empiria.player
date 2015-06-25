package eu.ydp.empiria.player.client.util.events.external;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public abstract class ExternalEvent {

	private final String type;

	protected ExternalEvent(String type) {
		this.type = type;
	}

	protected abstract JSONObject getPayload();

	public JavaScriptObject getJSONObject() {
		JSONObject jsonEvent = new JSONObject();
		jsonEvent.put("type", new JSONString(type));

		JavaScriptObject jsPayload = getPayload().getJavaScriptObject();
		jsonEvent.put("payload", new JSONObject(jsPayload));

		return jsonEvent.getJavaScriptObject();
	}
}

