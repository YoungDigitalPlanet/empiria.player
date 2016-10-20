package eu.ydp.empiria.player.client.controller.variables;

import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public abstract class VariablePossessorBase<V extends Variable> extends VariableProviderBase {

    protected Map<String, V> variables;

    public VariablePossessorBase() {
        variables = new TreeMap<>();
    }

    public V getVariable(String identifier) {
        return variables.get(identifier);
    }

    public void reset() {
        for (Variable currVariable : variables.values()) {
            currVariable.reset();
        }
    }

    @Override
    public Set<String> getVariableIdentifiers() {
        return variables.keySet();
    }

    @Override
    public Variable getVariableValue(String identifier) {
        return variables.get(identifier);
    }

    public boolean isLastAnswerSelectAction() {
        Set<String> identifiers = getVariableIdentifiers();
        for (String variableName : identifiers) {
            if (checkVariable(variableName)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkVariable(String variableName) {
        String lastchange = VariableName.LASTCHANGE.toString();
        if (variableName.endsWith(lastchange)) {
            V variable = variables.get(variableName);
            if (hasSelectChange(variable)) {
                return true;
            }

        }
        return false;
    }

    private boolean hasSelectChange(V variable) {
        for (String answer : variable.values) {
            if (!answer.equals("+") && answer.startsWith("+")) {
                return true;
            }
        }
        return false;
    }
}
