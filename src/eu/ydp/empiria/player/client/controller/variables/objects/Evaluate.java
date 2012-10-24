package eu.ydp.empiria.player.client.controller.variables.objects;

/**
 * Used with {@link Cardinality#MULTIPLE} to define the method of processing response (user answers / correct answers / default for the module).
 */
public enum Evaluate {

	CORRECT, USER, DEFAULT;
	
	public static Evaluate fromString(String key) {
		
		Evaluate evaluate = DEFAULT;
		
		if (key.toLowerCase().compareTo("user") == 0) {
			evaluate = USER;
		}
		
		return evaluate;
	}
	
}
