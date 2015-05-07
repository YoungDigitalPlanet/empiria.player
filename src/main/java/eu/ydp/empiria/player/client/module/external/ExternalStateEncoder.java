package eu.ydp.empiria.player.client.module.external;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

public class ExternalStateEncoder {

	public JSONArray encodeState(JavaScriptObject state) {
		JSONObject obj = new JSONObject(state);
		JSONArray jsonArray = new JSONArray();

		jsonArray.set(0, obj);
		return jsonArray;
	}

	public JavaScriptObject decodeState(JSONArray array) {
		if (array.size() > 0) {
			JSONValue jsonValue = array.get(0);
			return jsonValue.isObject().getJavaScriptObject();
		}

		return JavaScriptObject.createObject();
	}
}
