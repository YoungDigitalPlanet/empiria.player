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

import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedAnswersManager;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VariablesProcessingModulesInitializerJUnitTest {

    private VariablesProcessingModulesInitializer variablesProcessingModulesInitializer;

    @Mock
    private GroupedAnswersManager groupedAnswersManager;
    @Mock
    private ModulesVariablesProcessor modulesVariablesProcessor;
    @Mock
    private MistakesInitializer mistakesInitializer;

    @Before
    public void setUp() throws Exception {
        variablesProcessingModulesInitializer = new VariablesProcessingModulesInitializer(groupedAnswersManager, modulesVariablesProcessor, mistakesInitializer);
    }

    @After
    public void after() {
        verifyNoMoreInteractions(groupedAnswersManager, modulesVariablesProcessor, mistakesInitializer);
    }

    @Test
    public void shouldCallAllRelatedInitializations() throws Exception {
        ItemResponseManager responseManager = mock(ItemResponseManager.class);
        ItemOutcomeStorageImpl outcomeStorage = new ItemOutcomeStorageImpl();
        variablesProcessingModulesInitializer.initializeVariableProcessingModules(responseManager, outcomeStorage);

        InOrder inOrder = inOrder(groupedAnswersManager, modulesVariablesProcessor, mistakesInitializer);
        inOrder.verify(groupedAnswersManager).initialize(responseManager);
        inOrder.verify(modulesVariablesProcessor).initialize(responseManager);
        inOrder.verify(mistakesInitializer).initialize(outcomeStorage);
    }
}
