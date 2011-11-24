package eu.ydp.empiria.player.client.controller.variables.objects;

public enum BaseType {
	IDENTIFIER, BOOLEAN, INTEGER, FLOAT, STRING, POINT, PAIR, DIRECTED_PAIR, DURATION, FILE, URL;
	
	public static BaseType fromString(String key){
		if (key.toLowerCase().compareTo("identifier") == 0){
			return IDENTIFIER;
		} else if (key.toLowerCase().compareTo("boolean") == 0){
			return BOOLEAN;
		} else if (key.toLowerCase().compareTo("interger") == 0){
			return INTEGER;
		} else if (key.toLowerCase().compareTo("float") == 0){
			return FLOAT;
		} else if (key.toLowerCase().compareTo("string") == 0){
			return STRING;
		} else if (key.toLowerCase().compareTo("point") == 0){
			return POINT;
		} else if (key.toLowerCase().compareTo("pair") == 0){
			return PAIR;
		} else if (key.toLowerCase().compareTo("directedPair") == 0){
			return DIRECTED_PAIR;
		} else if (key.toLowerCase().compareTo("duration") == 0){
			return DURATION;
		} else if (key.toLowerCase().compareTo("file") == 0){
			return FILE;
		} else if (key.toLowerCase().compareTo("url") == 0){
			return URL;
		}
		
		return STRING;
	}
}
