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

package eu.ydp.empiria.player.client.module.choice;

import eu.ydp.empiria.player.client.module.core.answer.ShowAnswersType;
import eu.ydp.empiria.player.client.module.choice.presenter.ChoiceModulePresenter;
import eu.ydp.empiria.player.client.module.choice.presenter.SimpleChoicePresenter;

public class ChoiceModuleListenerImpl implements ChoiceModuleListener {

    private final ChoiceModuleModel model;
    private final ChoiceModulePresenter presenter;

    public ChoiceModuleListenerImpl(ChoiceModuleModel model, ChoiceModulePresenter presenter) {
        this.model = model;
        this.presenter = presenter;
    }

    @Override
    public void onChoiceClick(SimpleChoicePresenter choice) {
        String choiceIdentifier = choice.getIdentifier();
        if (choice.isSelected()) {
            model.removeAnswer(choiceIdentifier);
        } else {
            model.addAnswer(choiceIdentifier);
        }

        presenter.showAnswers(ShowAnswersType.USER);
    }

}
