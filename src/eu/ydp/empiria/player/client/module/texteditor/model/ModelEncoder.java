package eu.ydp.empiria.player.client.module.texteditor.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;

public class ModelEncoder {

	public JSONArray encodeModel(Model model) {
		JSONArray jsonState = new JSONArray();
		JSONString value = new JSONString(model.getContent());
		jsonState.set(0, value);
		return jsonState;
	}

	public Model decodeModel(JSONArray newState) {
		String content = newState.get(0)
								 .toString();

		return new Model(content);
	}

}
