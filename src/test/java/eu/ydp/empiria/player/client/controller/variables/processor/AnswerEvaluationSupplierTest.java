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

package eu.ydp.empiria.player.client.controller.variables.processor;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.InitialProcessingResultFactory;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GeneralVariables;
import org.junit.Test;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class AnswerEvaluationSupplierTest {

    private Logger LOGGER = Logger.getLogger(VariableProcessingAdapter.class.getName());

    private AnswerEvaluationSupplier supplier = new AnswerEvaluationSupplier();

    @Test
    public void evaluateAnswer() {
        // given
        final String ID = "";
        Response response = createResponse(ID);
        List<Boolean> answerEvaluations = Lists.newArrayList(true, false, true);
        ModulesProcessingResults modulesProcessingResults = prepareModulesProcessingResults(ID, answerEvaluations);
        supplier.updateModulesProcessingResults(modulesProcessingResults);

        // when
        List<Boolean> evaluation = supplier.evaluateAnswer(response);

        // then
        assertThat(evaluation).isEqualTo(answerEvaluations);
    }

    private Response createResponse(final String ID) {
        Response response = mock(Response.class);
        when(response.getID()).thenReturn(ID);
        return response;
    }

    private ModulesProcessingResults prepareModulesProcessingResults(final String ID, List<Boolean> answerEvaluations) {
        ModulesProcessingResults modulesProcessingResults = new ModulesProcessingResults(new InitialProcessingResultFactory());
        DtoModuleProcessingResult results = modulesProcessingResults.getProcessingResultsForResponseId(ID);
        results.setGeneralVariables(new GeneralVariables(Lists.<String>newArrayList(), answerEvaluations, 0, 0));
        return modulesProcessingResults;
    }

    @Test
    public void evaluateAnswer_evaluationBeforeAnswerProcessing() {
        // given
        Response response = mock(Response.class);
        Handler handler = mock(Handler.class);
        LOGGER.addHandler(handler);

        // when
        List<Boolean> evaluation = supplier.evaluateAnswer(response);

        // then
        assertThat(evaluation.isEmpty()).isTrue();
        verify(handler).publish(any(LogRecord.class));

    }

}
