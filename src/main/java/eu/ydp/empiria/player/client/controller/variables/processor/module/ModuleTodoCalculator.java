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

package eu.ydp.empiria.player.client.controller.variables.processor.module;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

import java.util.logging.Logger;

public class ModuleTodoCalculator {

    private static final Logger LOGGER = Logger.getLogger(ModuleTodoCalculator.class.getName());

    public int calculateTodoForResponse(Response response) {
        CountMode countMode = response.getAppropriateCountMode();

        int todoCount;
        int correctAnswersCount = response.correctAnswers.getAnswersCount();

        if (countMode == CountMode.SINGLE) {
            boolean isSomethingToDo = correctAnswersCount > 0;
            if (isSomethingToDo) {
                todoCount = 1;
            } else {
                todoCount = 0;
            }
        } else if (countMode == CountMode.CORRECT_ANSWERS) {
            todoCount = correctAnswersCount;
        } else {
            todoCount = 1;
            LOGGER.warning("Unsupported TODO countMode: " + countMode);
        }
        return todoCount;
    }
}
