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

package eu.ydp.empiria.player.client.controller.variables.processor.results;

import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ModulesProcessingResultsJUnitTest {

    private ModulesProcessingResults modulesProcessingResults;

    @Before
    public void setUp() throws Exception {
        modulesProcessingResults = new ModulesProcessingResults(new InitialProcessingResultFactory());
    }

    @Test
    public void shouldCreateNewResultsWhenPreviousNotCached() throws Exception {
        InitialProcessingResultFactory processingResultFactory = Mockito.mock(InitialProcessingResultFactory.class);
        modulesProcessingResults = new ModulesProcessingResults(processingResultFactory);

        DtoModuleProcessingResult processingResults = Mockito.mock(DtoModuleProcessingResult.class);
        when(processingResultFactory.createProcessingResultWithInitialValues()).thenReturn(processingResults);

        DtoModuleProcessingResult returnedProcessingResults = modulesProcessingResults.getProcessingResultsForResponseId("responseId");

        assertThat(returnedProcessingResults).isEqualTo(processingResults);
    }

    @Test
    public void shouldReturnPreviousCachedResults() throws Exception {
        String responseId = "responseId";
        DtoModuleProcessingResult firstProcessingResults = modulesProcessingResults.getProcessingResultsForResponseId(responseId);
        DtoModuleProcessingResult secondProcessingResults = modulesProcessingResults.getProcessingResultsForResponseId(responseId);

        assertThat(secondProcessingResults).isEqualTo(firstProcessingResults);
    }

    @Test
    public void shouldReturnIdsOfAllProcessedResponses() throws Exception {
        createProcessingResult("id1");
        createProcessingResult("id2");

        Set<String> ids = modulesProcessingResults.getIdsOfProcessedResponses();

        assertThat(ids).contains("id1", "id2");
    }

    @Test
    public void shouldReturnCollectionOfProcessingResults() throws Exception {
        DtoModuleProcessingResult processingResult = createProcessingResult("id1");
        DtoModuleProcessingResult otherProcessingResult = createProcessingResult("id2");

        Map<String, DtoModuleProcessingResult> listOfProcessingResults = modulesProcessingResults.getMapOfProcessingResults();


        assertThat(listOfProcessingResults.values()).contains(processingResult, otherProcessingResult);
    }

    private DtoModuleProcessingResult createProcessingResult(String responseId) {
        return modulesProcessingResults.getProcessingResultsForResponseId(responseId);
    }

}
