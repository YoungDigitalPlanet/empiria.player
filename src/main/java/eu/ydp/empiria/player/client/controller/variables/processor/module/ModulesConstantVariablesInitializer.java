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
