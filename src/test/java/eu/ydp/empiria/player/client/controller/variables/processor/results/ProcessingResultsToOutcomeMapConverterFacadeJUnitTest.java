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

import com.google.common.collect.Maps;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProcessingResultsToOutcomeMapConverterFacadeJUnitTest {

    @Mock
    private ProcessingResultsToOutcomeMapConverterFactory converterFactory;
    @InjectMocks
    private ProcessingResultsToOutcomeMapConverterFacade converterFacade;

    @Before
    public void setUp() {
    }

    @Test
    public void convert() {
        // given
        ItemOutcomeStorageImpl givenOutcomeStorage = new ItemOutcomeStorageImpl();

        ModulesProcessingResults results = mock(ModulesProcessingResults.class);
        GlobalVariables globalVariables = mock(GlobalVariables.class);
        ProcessingResultsToOutcomeMapConverter converter = mock(ProcessingResultsToOutcomeMapConverter.class);
        when(converterFactory.createConverter(givenOutcomeStorage)).thenReturn(converter);

        // when
        converterFacade.convert(givenOutcomeStorage, results, globalVariables);

        // then
        verify(converterFactory).createConverter(givenOutcomeStorage);
        verify(converter).updateOutcomeMapByModulesProcessingResults(results);
        verify(converter).updateOutcomeMapWithGlobalVariables(globalVariables);
    }

}
