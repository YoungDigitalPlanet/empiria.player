package eu.ydp.empiria.player.client.controller.variables;

import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.gwtutil.client.NumberUtils;

public class VariableUtil {

    private VariableProviderSocket variableProvider;

    public VariableUtil(VariableProviderSocket variableProvider) {
        this.variableProvider = variableProvider;
    }

    public String getVariableValue(String name, String defaultValue) {
        Variable var = variableProvider.getVariableValue(name);
        String value = defaultValue;
        if (var != null) {
            value = var.getValuesShort();
        }
        return value;
    }

    public int getVariableIntValue(String name, int defaultValue) {
        String defaultValueString = String.valueOf(defaultValue);
        String variableValue = getVariableValue(name, defaultValueString);
        return NumberUtils.tryParseInt(variableValue, defaultValue);
    }
}
