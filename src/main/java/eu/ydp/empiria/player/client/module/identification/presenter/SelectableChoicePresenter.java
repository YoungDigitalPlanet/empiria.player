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

package eu.ydp.empiria.player.client.module.identification.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.gin.binding.UniqueId;
import eu.ydp.empiria.player.client.gin.factory.IdentificationModuleFactory;
import eu.ydp.empiria.player.client.module.identification.view.SelectableChoiceView;

public class SelectableChoicePresenter {

    private final String identifier;
    private final SelectableChoiceView view;
    private boolean selected;

    @Inject
    public SelectableChoicePresenter(@Assisted Widget contentWidget, @Assisted String identifier, @UniqueId String coverId,
                                     IdentificationModuleFactory moduleFactory) {
        this.identifier = identifier;
        this.view = moduleFactory.createSelectableChoiceView(contentWidget);
        init(coverId);
    }

    public void init(String coverId) {
        setSelected(false);
        view.setCoverId(coverId);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        updatePanelStyleName();
    }

    public void markAnswers(boolean mark, boolean isChoiceCorrect) {
        if (mark) {
            markChoice(isChoiceCorrect);
        } else {
            updatePanelStyleName();
        }
    }

    private void markChoice(boolean isChoiceCorrect) {
        if (isChoiceCorrect) {
            markCorrectChoice();
        } else {
            markWrongChoice();
        }
    }

    private void markCorrectChoice() {
        if (selected) {
            view.markSelectedAnswerCorrect();
        } else {
            view.markNotSelectedAnswerWrong();
        }
    }

    private void markWrongChoice() {
        if (selected) {
            view.markSelectedAnswerWrong();
        } else {
            view.markNotSelectedAnswerCorrect();
        }
    }

    private void updatePanelStyleName() {
        if (selected) {
            view.markSelectedOption();
        } else {
            view.unmarkSelectedOption();
        }
    }

    public String getIdentifier() {
        return identifier;
    }

    public Widget getView() {
        return view.asWidget();
    }

    public void lock() {
        view.lock();
    }

    public void unlock() {
        view.unlock();
    }
}
