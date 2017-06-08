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

package eu.ydp.empiria.player.client.module.colorfill;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.AnswerEvaluationSupplier;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ResponseAnswerByViewBuilder;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ResponseUserAnswersConverter;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillBeanProxy;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ColorfillModelProxyJUnitTest {

    @InjectMocks
    private ColorfillModelProxy modelProxy;
    @Mock
    private ResponseAnswerByViewBuilder responseAnswerByViewBuilder;
    @Mock
    private ColorfillInteractionModuleModel colorfillInteractionModuleModel;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ColorfillBeanProxy beanProxy;
    @Mock
    private ResponseUserAnswersConverter responseUserAnswersConverter;

    @Mock
    private AnswerEvaluationSupplier answerEvaluationSupplier;

    @SuppressWarnings("unchecked")
    @Test
    public void updateUserAnswers() {
        // given
        List<String> list = Lists.newArrayList("a");
        when(responseAnswerByViewBuilder.buildNewResponseAnswersByCurrentImage(anyList())).thenReturn(list);

        // when
        modelProxy.updateUserAnswers();

        // then
        verify(colorfillInteractionModuleModel).setNewUserAnswers(list);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getUserAnswers() {
        // given
        Map<Area, ColorModel> map = ImmutableMap.of(new Area(1, 2), ColorModel.createFromRgba(0, 1, 2, 3));
        when(responseUserAnswersConverter.convertResponseAnswersToAreaColorMap(anyList())).thenReturn(map);

        // when
        Map<Area, ColorModel> result = modelProxy.getUserAnswers();

        // then
        assertThat(result).isEqualTo(map);
    }

    @Test
    public void getUserWrongAnswers() {
        List<Boolean> evaluationResult = Lists.newArrayList(true, false, true, false);
        doReturn(evaluationResult).when(answerEvaluationSupplier).evaluateAnswer(Matchers.any(Response.class));
        List<String> currentAnswers = Lists.newArrayList("true", "false", "true", "false");
        doReturn(currentAnswers).when(colorfillInteractionModuleModel).getCurrentAnswers();

        ArgumentCaptor<List> userAnswersCaptor = ArgumentCaptor.forClass(List.class);
        modelProxy.getUserWrongAnswers();

        verify(responseUserAnswersConverter).convertResponseAnswersToAreaList(userAnswersCaptor.capture());
        List<String> values = userAnswersCaptor.getValue();
        assertThat(values).hasSize(2).contains("false", "false");
    }

    @Test
    public void getUserCorrectAnswers() {
        List<Boolean> evaluationResult = Lists.newArrayList(true, false, true, false);
        doReturn(evaluationResult).when(answerEvaluationSupplier).evaluateAnswer(Matchers.any(Response.class));
        List<String> currentAnswers = Lists.newArrayList("true", "false", "true", "false");
        doReturn(currentAnswers).when(colorfillInteractionModuleModel).getCurrentAnswers();

        modelProxy.getUserCorrectAnswers();

        ArgumentCaptor<List> userAnswersCaptor = ArgumentCaptor.forClass(List.class);
        verify(responseUserAnswersConverter).convertResponseAnswersToAreaList(userAnswersCaptor.capture());
        List<String> values = userAnswersCaptor.getValue();
        assertThat(values).hasSize(2).contains("true", "true");
    }

}
