package eu.ydp.empiria.player.client.controller.variables.objects;

public enum BaseType {
	IDENTIFIER, BOOLEAN, INTEGER, STRING,
	
	/**
	 * TODO: To remove this BaseType along with the associated implementation;
	 * seems to be not important
	 */	
	@Deprecated
	FLOAT,
	@Deprecated
	POINT,
	@Deprecated
	PAIR,
	@Deprecated
	DIRECTED_PAIR,
	@Deprecated
	DURATION,
	@Deprecated
	FILE,
	@Deprecated
	URL,
	@Deprecated
	DEPRECATED;
	
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
