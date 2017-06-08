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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChoiceModuleListenerImplTest {

    private static final String IDENTIFIER = "ID_1";
    @Mock
    SimpleChoicePresenter choice;
    @Mock
    ChoiceModuleModel model;
    @Mock
    ChoiceModulePresenter presenter;
    @InjectMocks
    ChoiceModuleListenerImpl listener;

    @Test
    public void shouldRemoveAnswerWhenChoiceIsSelected() {
        // given
        when(choice.isSelected()).thenReturn(true);
        when(choice.getIdentifier()).thenReturn(IDENTIFIER);

        // when
        listener.onChoiceClick(choice);

        // then
        verify(model).removeAnswer(IDENTIFIER);
        verify(presenter).showAnswers(ShowAnswersType.USER);
    }

    @Test
    public void shouldAddAnswerWhenChoiceIsNotSelected() {
        // given
        when(choice.isSelected()).thenReturn(false);
        when(choice.getIdentifier()).thenReturn(IDENTIFIER);

        // when
        listener.onChoiceClick(choice);

        // then
        verify(model).addAnswer(IDENTIFIER);
        verify(presenter).showAnswers(ShowAnswersType.USER);
    }
}
