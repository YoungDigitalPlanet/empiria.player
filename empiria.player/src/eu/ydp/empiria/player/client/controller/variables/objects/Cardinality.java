package eu.ydp.empiria.player.client.controller.variables.objects;

public enum Cardinality {
		
	SINGLE, MULTIPLE, ORDERED, RECORD, COMMUTATIVE;
	
	public static Cardinality fromString(String key){
		if (key.toLowerCase().compareTo("single") == 0){
			return SINGLE;
		} else if (key.toLowerCase().compareTo("multiple") == 0){
			return MULTIPLE;
		} else if (key.toLowerCase().compareTo("ordered") == 0){
			return ORDERED;
		} else if (key.toLowerCase().compareTo("record") == 0){
			return RECORD;
		} else if (key.toLowerCase().compareTo("commutative") == 0){
			return COMMUTATIVE;
		}
		
		return SINGLE;
	}
}
