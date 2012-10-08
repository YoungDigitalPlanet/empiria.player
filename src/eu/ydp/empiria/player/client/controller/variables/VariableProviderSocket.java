package eu.ydp.empiria.player.client.controller.variables;

import java.util.Set;

import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

public interface VariableProviderSocket {

	public abstract Set<String> getVariableIdentifiers();
	
	public abstract Variable getVariableValue(String identifier);

}
