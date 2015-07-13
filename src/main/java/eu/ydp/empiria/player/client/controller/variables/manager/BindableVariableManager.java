package eu.ydp.empiria.player.client.controller.variables.manager;

import eu.ydp.empiria.player.client.controller.variables.VariablePossessorBase;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

import java.util.Map;

public class BindableVariableManager<V extends Variable> extends VariablePossessorBase<V> {

    public BindableVariableManager(Map<String, V> outcomeVariablesMapToBind) {
        variables = outcomeVariablesMapToBind;
    }

}
