package eu.ydp.empiria.player.client.controller.variables;

import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

import java.util.Set;

public interface VariableProviderSocket {

    public abstract Set<String> getVariableIdentifiers();

    public abstract Variable getVariableValue(String identifier);

}
