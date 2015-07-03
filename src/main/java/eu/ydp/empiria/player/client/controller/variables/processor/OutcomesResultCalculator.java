package eu.ydp.empiria.player.client.controller.variables.processor;

import com.google.inject.Inject;
import eu.ydp.gwtutil.client.debug.gwtlogger.Logger;

public class OutcomesResultCalculator {

    private static final int RESULT_MIN = 0;
    private static final int RESULT_MAX = 100;

    @Inject
    private Logger logger;

    public int calculateResult(int todo, int done) {
        checkArguments(todo, done);
        if (todo == 0) {
            return RESULT_MIN;
        }
        return done * RESULT_MAX / todo;
    }

    private void checkArguments(int todo, int done) {
        if (done > todo || done < 0) {
            logger.severe("Problem calculating result. Done=" + done + ", todo=" + todo + ".");
        }
    }
}
