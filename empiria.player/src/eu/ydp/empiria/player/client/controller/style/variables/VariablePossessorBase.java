package eu.ydp.empiria.player.client.controller.style.variables;

import java.util.HashMap;
import java.util.Set;

import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

public abstract class VariablePossessorBase<V extends Variable> extends VariableProviderBase  {
	
	public HashMap<String, V> variables = new HashMap<String, V>();
	
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

}
