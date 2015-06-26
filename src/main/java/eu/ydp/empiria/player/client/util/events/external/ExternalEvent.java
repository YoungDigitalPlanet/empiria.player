package eu.ydp.empiria.player.client.util.events.external;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public abstract class ExternalEvent {

	public static final String EVENT_TYPE = "type";
	public static final String EVENT_PAYLOAD = "payload";
	private final String type;

	protected ExternalEvent(String type) {
		this.type = type;
	}

	protected abstract JSONObject getPayload();

	public JavaScriptObject getJSONObject() {
		JSONObject jsonEvent = new JSONObject();
		jsonEvent.put(EVENT_TYPE, new JSONString(type));

		JavaScriptObject jsPayload = getPayload().getJavaScriptObject();
		jsonEvent.put(EVENT_PAYLOAD, new JSONObject(jsPayload));

		return jsonEvent.getJavaScriptObject();
	}
}

