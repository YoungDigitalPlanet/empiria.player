package eu.ydp.empiria.player.client.controller.extensions.internal.state.json;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaState;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaStateType;

public class EmpiriaStateDeserializerTest extends EmpiriaPlayerGWTTestCase{

    private EmpiriaStateDeserializer testObj = new EmpiriaStateDeserializer();

    public void testShouldDeserializeStateWithNewFormat() throws Exception {
        // given
        String givenState = "givenState";

        JSONObject givenStateObject = new JSONObject();
        givenStateObject.put(EmpiriaState.STATE, new JSONString(givenState));
        givenStateObject.put(EmpiriaState.TYPE, new JSONString("LZ_GWT"));

        // when
        EmpiriaState result = testObj.deserialize(givenStateObject);

        // then
        assertEquals(result.getFormatType(), EmpiriaStateType.LZ_GWT);
        assertEquals(result.getState(), givenState);
    }

    public void testShouldDeserializeStateWithDefaultType_whenStateIsInOldFormat() throws Exception {
        // given
        String givenOldState = "old state";
        JSONString oldState = new JSONString(givenOldState);

        // when
        EmpiriaState result = testObj.deserialize(oldState);

        // then
        assertEquals(result.getState(), oldState.toString());
        assertEquals(result.getFormatType(), EmpiriaStateType.DEFAULT);
    }

    public void testShouldDeserializeStateWithDefaultType_whenStateHasNoTypeField() throws Exception {
        String givenState = "givenState";
        JSONString stateString = new JSONString(givenState);

        JSONObject givenStateObject = new JSONObject();
        givenStateObject.put("some_field", stateString);

        // when
        EmpiriaState result = testObj.deserialize(givenStateObject);

        // then
        assertEquals(result.getState(), givenStateObject.toString());
    }
}