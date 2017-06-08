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

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;

import java.util.List;

public class ResponseDifferenceFinder {

    public LastAnswersChanges findChangesOfAnswers(List<String> previousAnswers, List<String> currentAnswers) {
        List<String> addedAnswers = findAddedAnswers(previousAnswers, currentAnswers);
        List<String> removedAnswers = findRemovedAnswers(previousAnswers, currentAnswers);

        LastAnswersChanges answerChanges = new LastAnswersChanges(addedAnswers, removedAnswers);
        return answerChanges;
    }

    private List<String> findAddedAnswers(List<String> previousAnswers, List<String> currentAnswers) {
        List<String> addedAnswers = Lists.newArrayList();
        for (String currentAnswer : currentAnswers) {
            if (isValidAnswer(currentAnswer) && isNewAnswer(previousAnswers, currentAnswer)) {
                addedAnswers.add(currentAnswer);
            }
        }
        return addedAnswers;
    }

    private List<String> findRemovedAnswers(List<String> previousAnswers, List<String> currentAnswers) {
        List<String> removedAnswers = Lists.newArrayList();
        for (String previousAnswer : previousAnswers) {
            if (isValidAnswer(previousAnswer) && wasRemoved(previousAnswer, currentAnswers)) {
                removedAnswers.add(previousAnswer);
            }
        }
        return removedAnswers;
    }

    private boolean wasRemoved(String previousAnswer, List<String> currentAnswers) {
        boolean isPresentInCurrentAnswers = currentAnswers.contains(previousAnswer);
        return !isPresentInCurrentAnswers;
    }

    private boolean isNewAnswer(List<String> previousAnswers, String currentAnswer) {
        return !previousAnswers.contains(currentAnswer);
    }

    private boolean isValidAnswer(String currentAnswer) {
        boolean isNotNullOrEmpty = !Strings.isNullOrEmpty(currentAnswer);
        return isNotNullOrEmpty;
    }

}
