package eu.ydp.empiria.player.client.module.external.common.state;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.query.client.impl.ConsoleBrowser;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.BrowserLogger;
import eu.ydp.empiria.player.client.module.connection.structure.StateController;

import static eu.ydp.empiria.player.client.module.connection.structure.StateController.STATE;

@Singleton
public class ExternalStateEncoder {

    public JSONArray encodeState(JavaScriptObject state) {

        JavaScriptObject javaScriptObject = fixObjectFromFrame(state);
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
        BrowserLogger.log(jsonValue);
        JSONObject state = jsonValue.isObject();
        BrowserLogger.log(state);

        if (state.containsKey(STATE)) {
            BrowserLogger.log("ifififif");
            return state.get(STATE).isArray().get(0).isObject().getJavaScriptObject();
        }

        return state.getJavaScriptObject();
    }


    private native JavaScriptObject fixObjectFromFrame(JavaScriptObject object) /*-{
        return JSON.parse(JSON.stringify(object));
    }-*/;
}
