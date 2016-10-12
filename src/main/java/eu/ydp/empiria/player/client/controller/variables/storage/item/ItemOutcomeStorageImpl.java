package eu.ydp.empiria.player.client.controller.variables.storage.item;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import eu.ydp.empiria.player.client.controller.variables.VariablePossessorBase;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;

import java.util.HashMap;
import java.util.Map;

public class ItemOutcomeStorageImpl extends VariablePossessorBase<Outcome> {

    public ItemOutcomeStorageImpl() {
        variables = new HashMap<>();
    }

    public void importFromMap(Map<String, Outcome> newValues) {
        variables.putAll(newValues);
    }

    public void putVariable(String key, Outcome variable) {
        variables.put(key, variable);
    }

    public JSONValue toJSON() {
        JSONArray stateArr = new JSONArray();
        int i = 0;
        for (Outcome value : variables.values()) {
            stateArr.set(i, value.toJSON());
            i++;
        }
        return stateArr;
    }

    @SuppressWarnings("unchecked")
    public void fromJSON(JSONValue json) {

        JSONArray jsonArr = json.isArray();

        if (jsonArr != null) {
            for (int i = 0; i < jsonArr.size(); i++) {
                String type = jsonArr.get(i).isArray().get(0).isString().stringValue();
                if (type.equals(Outcome.OUTCOME)) {
                    Outcome o = new Outcome();
                    o.fromJSON(jsonArr.get(i));
                    variables.put(o.identifier, o);
                }
            }
        }
    }
}
