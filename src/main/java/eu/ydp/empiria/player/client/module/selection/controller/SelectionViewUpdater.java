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
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.model.SelectionGridElementPosition;
import eu.ydp.empiria.player.client.module.selection.view.SelectionElementPositionGenerator;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleView;

import java.util.List;

public class SelectionViewUpdater {

    private SelectionElementPositionGenerator elementPositionGenerator;

    @Inject
    public SelectionViewUpdater(SelectionElementPositionGenerator elementPositionGenerator) {
        this.elementPositionGenerator = elementPositionGenerator;
    }

    public void updateView(SelectionModuleView selectionModuleView, GroupAnswersController groupChoicesController, int itemNumber) {
        List<SelectionAnswerDto> allAnswers = groupChoicesController.getAllAnswers();

        for (int choiceNumber = 0; choiceNumber < allAnswers.size(); choiceNumber++) {
            SelectionAnswerDto selectionAnswerDto = allAnswers.get(choiceNumber);

            if (selectionAnswerDto.isStateChanged()) {
                SelectionGridElementPosition position = elementPositionGenerator.getButtonElementPositionFor(itemNumber, choiceNumber);
                updateSingleAnswer(selectionAnswerDto, position, selectionModuleView);
                selectionAnswerDto.setStateChanged(false);
            }
        }
    }

    private void updateSingleAnswer(SelectionAnswerDto selectionAnswerDto, SelectionGridElementPosition position, SelectionModuleView selectionModuleView) {
        if (selectionAnswerDto.isSelected()) {
            selectionModuleView.selectButton(position);
        } else {
            selectionModuleView.unselectButton(position);
        }

        selectionModuleView.lockButton(position, selectionAnswerDto.isLocked());
        selectionModuleView.updateButtonStyle(position, selectionAnswerDto.getSelectionAnswerType());
    }
}
