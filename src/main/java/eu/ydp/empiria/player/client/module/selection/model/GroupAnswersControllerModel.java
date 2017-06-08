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

package eu.ydp.empiria.player.client.module.selection.model;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersType;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.controller.GroupAnswersController;
import eu.ydp.empiria.player.client.module.selection.controller.IdentifiableAnswersByTypeFinder;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.ArrayList;
import java.util.List;

public class GroupAnswersControllerModel {

    private IdentifiableAnswersByTypeFinder identifiableAnswersByTypeFinder;
    private SelectionModuleModel model;

    @Inject
    public GroupAnswersControllerModel(IdentifiableAnswersByTypeFinder identifiableAnswersByTypeFinder, @ModuleScoped SelectionModuleModel model) {
        this.identifiableAnswersByTypeFinder = identifiableAnswersByTypeFinder;
        this.model = model;
    }

    private List<GroupAnswersController> groupChoicesControllers;

    public void setGroupChoicesControllers(List<GroupAnswersController> groupChoicesControllers) {
        this.groupChoicesControllers = groupChoicesControllers;
    }

    public List<GroupAnswersController> getGroupChoicesControllers() {
        return groupChoicesControllers;
    }

    public int indexOf(GroupAnswersController groupChoicesController) {
        return groupChoicesControllers.indexOf(groupChoicesController);
    }

    public List<SelectionAnswerDto> getSelectedAnswers() {
        List<SelectionAnswerDto> selectedAnswers = new ArrayList<SelectionAnswerDto>();
        for (GroupAnswersController item : groupChoicesControllers) {
            selectedAnswers.addAll(item.getSelectedAnswers());
        }
        return selectedAnswers;
    }

    public List<SelectionAnswerDto> getNotSelectedAnswers() {
        List<SelectionAnswerDto> notSelectedAnswers = new ArrayList<SelectionAnswerDto>();
        for (GroupAnswersController item : groupChoicesControllers) {
            notSelectedAnswers.addAll(item.getNotSelectedAnswers());
        }
        return notSelectedAnswers;
    }

    public List<SelectionAnswerDto> getButtonsToMarkForType(MarkAnswersType type) {
        return identifiableAnswersByTypeFinder.findAnswersObjectsOfGivenType(type, getSelectedAnswers(), model);
    }
}
