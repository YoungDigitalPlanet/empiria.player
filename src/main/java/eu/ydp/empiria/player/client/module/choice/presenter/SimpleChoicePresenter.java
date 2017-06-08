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

package eu.ydp.empiria.player.client.module.choice.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersType;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleListener;

public interface SimpleChoicePresenter extends IsWidget {

    void setLocked(boolean locked);

    void setListener(ChoiceModuleListener listener);

    void reset();

    IsWidget getFeedbackPlaceHolder();

    boolean isSelected();

    void markAnswer(MarkAnswersType type, MarkAnswersMode mode);

    void setSelected(boolean isCorrect);

    boolean isMulti();

    void onChoiceClick();

    String getIdentifier();
}
