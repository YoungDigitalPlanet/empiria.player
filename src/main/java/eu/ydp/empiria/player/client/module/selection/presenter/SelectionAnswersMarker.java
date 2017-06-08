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

package eu.ydp.empiria.player.client.module.selection.presenter;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.core.answer.AnswersMarkingTemplate;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersType;
import eu.ydp.empiria.player.client.module.selection.model.GroupAnswersControllerModel;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SelectionAnswersMarker extends AnswersMarkingTemplate {

    private final GroupAnswersControllerModel answersControllerModel;

    @Inject
    public SelectionAnswersMarker(@ModuleScoped GroupAnswersControllerModel answersControllerModel) {
        this.answersControllerModel = answersControllerModel;
    }

    @Override
    public void unmarkWrong() {
        unMark(MarkAnswersType.WRONG);
    }

    @Override
    public void markWrong() {
        applyAnswerTypeToCollection(UserAnswerType.WRONG, answersControllerModel.getButtonsToMarkForType(MarkAnswersType.WRONG));
        applyAnswerTypeToCollection(UserAnswerType.NONE, answersControllerModel.getNotSelectedAnswers());
    }

    @Override
    public void unmarkCorrect() {
        unMark(MarkAnswersType.CORRECT);
    }

    @Override
    public void markCorrect() {
        applyAnswerTypeToCollection(UserAnswerType.CORRECT, answersControllerModel.getButtonsToMarkForType(MarkAnswersType.CORRECT));
        applyAnswerTypeToCollection(UserAnswerType.NONE, answersControllerModel.getNotSelectedAnswers());
    }

    @Override
    public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
        super.markAnswers(type, mode);
    }

    private void unMark(MarkAnswersType type) {
        List<SelectionAnswerDto> buttonsToMarkDefaultState = new ArrayList<SelectionAnswerDto>(answersControllerModel.getButtonsToMarkForType(type));
        buttonsToMarkDefaultState.addAll(answersControllerModel.getNotSelectedAnswers());
        applyAnswerTypeToCollection(UserAnswerType.DEFAULT, buttonsToMarkDefaultState);
    }

    private void applyAnswerTypeToCollection(UserAnswerType userAnswerType, Collection<SelectionAnswerDto> answers) {
        for (SelectionAnswerDto selectionAnswerDto : answers) {
            selectionAnswerDto.setSelectionAnswerType(userAnswerType);
        }
    }
}
