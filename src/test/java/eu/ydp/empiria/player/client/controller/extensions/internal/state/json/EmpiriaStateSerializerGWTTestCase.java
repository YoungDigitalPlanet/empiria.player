package eu.ydp.empiria.player.client.controller.extensions.internal.state.json;

import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaState;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaStateType;

public class EmpiriaStateSerializerGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private EmpiriaStateSerializer testObj = new EmpiriaStateSerializer();

    public void testShouldSerializeEmpiriaState() throws Exception {
        // GIVEN
        EmpiriaState givenEmpiriaState = new EmpiriaState(EmpiriaStateType.OLD, "givenState");

        // WHEN
        JSONValue result = testObj.serialize(givenEmpiriaState);

        // THEN
        assertNotNull(result.isObject());
        assertEquals(result.isObject().get(EmpiriaState.TYPE), new JSONString("OLD"));
        assertEquals(result.isObject().get(EmpiriaState.STATE), new JSONString("givenState"));
    }
}