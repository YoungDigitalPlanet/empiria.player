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

import com.google.common.collect.Lists;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.SimpleChoicePresenterFactory;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersType;
import eu.ydp.empiria.player.client.module.core.answer.ShowAnswersType;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleListener;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleModel;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceInteractionBean;
import eu.ydp.empiria.player.client.module.choice.structure.SimpleChoiceBean;
import eu.ydp.empiria.player.client.module.choice.view.ChoiceModuleView;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ChoiceModulePresenterImplTest {

    @InjectMocks
    ChoiceModulePresenterImpl presenter;

    @Mock
    ChoiceModuleModel model;
    @Mock
    ChoiceModuleView view;
    @Mock
    SimpleChoicePresenterFactory choiceModuleFactory;
    @Mock
    InlineBodyGeneratorSocket bodyGenerator;
    @Mock
    SimpleChoicePresenter simplePresenter1;
    @Mock
    SimpleChoicePresenter simplePresenter2;
    @Mock
    SimpleChoicePresenter simplePresenter3;
    @Mock
    SimpleChoicePresenter simplePresenter4;
    ChoiceInteractionBean bean;

    private static final String IDENTIFIER_1 = "1";
    private static final String IDENTIFIER_2 = "2";
    private static final String IDENTIFIER_3 = "3";
    private static final String IDENTIFIER_4 = "4";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        prepareBean();
        presenter.setBean(bean);
        presenter.setInlineBodyGenerator(bodyGenerator);

        presenter.bindView();
    }

    @BeforeClass
    public static void disarm() {
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void restore() {
        GWTMockUtilities.restore();
    }

    private void prepareBean() {
        bean = new ChoiceInteractionBean();
        List<SimpleChoiceBean> simpleChoices = Lists.newArrayList(createSimpleBean(IDENTIFIER_1, simplePresenter1),
                createSimpleBean(IDENTIFIER_2, simplePresenter2), createSimpleBean(IDENTIFIER_3, simplePresenter3),
                createSimpleBean(IDENTIFIER_4, simplePresenter4));
        bean.setSimpleChoices(simpleChoices);
    }

    private SimpleChoiceBean createSimpleBean(String id, SimpleChoicePresenter simplePresenter) {
        SimpleChoiceBean bean = new SimpleChoiceBean();
        bean.setIdentifier(id);
        when(choiceModuleFactory.getSimpleChoicePresenter(bean, bodyGenerator)).thenReturn(simplePresenter);

        when(simplePresenter.getIdentifier()).thenReturn(id);

        return bean;
    }

    @Test
    public void shouldInitializeView() {
        // then
        verify(view).clear();
        verify(view, times(4)).addChoice(any(Widget.class));
        verify(simplePresenter1).setListener(any(ChoiceModuleListener.class));
        verify(simplePresenter2).setListener(any(ChoiceModuleListener.class));
        verify(simplePresenter3).setListener(any(ChoiceModuleListener.class));
        verify(simplePresenter4).setListener(any(ChoiceModuleListener.class));
    }

    @Test
    public void shouldLockAlleSimplePresenters() {
        // when
        presenter.setLocked(true);

        // then
        verify(simplePresenter1).setLocked(true);
        verify(simplePresenter2).setLocked(true);
        verify(simplePresenter3).setLocked(true);
        verify(simplePresenter4).setLocked(true);
    }

    @Test
    public void shouldResetAllSimplePresenters() {
        // when
        presenter.reset();

        // then
        verify(simplePresenter1).reset();
        verify(simplePresenter2).reset();
        verify(simplePresenter3).reset();
        verify(simplePresenter4).reset();
    }

    @Test
    public void shouldShowAnswers_USER() {
        // given
        when(model.isUserAnswer(IDENTIFIER_1)).thenReturn(true);
        when(model.isUserAnswer(IDENTIFIER_2)).thenReturn(false);
        when(model.isUserAnswer(IDENTIFIER_3)).thenReturn(false);
        when(model.isUserAnswer(IDENTIFIER_4)).thenReturn(true);

        // when
        presenter.showAnswers(ShowAnswersType.USER);

        // then
        verify(simplePresenter1).setSelected(true);
        verify(simplePresenter2).setSelected(false);
        verify(simplePresenter3).setSelected(false);
        verify(simplePresenter4).setSelected(true);
    }

    @Test
    public void shouldShowAnswers_CORRECT() {
        // given
        when(model.isCorrectAnswer(IDENTIFIER_1)).thenReturn(true);
        when(model.isCorrectAnswer(IDENTIFIER_2)).thenReturn(false);
        when(model.isCorrectAnswer(IDENTIFIER_3)).thenReturn(false);
        when(model.isCorrectAnswer(IDENTIFIER_4)).thenReturn(true);

        // when
        presenter.showAnswers(ShowAnswersType.CORRECT);

        // then
        verify(simplePresenter1).setSelected(true);
        verify(simplePresenter2).setSelected(false);
        verify(simplePresenter3).setSelected(false);
        verify(simplePresenter4).setSelected(true);
    }

    @Test
    public void shouldMarkAnswers_CORRECT_MARK() {
        // given
        when(simplePresenter1.isSelected()).thenReturn(true);
        when(model.isCorrectAnswer(IDENTIFIER_1)).thenReturn(true);

        when(simplePresenter2.isSelected()).thenReturn(true);
        when(model.isWrongAnswer(IDENTIFIER_2)).thenReturn(true);

        when(simplePresenter3.isSelected()).thenReturn(false);
        when(model.isWrongAnswer(IDENTIFIER_3)).thenReturn(true);

        when(simplePresenter4.isSelected()).thenReturn(false);
        when(model.isCorrectAnswer(IDENTIFIER_4)).thenReturn(true);

        // when
        presenter.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.MARK);

        // then
        verify(simplePresenter1).markAnswer(MarkAnswersType.CORRECT, MarkAnswersMode.MARK);
        verify(simplePresenter2, never()).markAnswer(any(MarkAnswersType.class), any(MarkAnswersMode.class));
        verify(simplePresenter3, never()).markAnswer(any(MarkAnswersType.class), any(MarkAnswersMode.class));
        verify(simplePresenter4, never()).markAnswer(any(MarkAnswersType.class), any(MarkAnswersMode.class));
    }

    @Test
    public void shouldMarkAnswers_WRONG_MARK() {
        // given
        when(simplePresenter1.isSelected()).thenReturn(true);
        when(model.isCorrectAnswer(IDENTIFIER_1)).thenReturn(true);

        when(simplePresenter2.isSelected()).thenReturn(true);
        when(model.isWrongAnswer(IDENTIFIER_2)).thenReturn(true);

        when(simplePresenter3.isSelected()).thenReturn(false);
        when(model.isWrongAnswer(IDENTIFIER_3)).thenReturn(true);

        when(simplePresenter4.isSelected()).thenReturn(false);
        when(model.isCorrectAnswer(IDENTIFIER_4)).thenReturn(true);

        // when
        presenter.markAnswers(MarkAnswersType.WRONG, MarkAnswersMode.MARK);

        // then
        verify(simplePresenter1, never()).markAnswer(any(MarkAnswersType.class), any(MarkAnswersMode.class));
        verify(simplePresenter2).markAnswer(MarkAnswersType.WRONG, MarkAnswersMode.MARK);
        verify(simplePresenter3, never()).markAnswer(any(MarkAnswersType.class), any(MarkAnswersMode.class));
        verify(simplePresenter4, never()).markAnswer(any(MarkAnswersType.class), any(MarkAnswersMode.class));
    }

    @Test
    public void shouldMarkAnswers_CORRECT_UNMARK() {
        // given
        when(simplePresenter1.isSelected()).thenReturn(true);
        when(model.isCorrectAnswer(IDENTIFIER_1)).thenReturn(true);

        when(simplePresenter2.isSelected()).thenReturn(true);
        when(model.isWrongAnswer(IDENTIFIER_2)).thenReturn(true);

        when(simplePresenter3.isSelected()).thenReturn(false);
        when(model.isWrongAnswer(IDENTIFIER_3)).thenReturn(true);

        when(simplePresenter4.isSelected()).thenReturn(false);
        when(model.isCorrectAnswer(IDENTIFIER_4)).thenReturn(true);

        // when
        presenter.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.UNMARK);

        // then
        verify(simplePresenter1).markAnswer(MarkAnswersType.CORRECT, MarkAnswersMode.UNMARK);
        verify(simplePresenter2, never()).markAnswer(any(MarkAnswersType.class), any(MarkAnswersMode.class));
        verify(simplePresenter3, never()).markAnswer(any(MarkAnswersType.class), any(MarkAnswersMode.class));
        verify(simplePresenter4, never()).markAnswer(any(MarkAnswersType.class), any(MarkAnswersMode.class));
    }

    @Test
    public void shouldMarkAnswers_WRONG_UNMARK() {
        // given
        when(simplePresenter1.isSelected()).thenReturn(true);
        when(model.isCorrectAnswer(IDENTIFIER_1)).thenReturn(true);

        when(simplePresenter2.isSelected()).thenReturn(true);
        when(model.isWrongAnswer(IDENTIFIER_2)).thenReturn(true);

        when(simplePresenter3.isSelected()).thenReturn(false);
        when(model.isWrongAnswer(IDENTIFIER_3)).thenReturn(true);

        when(simplePresenter4.isSelected()).thenReturn(false);
        when(model.isCorrectAnswer(IDENTIFIER_4)).thenReturn(true);

        // when
        presenter.markAnswers(MarkAnswersType.WRONG, MarkAnswersMode.UNMARK);

        // then
        verify(simplePresenter1, never()).markAnswer(any(MarkAnswersType.class), any(MarkAnswersMode.class));
        verify(simplePresenter2).markAnswer(MarkAnswersType.WRONG, MarkAnswersMode.UNMARK);
        verify(simplePresenter3, never()).markAnswer(any(MarkAnswersType.class), any(MarkAnswersMode.class));
        verify(simplePresenter4, never()).markAnswer(any(MarkAnswersType.class), any(MarkAnswersMode.class));
    }
}
