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

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedAnswersManager;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

public class VariablesProcessingModulesInitializer {

    private final GroupedAnswersManager groupedAnswersManager;
    private final ModulesVariablesProcessor modulesVariablesProcessor;
    private final MistakesInitializer mistakesInitializer;

    @Inject
    public VariablesProcessingModulesInitializer(@PageScoped GroupedAnswersManager groupedAnswersManager,
                                                 @PageScoped ModulesVariablesProcessor modulesVariablesProcessor, MistakesInitializer mistakesInitializer) {
        this.groupedAnswersManager = groupedAnswersManager;
        this.modulesVariablesProcessor = modulesVariablesProcessor;
        this.mistakesInitializer = mistakesInitializer;
    }

    public void initializeVariableProcessingModules(ItemResponseManager responseManager, ItemOutcomeStorageImpl outcomeManager) {

        groupedAnswersManager.initialize(responseManager);
        modulesVariablesProcessor.initialize(responseManager);
        mistakesInitializer.initialize(outcomeManager);
    }

}
