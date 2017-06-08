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
import eu.ydp.empiria.player.client.controller.variables.manager.VariableManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ProcessingResultsToOutcomeMapConverterFacade;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

public class VariableProcessingAdapter {

    private final ModulesVariablesProcessor modulesVariablesProcessor;
    private final AnswerEvaluationSupplier answerEvaluationProvider;
    private final ProcessingResultsToOutcomeMapConverterFacade mapConverterFacade;
    private final GlobalVariablesProvider globalVariablesProvider;

    @Inject
    public VariableProcessingAdapter(@PageScoped ModulesVariablesProcessor modulesVariablesProcessor,
                                     @PageScoped AnswerEvaluationSupplier answerEvaluationProvider, ProcessingResultsToOutcomeMapConverterFacade mapConverterFacade,
                                     GlobalVariablesProvider globalVariablesProvider) {
        this.modulesVariablesProcessor = modulesVariablesProcessor;
        this.mapConverterFacade = mapConverterFacade;
        this.answerEvaluationProvider = answerEvaluationProvider;
        this.globalVariablesProvider = globalVariablesProvider;
    }

    public void processResponseVariables(VariableManager<Response> responseManager, ItemOutcomeStorageImpl outcomeManager, ProcessingMode processingMode) {
        ModulesProcessingResults modulesProcessingResults = modulesVariablesProcessor.processVariablesForResponses(responseManager, processingMode);
        GlobalVariables globalVariables = globalVariablesProvider.retrieveGlobalVariables(modulesProcessingResults, responseManager);
        mapConverterFacade.convert(outcomeManager, modulesProcessingResults, globalVariables);
        answerEvaluationProvider.updateModulesProcessingResults(modulesProcessingResults);
    }
}
