package eu.ydp.empiria.player.client.controller.variables.objects;

public enum BaseType {
	IDENTIFIER, BOOLEAN, INTEGER, STRING;

	public static BaseType fromString(String key) {
		
		BaseType baseType = STRING;
		
		if (key.equalsIgnoreCase(IDENTIFIER.name())) {
			baseType = IDENTIFIER;
		} else if (key.equalsIgnoreCase(BOOLEAN.name())) {
			baseType = BOOLEAN;
		} else if (key.equalsIgnoreCase(INTEGER.name())) {
			baseType = INTEGER;
		}

		return baseType;
	}
}
