package eu.ydp.empiria.player.client.controller.variables.objects;

/**
 * Used with {@link Cardinality#MULTIPLE} to define the method of processing response (user answers / correct answers / default for the module).
 */
public enum Evaluate {

    CORRECT, USER, DEFAULT;

    public static Evaluate fromString(String key) {

        Evaluate evaluate = DEFAULT;

        if (key != null) {
            if (key.equalsIgnoreCase(USER.toString())) {
                evaluate = USER;
            } else if (key.equalsIgnoreCase(CORRECT.toString())) {
                evaluate = CORRECT;
            }
        }

        return evaluate;
    }

}
