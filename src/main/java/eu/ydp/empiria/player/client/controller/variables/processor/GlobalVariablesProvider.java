package eu.ydp.empiria.player.client.controller.variables.processor;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.manager.VariableManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.global.GlobalVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;

import java.util.Map;

public class GlobalVariablesProvider {

    @Inject
    private GlobalVariablesProcessor globalVariablesProcessor;

    public GlobalVariables retrieveGlobalVariables(ModulesProcessingResults modulesProcessingResults, VariableManager<Response> responseManager) {

        return calculateGlobalVariables(modulesProcessingResults, responseManager);
    }

    private GlobalVariables calculateGlobalVariables(ModulesProcessingResults modulesProcessingResults, VariableManager<Response> responseManager) {
        Map<String, DtoModuleProcessingResult> mapOfProcessingResults = modulesProcessingResults.getMapOfProcessingResults();
        return globalVariablesProcessor.calculateGlobalVariables(mapOfProcessingResults, responseManager);
    }
}
