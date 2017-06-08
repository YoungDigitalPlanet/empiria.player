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

package eu.ydp.empiria.player.client.module.selection.handlers;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.selection.controller.GroupAnswersController;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;

public class ChoiceButtonClickHandler implements ClickHandler {

    private final GroupAnswersController groupAnswerController;
    private final String buttonId;
    private final SelectionModulePresenter selectionModulePresenter;

    @Inject
    public ChoiceButtonClickHandler(@Assisted GroupAnswersController groupAnswerController, @Assisted String buttonId,
                                    @Assisted SelectionModulePresenter selectionModulePresenter) {
        this.groupAnswerController = groupAnswerController;
        this.buttonId = buttonId;
        this.selectionModulePresenter = selectionModulePresenter;
    }

    @Override
    public void onClick(ClickEvent event) {
        groupAnswerController.selectToggleAnswer(buttonId);
        selectionModulePresenter.updateGroupAnswerView(groupAnswerController);
    }
}
