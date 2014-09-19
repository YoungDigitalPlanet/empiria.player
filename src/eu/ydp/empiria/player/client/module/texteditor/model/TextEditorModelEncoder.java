package eu.ydp.empiria.player.client.module.texteditor.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;

public class TextEditorModelEncoder {

	public JSONArray encodeModel(TextEditorModel textEditorModel) {
		JSONArray jsonState = new JSONArray();
		JSONString value = new JSONString(textEditorModel.getContent());
		jsonState.set(0, value);
		return jsonState;
	}

	public TextEditorModel decodeModel(JSONArray newState) {
		String content = newState.get(0)
								 .isString()
								 .stringValue();

		return new TextEditorModel(content);
	}

}
