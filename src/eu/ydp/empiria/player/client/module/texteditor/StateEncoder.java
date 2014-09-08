package eu.ydp.empiria.player.client.module.texteditor;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;

class StateEncoder {
	public JSONArray encodeState(String content) {
		JSONArray state = new JSONArray();
		JSONString value = new JSONString(content);
		state.set(0, value);
		return state;
	}

	public String decodeState(JSONArray newState) {
		return newState.get(0)
					   .toString();
	}
}
