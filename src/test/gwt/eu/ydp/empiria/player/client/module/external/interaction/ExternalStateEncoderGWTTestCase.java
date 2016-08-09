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
        JSONObject jsonObject = new JSONObject();
        JSONArray arr = new JSONArray();

        JSONArray firstChild = new JSONArray();
        firstChild.set(0, new JSONNumber(200));
        firstChild.set(1, new JSONNumber(0));
        arr.set(0, firstChild);

        JSONArray secondChild = new JSONArray();
        secondChild.set(0, new JSONNumber(50));
        secondChild.set(1, new JSONNumber(20));
        arr.set(1, secondChild);

        jsonObject.put("arr", arr);
        jsonObject.put("done", new JSONString("true"));

        JavaScriptObject javaScriptObject = getObject();
        JSONObject result = new JSONObject(javaScriptObject);

        JavaScriptObject jsObject = JavaScriptObject.createObject();
        int expectedArraySize = 1;

        // when
        JSONArray jsonArray = testObj.encodeState(javaScriptObject);

        // then
        assertEquals(expectedArraySize, jsonArray.size());
        JavaScriptObject jsObjectResult = jsonArray.get(0).isObject().getJavaScriptObject();

        assertEquals(jsObject, jsObjectResult);
    }

    public native JavaScriptObject getObject() /*-{

        var arr = new Array();

        var child1 = new Array();
        child1.push(100);
        child1.push(0);

        var child2 = new Array();
        child2.push(5000);
        child2.push(70);

        arr.push(child1);
        arr.push(child2);



        return {
            "arr": arr,
            "done": 1
        }
    }-*/;

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
