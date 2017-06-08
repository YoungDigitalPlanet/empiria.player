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

import com.google.common.collect.ImmutableMap;
import eu.ydp.empiria.player.client.controller.variables.manager.VariableManager;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ProcessingResultsToOutcomeMapConverterFacade;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VariableProcessingAdapterTest {

    @InjectMocks
    private VariableProcessingAdapter testObj;

    @Mock
    private ModulesVariablesProcessor modulesVariablesProcessor;
    @Mock
    private AnswerEvaluationSupplier answerEvaluationProvider;
    @Mock
    private ProcessingResultsToOutcomeMapConverterFacade resultsToOutcomeMapConverterFacade;
    @Mock
    private GlobalVariablesProvider globalVariablesProvider;

    @Mock
    private ModulesProcessingResults modulesProcessingResults;
    @Mock
    private VariableManager<Response> responseManager;
    @Mock
    private ItemOutcomeStorageImpl outcomeStorage;

    private final ProcessingMode processingMode = ProcessingMode.USER_INTERACT;

    @Before
    public void setUp() throws Exception {
        when(modulesVariablesProcessor.processVariablesForResponses(responseManager, processingMode)).thenReturn(modulesProcessingResults);
    }

    @Test
    public void shouldProcessResponsesAndReturnResultConvertedToOldMap() throws Exception {
        // given
        final String ID = "ID";

        Map<String, DtoModuleProcessingResult> processingResults = ImmutableMap.of(ID, DtoModuleProcessingResult.fromDefaultVariables());
        when(modulesProcessingResults.getMapOfProcessingResults()).thenReturn(processingResults);

        GlobalVariables globalVariables = mock(GlobalVariables.class);
        when(globalVariablesProvider.retrieveGlobalVariables(modulesProcessingResults, responseManager)).thenReturn(globalVariables);

        // when
        testObj.processResponseVariables(responseManager, outcomeStorage, processingMode);

        // then
        verify(modulesVariablesProcessor).processVariablesForResponses(responseManager, processingMode);
        verify(resultsToOutcomeMapConverterFacade).convert(outcomeStorage, modulesProcessingResults, globalVariables);
    }

    @Test
    public void shouldUpdateAnswerEvaluationProvider() throws Exception {
        // when
        testObj.processResponseVariables(responseManager, outcomeStorage, processingMode);

        // then
        verify(answerEvaluationProvider).updateModulesProcessingResults(modulesProcessingResults);
    }
}
