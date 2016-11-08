package eu.ydp.empiria.player.client.controller.extensions.internal.state.json;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaState;

import javax.inject.Singleton;

@Singleton
public class EmpiriaStateSerializer {

    public JSONValue serialize(EmpiriaState empiriaState) {

        String typeFormat = empiriaState.getFormatType().name();
        String state = empiriaState.getState();

        JSONObject stateObject = new JSONObject();
        stateObject.put(EmpiriaState.TYPE, new JSONString(typeFormat));
        stateObject.put(EmpiriaState.STATE, new JSONString(state));

        return stateObject;
    }

}
