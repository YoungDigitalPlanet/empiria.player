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

package eu.ydp.empiria.player.client.controller.variables.processor.module;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.response.DtoProcessedResponse;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponsesMapBuilder;
import eu.ydp.empiria.player.client.controller.variables.processor.results.InitialProcessingResultFactory;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResponseChangesFinderJUnitTest {

    private ResponseChangesFinder responseChangesFinder;
    private ItemResponseManager responsesManager;
    private ModulesProcessingResults processingResults;

    @Mock
    private ResponseDifferenceFinder responseDifferenceFinder;
    @Mock
    private OrderedResponseChangesFinder orderedResponseChangesFinder;

    @Before
    public void setUp() throws Exception {
        processingResults = new ModulesProcessingResults(new InitialProcessingResultFactory());
        responseChangesFinder = new ResponseChangesFinder(responseDifferenceFinder, orderedResponseChangesFinder);
    }

    @Test
    public void shouldBuildProcessedResponseDtoWithAnswerChanges_CardinalityNotOrdered() throws Exception {
        String responseId = "responseId";

        List<String> previousAnswers = Lists.newArrayList("previousAnswer");
        List<String> currentAnswers = Lists.newArrayList("currentAnswer");
        DtoModuleProcessingResult processingResult = createProcessingResultForIdAndWithPreviousAnswers(responseId, previousAnswers);

        Response response = new ResponseBuilder().withValues(currentAnswers).withIdentifier(responseId).build();
        responsesManager = new ResponsesMapBuilder().buildResponseManager(response);

        LastAnswersChanges answerChanges = Mockito.mock(LastAnswersChanges.class);
        when(responseDifferenceFinder.findChangesOfAnswers(previousAnswers, currentAnswers)).thenReturn(answerChanges);

        List<DtoProcessedResponse> processedResponses = responseChangesFinder.findChangesOfAnswers(processingResults, responsesManager);

        assertEquals(1, processedResponses.size());
        DtoProcessedResponse dtoProcessedResponse = processedResponses.get(0);
        assertEquals(response, dtoProcessedResponse.getCurrentResponse());
        assertEquals(answerChanges, dtoProcessedResponse.getLastAnswersChanges());
        assertEquals(processingResult, dtoProcessedResponse.getPreviousProcessingResult());
    }

    @Test
    public void shouldBuildProcessedResponseDtoWithAnswerChanges_CardinalityOrdered() throws Exception {
        String responseId = "responseId";

        List<String> previousAnswers = Lists.newArrayList("previousAnswer");
        List<String> currentAnswers = Lists.newArrayList("currentAnswer");
        DtoModuleProcessingResult processingResult = createProcessingResultForIdAndWithPreviousAnswers(responseId, previousAnswers);

        Response response = new ResponseBuilder().withValues(currentAnswers).withIdentifier(responseId).withCardinality(Cardinality.ORDERED).build();
        responsesManager = new ResponsesMapBuilder().buildResponseManager(response);

        LastAnswersChanges answerChanges = Mockito.mock(LastAnswersChanges.class);
        when(orderedResponseChangesFinder.findChangesOfAnswers(previousAnswers, currentAnswers)).thenReturn(answerChanges);

        List<DtoProcessedResponse> processedResponses = responseChangesFinder.findChangesOfAnswers(processingResults, responsesManager);

        assertEquals(1, processedResponses.size());
        DtoProcessedResponse dtoProcessedResponse = processedResponses.get(0);
        assertEquals(response, dtoProcessedResponse.getCurrentResponse());
        assertEquals(answerChanges, dtoProcessedResponse.getLastAnswersChanges());
        assertEquals(processingResult, dtoProcessedResponse.getPreviousProcessingResult());
    }

    private DtoModuleProcessingResult createProcessingResultForIdAndWithPreviousAnswers(String responseId, List<String> previousAnswers) {
        DtoModuleProcessingResult processingResult = processingResults.getProcessingResultsForResponseId(responseId);
        processingResult.getGeneralVariables().setAnswers(previousAnswers);
        return processingResult;
    }

}
