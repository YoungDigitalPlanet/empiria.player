package eu.ydp.empiria.player.client.controller.variables;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;

public abstract class VariablePossessorBase<V extends Variable> extends VariableProviderBase {

	public Map<String, V> variables;

	public VariablePossessorBase() {
		variables = new TreeMap<String, V>();
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
		Set<String> keySet = variables.keySet();
		for (String variableName : keySet) {
			String lastchange = VariableName.LASTCHANGE.toString();
			if (variableName.endsWith(lastchange)) {
				V variable = variables.get(variableName);
				if (hasSelectChanges(variable.values)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean hasSelectChanges(List<String> values) {
		for (String answer : values) {
			if (!answer.equals("+") && answer.startsWith("+")) {
				return true;
			}
		}
		return false;
	}

	public Map<String, V> getVariablesMap() {
		return variables;
	}

}
