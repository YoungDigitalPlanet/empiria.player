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

package eu.ydp.empiria.player.client.module.external.common.api;

import eu.ydp.empiria.player.client.module.external.interaction.ExternalInteractionResponseModel;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalInteractionEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalInteractionStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExternalInteractionEmpiriaApiTest {

    @InjectMocks
    private ExternalInteractionEmpiriaApi testObj;
    @Mock
    private ExternalInteractionResponseModel responseModel;
    @Captor
    private ArgumentCaptor<String> answersCaptor;

    @Test
    public void shouldReplaceExternalState() {
        // given
        int done = 5;
        int errors = 3;
        ExternalInteractionStatus status = mock(ExternalInteractionStatus.class);
        when(status.getDone()).thenReturn(done);
        when(status.getErrors()).thenReturn(errors);

        List<String> expectedAnswers = Arrays.asList("1", "2", "3", "4", "5", "-1", "-2", "-3");
        int answersSize = expectedAnswers.size();

        // when
        testObj.onResultChange(status);

        // then
        verify(responseModel, times(answersSize)).addAnswer(answersCaptor.capture());
        List<String> resultList = answersCaptor.getAllValues();
        assertThat(resultList).containsAll(expectedAnswers);
    }
}
