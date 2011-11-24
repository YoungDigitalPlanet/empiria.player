package eu.ydp.empiria.player.client.model;

public interface ItemVariablesAccessor {
	String getResponseVariables();
	String getResponseVariableValue(String var);
	String getResponseVariableCardinality(String var);
	String getResponseVariableBaseType(String var);
}
