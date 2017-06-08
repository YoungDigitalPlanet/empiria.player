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

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.ConstantVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;

public class ModulesConstantVariablesInitializer {

    private final ModuleTodoCalculator moduleTodoCalculator;

    @Inject
    public ModulesConstantVariablesInitializer(ModuleTodoCalculator moduleTodoCalculator) {
        this.moduleTodoCalculator = moduleTodoCalculator;
    }

    public void initializeTodoVariables(ItemResponseManager responseManager, ModulesProcessingResults modulesProcessingResults) {
        for (String responseId : responseManager.getVariableIdentifiers()) {
            Response response = responseManager.getVariable(responseId);
            DtoModuleProcessingResult moduleProcessingResult = modulesProcessingResults.getProcessingResultsForResponseId(responseId);

            initializeTodoCount(response, moduleProcessingResult);
        }
    }

    private void initializeTodoCount(Response response, DtoModuleProcessingResult moduleProcessingResult) {
        int todoCountForResponse = moduleTodoCalculator.calculateTodoForResponse(response);
        ConstantVariables constantVariables = moduleProcessingResult.getConstantVariables();
        constantVariables.setTodo(todoCountForResponse);
    }

}
