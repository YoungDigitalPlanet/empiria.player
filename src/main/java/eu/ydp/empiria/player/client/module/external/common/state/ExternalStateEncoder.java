package eu.ydp.empiria.player.client.module.external.common.state;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import static eu.ydp.empiria.player.client.module.connection.structure.StateController.STATE;

@Singleton
public class ExternalStateEncoder {

    private final ExternalFrameObjectFixer frameObjectFixer;

    @Inject
    public ExternalStateEncoder(ExternalFrameObjectFixer frameObjectFixer) {
        this.frameObjectFixer = frameObjectFixer;
    }

    public JSONArray encodeState(JavaScriptObject state) {

        JavaScriptObject javaScriptObject = frameObjectFixer.fix(state);
        JSONObject obj = new JSONObject(javaScriptObject);
        JSONArray jsonArray = new JSONArray();

        jsonArray.set(0, obj);
        return jsonArray;
    }

    public JavaScriptObject decodeState(JSONArray array) {
        if (array.size() > 0) {
            return getState(array.get(0));
        }

        return JavaScriptObject.createObject();
    }

    private JavaScriptObject getState(JSONValue jsonValue) {
        JSONObject state = jsonValue.isObject();

        if (state.containsKey(STATE)) {
            return state.get(STATE).isArray().get(0).isObject().getJavaScriptObject();
        }

        return state.getJavaScriptObject();
    }
}
