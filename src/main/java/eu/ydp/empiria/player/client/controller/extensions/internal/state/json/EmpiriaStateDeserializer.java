package eu.ydp.empiria.player.client.controller.extensions.internal.state.json;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaState;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaStateType;

import javax.inject.Singleton;

@Singleton
public class EmpiriaStateDeserializer {

    public EmpiriaState deserialize(JSONValue stateJson) {

        JSONObject stateObject = stateJson.isObject();

        if (isNewStateObject(stateJson)) {
            EmpiriaStateType type = getStateType(stateObject);
            String state = stateObject.get(EmpiriaState.STATE).isString().stringValue();

            return new EmpiriaState(type, state);
        }

        return new EmpiriaState(EmpiriaStateType.DEFAULT, stateJson.toString());
    }

    private boolean isNewStateObject(JSONValue stateJson) {
        return stateJson.isObject() != null && stateJson.isObject().containsKey(EmpiriaState.TYPE);
    }

    private EmpiriaStateType getStateType(JSONObject jsonObject) {
        String typeValue = jsonObject.get(EmpiriaState.TYPE).isString().stringValue();
        return EmpiriaStateType.valueOf(typeValue);
    }
}
