package eu.ydp.empiria.player.client.module.external.interaction;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateEncoder;

public class ExternalStateEncoderGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private ExternalStateEncoder testObj = new ExternalStateEncoder();

    public void testShouldWrapStateInJSONArray() {
        // given

        JavaScriptObject jsObject = JavaScriptObject.createObject();
        int expectedArraySize = 1;

        // when
        JSONArray jsonArray = testObj.encodeState(jsObject);

        // then
        assertEquals(expectedArraySize, jsonArray.size());
        JavaScriptObject jsObjectResult = jsonArray.get(0).isObject().getJavaScriptObject();

        assertEquals(jsObject, jsObjectResult);
    }

    public void testShouldUnwrapStateFromArrayToJSO() {
        // given
        JavaScriptObject state = JavaScriptObject.createObject();
        JSONObject obj = new JSONObject(state);
        JSONArray jsonArray = new JSONArray();
        jsonArray.set(0, obj);

        // when
        JavaScriptObject jsObjectResult = testObj.decodeState(jsonArray);

        // then
        assertEquals(state, jsObjectResult);
    }
}
