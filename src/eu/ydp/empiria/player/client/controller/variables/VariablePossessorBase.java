package eu.ydp.empiria.player.client.controller.variables;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

public abstract class VariablePossessorBase<V extends Variable> extends VariableProviderBase  {
	
	public Map<String, V> variables;
	
	public VariablePossessorBase(){
		variables = new TreeMap<String, V>();
	}
	
	public V getVariable(String identifier){
		return variables.get(identifier);
	}
	
	public void reset(){
		for(Variable currVariable: variables.values())
			currVariable.reset();
	}

	@Override
	public Set<String> getVariableIdentifiers() {
		return variables.keySet();
	}

	@Override
	public Variable getVariableValue(String identifier) {
		return variables.get(identifier);
	}
	
	public Map<String, V> getVariablesMap(){
		return variables;
	}

}
