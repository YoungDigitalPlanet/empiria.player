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

package eu.ydp.empiria.player.client.controller.variables.processor.module.grouped;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

import java.util.Collection;
import java.util.List;

public class ResponseAnswerGrouper {

    private List<GroupedAnswer> groupedAnswers;

    public ResponseAnswerGrouper(List<GroupedAnswer> groupedAnswers) {
        this.groupedAnswers = groupedAnswers;
    }

    public boolean isAnswerCorrect(String answer, Response response) {
        Collection<GroupedAnswer> fittingAnswers = findAllGroupedAnswersByValue(answer);
        boolean isAnswerCorrect;
        if (fittingAnswers.isEmpty()) {
            isAnswerCorrect = false;
        } else {
            isAnswerCorrect = checkIfIsAnswerCorrectForGivenResponse(fittingAnswers, response);
        }

        return isAnswerCorrect;
    }

    private boolean checkIfIsAnswerCorrectForGivenResponse(Collection<GroupedAnswer> fittingAnswers, Response response) {
        boolean isAnswerCorrect;
        boolean anyAnswerAlreadyUsedByThisResponse = isAnyAnswerAlreadyUsedByThisResponse(fittingAnswers, response);
        if (anyAnswerAlreadyUsedByThisResponse) {
            isAnswerCorrect = true;
        } else {
            List<GroupedAnswer> answersToUse = findAnswersThatCanBeUsed(fittingAnswers);
            if (answersToUse.size() > 0) {
                useAnyOfAnswers(answersToUse, response);
                isAnswerCorrect = true;
            } else {
                isAnswerCorrect = false;
            }
        }
        return isAnswerCorrect;
    }

    private List<GroupedAnswer> findAnswersThatCanBeUsed(Collection<GroupedAnswer> answers) {
        List<GroupedAnswer> answersToUse = Lists.newArrayList();

        for (GroupedAnswer answer : answers) {

            if (isUnusedAnswer(answer) || canBeReused(answer)) {
                answersToUse.add(answer);
            }
        }
        return answersToUse;
    }

    private boolean canBeReused(GroupedAnswer answer) {
        Response usedByResponse = answer.getUsedByResponse();
        boolean isNotUsedAnymore = !usedByResponse.values.contains(answer.getValue());
        return isNotUsedAnymore;
    }

    private boolean isUnusedAnswer(GroupedAnswer answer) {
        return !answer.isUsed();
    }

    private void useAnyOfAnswers(List<GroupedAnswer> answersToUse, Response response) {
        GroupedAnswer answer = answersToUse.get(0);
        answer.setUsed(true);
        answer.setUsedByResponse(response);
    }

    private boolean isAnyAnswerAlreadyUsedByThisResponse(Collection<GroupedAnswer> fittingAnswers, Response response) {
        for (GroupedAnswer fittingAnswer : fittingAnswers) {
            String currentResponseId = response.getID();
            Response usedByResponse = fittingAnswer.getUsedByResponse();

            if (usedByResponse != null) {
                String usingResponseId = usedByResponse.getID();

                if (usingResponseId.equals(currentResponseId)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Collection<GroupedAnswer> findAllGroupedAnswersByValue(final String value) {

        Collection<GroupedAnswer> answersWithValue = Collections2.filter(groupedAnswers, new Predicate<GroupedAnswer>() {
            @Override
            public boolean apply(GroupedAnswer groupedAnswer) {
                return value.equals(groupedAnswer.getValue());
            }
        });

        return answersWithValue;
    }

}
