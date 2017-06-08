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

package eu.ydp.empiria.player.client.module.selection.controller;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import java.util.*;
import java.util.logging.*;

public class GroupAnswersController {

    private static final Logger LOGGER = Logger.getLogger(GroupAnswersController.class.getName());

    private List<SelectionAnswerDto> allSelectionAnswers = new ArrayList<SelectionAnswerDto>();
    private Queue<SelectionAnswerDto> selectedAnswers;
    private int maxSelected;
    private AbstractResponseModel<?> responseModel;

    @Inject
    public GroupAnswersController(@Assisted int maxSelected, @Assisted SelectionModuleModel responseModel,
            NoAnswerPriorityComparator comparator) {
        this.maxSelected = maxSelected;
        this.responseModel = responseModel;
        this.selectedAnswers = new PriorityQueue<>(maxSelected, comparator);
    }

    public void addSelectionAnswer(SelectionAnswerDto button) {
        allSelectionAnswers.add(button);
    }

    private void selectAnswer(SelectionAnswerDto selectionAnswer) {
        if (!allSelectionAnswers.contains(selectionAnswer)) {
            LOGGER.log(Level.SEVERE, "SelectButton method called from GroupChoicesController with button as argument, "
                    + "that is not connected with this controller!");
            return;
        }

        if (selectedAnswers.size() >= maxSelected) {
            unselectFirstlySelectedAnswer();
        }

        selectedAnswers.add(selectionAnswer);

        selectionAnswer.setSelected(true);
        responseModel.addAnswer(selectionAnswer.getId());
    }

    private void unselectFirstlySelectedAnswer() {
        SelectionAnswerDto selectionAnswer = selectedAnswers.poll();
        selectionAnswer.setSelected(false);
        responseModel.removeAnswer(selectionAnswer.getId());
    }

    private void unselectAnswer(SelectionAnswerDto selectionAnswer) {
        selectedAnswers.remove(selectionAnswer);
        selectionAnswer.setSelected(false);
        responseModel.removeAnswer(selectionAnswer.getId());
    }

    public void selectToggleAnswer(String selectionAnswerId) {
        SelectionAnswerDto selectionAnswer = findSelectionAnswerById(selectionAnswerId);
        if (selectionAnswer == null) {
            return;
        }

        if (selectionAnswer.isSelected())
            unselectAnswer(selectionAnswer);
        else
            selectAnswer(selectionAnswer);
    }

    private SelectionAnswerDto findSelectionAnswerById(String answerId) {
        SelectionAnswerDto selectionAnswerById = null;
        for (SelectionAnswerDto selectionAnswer : allSelectionAnswers) {
            if (answerId.equals(selectionAnswer.getId())) {
                selectionAnswerById = selectionAnswer;
                break;
            }
        }
        return selectionAnswerById;
    }

    public void reset() {
        for (SelectionAnswerDto button : selectedAnswers) {
            button.setSelected(false);
        }
        selectedAnswers.clear();
    }

    public void selectOnlyAnswersMatchingIds(Collection<String> ids) {
        List<SelectionAnswerDto> buttonsToSelect = findAnswersWithIds(ids, allSelectionAnswers);
        List<SelectionAnswerDto> alreadySelectedMatchingIdsButtons = findAnswersWithIds(ids, selectedAnswers);

        selectedAnswers.removeAll(alreadySelectedMatchingIdsButtons);
        for (SelectionAnswerDto answerToUnselect : selectedAnswers) {
            answerToUnselect.setSelected(false);
        }
        selectedAnswers.clear();

        selectedAnswers.addAll(alreadySelectedMatchingIdsButtons);

        for (SelectionAnswerDto answerToSelect : buttonsToSelect) {
            if (!alreadySelectedMatchingIdsButtons.contains(answerToSelect)) {
                answerToSelect.setSelected(true);
                selectedAnswers.add(answerToSelect);
            }
        }
    }

    private List<SelectionAnswerDto> findAnswersWithIds(Collection<String> ids, Collection<SelectionAnswerDto> buttonsToSearch) {
        List<SelectionAnswerDto> buttons = new ArrayList<SelectionAnswerDto>();
        for (SelectionAnswerDto button : buttonsToSearch) {
            String id = button.getId();
            if (ids.contains(id)) {
                buttons.add(button);
            }
        }
        return buttons;
    }

    public void setLockedAllAnswers(boolean locked) {
        for (SelectionAnswerDto answer : allSelectionAnswers) {
            answer.setLocked(locked);
        }
    }

    public List<SelectionAnswerDto> getSelectedAnswers() {
        return new ArrayList<>(selectedAnswers);
    }

    public List<SelectionAnswerDto> getNotSelectedAnswers() {
        List<SelectionAnswerDto> notSelectedButtons = new ArrayList<SelectionAnswerDto>(allSelectionAnswers);
        notSelectedButtons.removeAll(selectedAnswers);
        return notSelectedButtons;
    }

    public List<SelectionAnswerDto> getAllAnswers() {
        return new ArrayList<SelectionAnswerDto>(allSelectionAnswers);
    }

}
