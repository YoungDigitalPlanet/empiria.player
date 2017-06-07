package eu.ydp.empiria.player.client.controller.extensions.internal.state.json;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaState;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.EmpiriaStateType;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Singleton;

@Singleton
public class EmpiriaStateDeserializer {

    public EmpiriaState deserialize(JSONValue stateJson) {

        JSONObject stateObject = stateJson.isObject();

        if (isNewStateObject(stateJson)) {
            EmpiriaStateType type = getStateType(stateObject);
            String state = stateObject.get(EmpiriaState.STATE).isString().stringValue();

            return new EmpiriaState(type, state, getSavedLessonIdentifier(stateObject));
        }

        return new EmpiriaState(EmpiriaStateType.OLD, stateJson.toString(), StringUtils.EMPTY);
    }

    private boolean isNewStateObject(JSONValue stateJson) {
        return stateJson.isObject() != null && stateJson.isObject().containsKey(EmpiriaState.TYPE);
    }

    private EmpiriaStateType getStateType(JSONObject jsonObject) {
        String typeValue = jsonObject.get(EmpiriaState.TYPE).isString().stringValue();

        for (EmpiriaStateType stateType : EmpiriaStateType.values()) {
            if (stateType.name().equals(typeValue)) {
                return stateType;
            }
        }

        return EmpiriaStateType.UNKNOWN;
    }

    private String getSavedLessonIdentifier(JSONObject jsonObject) {
        boolean hasIdentifier = jsonObject.containsKey(EmpiriaState.LESSON_IDENTIFIER);
        if(hasIdentifier) {
            return jsonObject.get(EmpiriaState.LESSON_IDENTIFIER).isString().stringValue();
        }

        return StringUtils.EMPTY;
    }
}
