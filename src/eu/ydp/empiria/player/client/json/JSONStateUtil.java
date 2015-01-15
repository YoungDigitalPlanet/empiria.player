package eu.ydp.empiria.player.client.json;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class JSONStateUtil {

	public String extractString(JSONArray array) {
		return extractValue(array).isString().stringValue();
	}

	public JSONArray createWithString(String value) {
		JSONString jsonString = new JSONString(value);
		return createWithValue(jsonString);
	}

	private JSONValue extractValue(JSONArray array) {
		return array.get(0);
	}

	private JSONArray createWithValue(JSONValue value) {
		JSONArray state = new JSONArray();
		state.set(0, value);
		return state;
	}
}
