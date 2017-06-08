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

package eu.ydp.empiria.player.client.module.info.handler;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.report.table.extraction.PagesRangeExtractor;
import eu.ydp.empiria.player.client.controller.variables.storage.assessment.AssessmentVariableStorage;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;
import eu.ydp.empiria.player.client.style.ModuleStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_INFO_TEST_INCLUDE;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProviderAssessmentValueHandlerTest {

    private ProviderAssessmentValueHandler testObj;
    @Mock
    private ModuleStyle moduleStyle;
    @Mock
    private AssessmentVariableStorage assessmentVariableStorage;
    @Mock
    private PagesRangeExtractor pagesRangeExtractor;
    @Mock
    private ContentFieldInfo contentFieldInfo;
    @Captor
    private ArgumentCaptor<List<Integer>> pagesCaptor;

    @Test
    public void shouldReturnVariableValueFromAllItems_whenStyleDoesNotExist() {
        // given
        when(moduleStyle.containsKey(EMPIRIA_INFO_TEST_INCLUDE)).thenReturn(false);
        testObj = new ProviderAssessmentValueHandler(moduleStyle, assessmentVariableStorage, pagesRangeExtractor);

        String variableName = "TODO";
        int todo = 5;
        String expectedTodo = "5";
        when(assessmentVariableStorage.getVariableIntValue(variableName)).thenReturn(todo);
        when(contentFieldInfo.getValueName()).thenReturn(variableName);

        // when
        String result = testObj.getValue(contentFieldInfo, 0);

        // then
        assertThat(result).isEqualTo(expectedTodo);
    }

    @Test
    public void shouldReturnPercentValueFromGivenPages_whenStyleExists() {
        // given
        String range = "2:5";
        Integer[] pages = {2, 3, 4, 5};
        List<Integer> pagesList = Lists.newArrayList(pages);
        when(moduleStyle.containsKey(EMPIRIA_INFO_TEST_INCLUDE)).thenReturn(true);
        when(pagesRangeExtractor.parseRange(range)).thenReturn(pagesList);
        when(moduleStyle.get(EMPIRIA_INFO_TEST_INCLUDE)).thenReturn(range);

        testObj = new ProviderAssessmentValueHandler(moduleStyle, assessmentVariableStorage, pagesRangeExtractor);

        String variableName = "TODO";
        int todo = 5;
        String expectedTodo = "5";
        when(assessmentVariableStorage.getVariableIntValue(eq("TODO"), pagesCaptor.capture())).thenReturn(todo);
        when(contentFieldInfo.getValueName()).thenReturn(variableName);

        // when
        String result = testObj.getValue(contentFieldInfo, 0);

        // then
        assertThat(result).isEqualTo(expectedTodo);
        List<Integer> capturedPageIndexes = pagesCaptor.getValue();
        assertThat(capturedPageIndexes).containsExactly(pages);
    }
}