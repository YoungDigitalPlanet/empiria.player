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

package eu.ydp.empiria.player.client.controller.item;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.AnswerEvaluationSupplier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ResponseProviderTest {

    @Mock
    private AnswerEvaluationSupplier answerEvaluationProvider;
    @Mock
    private ItemResponseManager itemResponseManager;

    @InjectMocks
    private ResponseProvider responseProvider;

    @Test
    public void getResponse_shouldDelegate() {
        // given
        final String ID = "ID";

        // when
        responseProvider.getResponse(ID);

        // then
        verify(itemResponseManager).getVariable(ID);
    }

    @Test
    public void evaluateResponse_shouldDelegate() {
        // given
        Response response = mock(Response.class);

        // when
        responseProvider.evaluateResponse(response);

        // then
        verify(answerEvaluationProvider).evaluateAnswer(response);
    }

}
