package eu.ydp.empiria.player.client.controller.variables.objects;

public enum BaseType {
	IDENTIFIER, BOOLEAN, INTEGER, FLOAT, STRING, POINT, PAIR, DIRECTED_PAIR, DURATION, FILE, URL;

	public static BaseType fromString(String key) {
		if (key.equalsIgnoreCase(IDENTIFIER.name())) {
			return IDENTIFIER;
		} else if (key.equalsIgnoreCase("BOOLEAN")) {
			return BOOLEAN;
		} else if (key.equalsIgnoreCase("INTEGER")) {
			return INTEGER;
		} else if (key.equalsIgnoreCase("FLOAT")) {
			return FLOAT;
		} else if (key.equalsIgnoreCase("STRING")) {
			return STRING;
		} else if (key.equalsIgnoreCase("POINT")) {
			return POINT;
		} else if (key.equalsIgnoreCase("PAIR")) {
			return PAIR;
		} else if (key.equalsIgnoreCase("DIRECTEDPAIR")) {
			return DIRECTED_PAIR;
		} else if (key.equalsIgnoreCase("DURATION")) {
			return DURATION;
		} else if (key.equalsIgnoreCase("FILE")) {
			return FILE;
		} else if (key.equalsIgnoreCase("URL")) {
			return URL;
		}

		return STRING;
	}
}
