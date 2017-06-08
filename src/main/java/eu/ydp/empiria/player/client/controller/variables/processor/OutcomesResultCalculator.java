/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
