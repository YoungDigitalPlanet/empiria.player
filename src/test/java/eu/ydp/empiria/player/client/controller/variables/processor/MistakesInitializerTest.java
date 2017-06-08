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

import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.OutcomeController;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MistakesInitializerTest {

    @InjectMocks
    private MistakesInitializer mistakesInitializer;

    @Mock
    private ModulesProcessingResults processingResults;
    @Mock
    private OutcomeController outcomeController;

    @After
    public void after() {
        verifyNoMoreInteractions(processingResults, outcomeController);
    }

    @Test
    public void initializeTest() {
        // given
        ItemOutcomeStorageImpl outcomeStorage = new ItemOutcomeStorageImpl();

        Integer value1 = 1;
        Integer value2 = 2;
        Integer value3 = 3;
        String id1 = "id1";
        String id2 = "id2";
        String id3 = "id3";

        Map<String, Integer> mistakeResponses = Maps.newHashMap();
        mistakeResponses.put(id1, value1);
        mistakeResponses.put(id2, value2);
        mistakeResponses.put(id3, value3);

        DtoModuleProcessingResult dtoModuleProcessingResult1 = DtoModuleProcessingResult.fromDefaultVariables();
        DtoModuleProcessingResult dtoModuleProcessingResult2 = DtoModuleProcessingResult.fromDefaultVariables();
        DtoModuleProcessingResult dtoModuleProcessingResult3 = DtoModuleProcessingResult.fromDefaultVariables();

        when(outcomeController.getAllMistakes(outcomeStorage)).thenReturn(mistakeResponses);
        when(processingResults.getProcessingResultsForResponseId(id1)).thenReturn(dtoModuleProcessingResult1);
        when(processingResults.getProcessingResultsForResponseId(id2)).thenReturn(dtoModuleProcessingResult2);
        when(processingResults.getProcessingResultsForResponseId(id3)).thenReturn(dtoModuleProcessingResult3);

        // when
        mistakesInitializer.initialize(outcomeStorage);

        // then
        InOrder inOrder = inOrder(processingResults, outcomeController);
        inOrder.verify(outcomeController).getAllMistakes(outcomeStorage);
        verify(processingResults).getProcessingResultsForResponseId(id1);
        verify(processingResults).getProcessingResultsForResponseId(id2);
        verify(processingResults).getProcessingResultsForResponseId(id3);

        assertEquals(value1, new Integer(dtoModuleProcessingResult1.getUserInteractionVariables().getMistakes()));
        assertEquals(value2, new Integer(dtoModuleProcessingResult2.getUserInteractionVariables().getMistakes()));
        assertEquals(value3, new Integer(dtoModuleProcessingResult3.getUserInteractionVariables().getMistakes()));
    }
}
