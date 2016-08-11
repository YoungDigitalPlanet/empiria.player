package eu.ydp.empiria.player.client.module.external.interaction;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalFrameObjectFixer;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateEncoder;

public class ExternalStateEncoderGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private ExternalStateEncoder testObj = new ExternalStateEncoder(new ExternalFrameObjectFixer());

    public void testShouldWrapStateInJSONArray() {
        // given
        String expectedKey = "test";
        double expectedNumberValue = 15.0;
        JSONObject givenObject = new JSONObject();
        givenObject.put(expectedKey, new JSONNumber(expectedNumberValue));

        JavaScriptObject jsObject = givenObject.getJavaScriptObject();
        int expectedArraySize = 1;

        // when
        JSONArray jsonArray = testObj.encodeState(jsObject);

        // then
        assertEquals(expectedArraySize, jsonArray.size());
        JavaScriptObject jsObjectResult = jsonArray.get(0).isObject().getJavaScriptObject();

        JSONObject jsonObjectResult = new JSONObject(jsObjectResult);

        assertEquals(jsonObjectResult.keySet().size(), 1);
        assertTrue(jsonObjectResult.containsKey(expectedKey));

        JSONValue valueResult = jsonObjectResult.get(expectedKey);
        assertNotNull(valueResult.isNumber());

        assertEquals(valueResult.isNumber().doubleValue(), expectedNumberValue);
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

    public void testShouldGetStateObject() {
        // given
        JavaScriptObject state = JavaScriptObject.createObject();
        JSONArray stateArray = new JSONArray();
        stateArray.set(0, new JSONObject(state));

        JSONObject stateObject = new JSONObject();
        stateObject.put("STATE", stateArray);

        JSONArray jsonArray = new JSONArray();
        jsonArray.set(0, stateObject);

        // when
        JavaScriptObject result = testObj.decodeState(jsonArray);

        // then
        assertEquals(result, state);
    }
}
