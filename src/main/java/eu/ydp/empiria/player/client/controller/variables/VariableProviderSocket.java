package eu.ydp.empiria.player.client.controller.variables;

import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

import java.util.Set;

public interface VariableProviderSocket {

    Set<String> getVariableIdentifiers();

    Variable getVariableValue(String identifier);

}
