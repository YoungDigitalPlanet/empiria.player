package eu.ydp.empiria.player.client.module.external;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;

public class ExternalStateEncoderTest extends AbstractEmpiriaPlayerGWTTestCase {

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