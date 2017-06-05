package eu.ydp.empiria.player.client.controller.extensions.internal.state.json;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaState;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaStateType;

public class EmpiriaStateDeserializerGWTTestCase extends EmpiriaPlayerGWTTestCase {

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

    public void testShouldDeserializeStateWithDefaultType_andIdentifier_whenStateIsInOldFormat() throws Exception {
        // given
        String givenOldState = "old state";
        JSONString oldState = new JSONString(givenOldState);

        // when
        EmpiriaState result = testObj.deserialize(oldState);

        // then
        assertEquals(result.getState(), oldState.toString());
        assertEquals(result.getFormatType(), EmpiriaStateType.OLD);
        assertEquals(result.getLessonIdentifier(), "");
    }

    public void testShouldDeserializeStateWithDefaultType_whenStateHasNoTypeField() throws Exception {
        // given
        String givenState = "givenState";
        JSONString stateString = new JSONString(givenState);

        JSONObject givenStateObject = new JSONObject();
        givenStateObject.put("some_field", stateString);

        // when
        EmpiriaState result = testObj.deserialize(givenStateObject);

        // then
        assertEquals(result.getState(), givenStateObject.toString());
    }

    public void testShouldReturnEmptyIdentifier_whenItsMissing() throws Exception {
        // given
        String givenState = "givenState";

        JSONObject givenStateObject = new JSONObject();
        givenStateObject.put(EmpiriaState.STATE, new JSONString(givenState));
        givenStateObject.put(EmpiriaState.TYPE, new JSONString("LZ_GWT"));

        // when
        EmpiriaState result = testObj.deserialize(givenStateObject);

        // then
        assertEquals(result.getState(), givenState);
        assertEquals(result.getLessonIdentifier(), "");
    }

    public void testShouldReturnIdentifierFromState() throws Exception {
        // given
        String givenState = "givenState";
        String identifier = "identifier";

        JSONObject givenStateObject = new JSONObject();
        givenStateObject.put(EmpiriaState.STATE, new JSONString(givenState));
        givenStateObject.put(EmpiriaState.LESSON_IDENTIFIER, new JSONString(identifier));
        givenStateObject.put(EmpiriaState.TYPE, new JSONString("LZ_GWT"));

        // when
        EmpiriaState result = testObj.deserialize(givenStateObject);

        // then
        assertEquals(result.getState(), givenState);
        assertEquals(result.getLessonIdentifier(), identifier);
    }
}