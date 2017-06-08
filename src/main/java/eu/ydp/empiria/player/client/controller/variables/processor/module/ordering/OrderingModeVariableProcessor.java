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

package eu.ydp.empiria.player.client.controller.variables.processor.module.ordering;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.VariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;

import java.util.ArrayList;
import java.util.List;

public class OrderingModeVariableProcessor implements VariableProcessor {

    @Override
    public int calculateErrors(Response response) {
        return 0;
    }

    @Override
    public int calculateDone(Response response) {
        List<String> allCorrectAnswers = getAllCorrectAnswers(response);
        List<String> allUserAnswers = response.values;

        for (int i = 0; i < allCorrectAnswers.size(); i++) {
            String correctAnswer = allCorrectAnswers.get(i);
            String userAnswer = allUserAnswers.get(i);

            if (!correctAnswer.equals(userAnswer)) {
                return 0;
            }
        }

        return 1;
    }

    @Override
    public LastMistaken checkLastmistaken(Response response, LastAnswersChanges answersChanges) {
        if (answersChanges.containChanges()) {
            int done = calculateDone(response);
            if (done == 1) {
                return LastMistaken.CORRECT;
            }
        }
        return LastMistaken.NONE;
    }

    @Override
    public int calculateMistakes(LastMistaken lastmistaken, int previousMistakes) {
        return 0;
    }

    @Override
    public List<Boolean> evaluateAnswers(Response response) {

        List<String> allCorrectAnswers = getAllCorrectAnswers(response);
        List<String> allUserAnswers = response.values;

        List<Boolean> result = new ArrayList<Boolean>();

        for (int i = 0; i < allCorrectAnswers.size(); i++) {
            String correctAnswer = allCorrectAnswers.get(i);
            String userAnswer = allUserAnswers.get(i);

            result.add(correctAnswer.equals(userAnswer));
        }
        return result;
    }

    private List<String> getAllCorrectAnswers(Response response) {
        CorrectAnswers correctAnswers = response.correctAnswers;
        return correctAnswers.getAllAnswers();
    }

}
